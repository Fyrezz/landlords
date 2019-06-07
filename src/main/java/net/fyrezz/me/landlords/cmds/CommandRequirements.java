package net.fyrezz.me.landlords.cmds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;

import net.fyrezz.me.landlords.LPlayer;
import net.fyrezz.me.landlords.Lordship;
import net.fyrezz.me.landlords.P;

public class CommandRequirements {

	public RequirementState isPlayer = RequirementState.ALLOWED;
	public RequirementState hasLordship = RequirementState.ALLOWED;
	public RequirementState isInOwnLand = RequirementState.ALLOWED;
	public int minArgs = 0;
	public int playerCost = 0;
	public int lordshipCost = 0;
	public List<Byte> allowedRanks = new ArrayList<Byte>(Arrays.asList((byte) 0, (byte) 1, (byte) 2, (byte) 3));

	public boolean check(CommandContent commandContent) {
		if (commandContent.getArgs().size() - 1 < minArgs) {
			P.p.getMM().msg(commandContent.getSender(), "invalidargs");
			return false;
		}
		
		if (commandContent.isPlayer()) {
			LPlayer lPlayer = commandContent.getLPlayer();

			if (this.isPlayer == RequirementState.EXCLUDED) {
				P.p.getMM().msg(lPlayer, "consolecommandonly");
				return false;
			}
			
			if (playerCost > 0 && !lPlayer.hasMaterial(Material.GOLD_INGOT, playerCost)) {
				Map<String, String> vars = new HashMap<String, String>();
				vars.put("amount", Integer.toString(playerCost));
				P.p.getMM().msg(lPlayer, "notenoughgold", vars);
				return false;
			}

			if (lPlayer.hasLordship()) {
				Lordship lordship = lPlayer.getLordship();
				
				if (hasLordship == RequirementState.EXCLUDED) {
					P.p.getMM().msg(lPlayer, "alreadyinalordship");
					return false;
				}

				if (!allowedRanks.contains(lordship.getRank(lPlayer))) {
					P.p.getMM().msg(lPlayer, "notenoughrank");
					return false;
				}

				if (isInOwnLand == RequirementState.EXCLUDED
						&& lordship.containsLazyLoc(lPlayer.getLazyLocation())) {
					P.p.getMM().msg(lPlayer, "cantbeinownland");
					return false;

				}
				
				if (isInOwnLand == RequirementState.REQUIRED && !lordship.containsLazyLoc(lPlayer.getLazyLocation())) {
					P.p.getMM().msg(lPlayer, "mustbeinownland");
					return false;
				}
				
				if (lordshipCost > 0 && lordshipCost < lordship.getGold()) {
					P.p.getMM().msg(lPlayer, "notenoughgoldvault");
					return false;
				}

			} else if (hasLordship == RequirementState.REQUIRED) {
				P.p.getMM().msg(lPlayer, "notinalordship");
				return false;
			}

		} else if (isPlayer == RequirementState.REQUIRED) {
			P.p.getMM().msg(commandContent.getSender(), "playercommandonly");
			return false;
		}
		return true;
	}
}
