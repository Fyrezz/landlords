package net.fyrezz.me.landlords.cmds;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.fyrezz.me.landlords.LPlayer;
import net.fyrezz.me.landlords.Lordship;
import net.fyrezz.me.landlords.Lordships;
import net.fyrezz.me.landlords.P;

public class CmdInvite extends LordshipCommand {

	@Override
	public void addAliases() {
		aliases.add("invite");
	}

	@Override
	public void setRequirements() {
		requirements.isPlayer = RequirementState.REQUIRED;
		requirements.hasLordship = RequirementState.REQUIRED;
		requirements.allowedRanks = new ArrayList<Byte>(Arrays.asList((byte) 0, (byte) 1));
		requirements.minArgs = 1;
	}

	@Override
	public void setPermission() {
		permission = "landlords.player";
	}

	@Override
	public void perform(CommandContent commandContent) {
		Lordship lordship = commandContent.getLordship();
		LPlayer lPlayer = commandContent.getLPlayer();
		String playerString = commandContent.getArg(0);
		Player invitedPlayer;

		try {
			invitedPlayer = Bukkit.getPlayer(playerString);
			if (!invitedPlayer.isOnline()) {
				P.p.getMM().msg(lPlayer, "invalidplayer");
				return;
			}
		} catch (Exception exception) {
			P.p.getMM().msg(lPlayer, "invalidplayer");
			return;
		}

		if (!Lordships.invites.containsKey(lordship)) {
			Lordships.invites.put(lordship, new ArrayList<String>(Arrays.asList(playerString)));
		} else {

			/* Remove it in case there was a previous invite for this player */
			Lordships.invites.get(lordship).remove(playerString);
			Lordships.invites.get(lordship).add(playerString);
		}
		vars.put("player", playerString);
		P.p.getMM().msg(lPlayer, "playerinvited", vars);

		vars.put("lordship", lordship.getLord().getName());
		P.p.getMM().msg(invitedPlayer, "invitedto", vars);
	}

}
