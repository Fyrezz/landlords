package net.fyrezz.me.landlords.cmds;

import java.util.Arrays;

import org.bukkit.entity.Player;

import net.fyrezz.me.landlords.LPlayer;
import net.fyrezz.me.landlords.Lordship;
import net.fyrezz.me.landlords.P;

public class CmdShrink extends LordshipCommand {

	@Override
	public void addAliases() {
		aliases.add("shrink");
	}

	@Override
	public void setRequirements() {
		requirements.isPlayer = RequirementState.REQUIRED;
		requirements.hasLordship = RequirementState.REQUIRED;
		requirements.allowedRanks = Arrays.asList((byte) 0);
		requirements.minArgs = 1;
	}

	@Override
	public void setPermission() {
		permission = "landlords.player";
	}

	@Override
	public void perform(CommandContent commandContent) {
		LPlayer lPlayer = commandContent.getLPlayer();
		Lordship lordship = lPlayer.getLordship();
		
		int argAmount;
		
		try {
			argAmount = Integer.parseInt(commandContent.getArg(0)) * 2;
		} catch (Exception exception) {
			P.p.getMM().undefinedMsg(commandContent.getLPlayer(), "&c/l shrink <amountBlocks>");
			return;
		}

		if (argAmount < 1 | argAmount > 512) {
			P.p.getMM().msg(lPlayer, "invalidamount");
			return;
		}
		
		if (lordship.getSide() <= 8) {
			P.p.getMM().msg(lPlayer, "cantshrinkmore");
			return;
		}
		
		if ((lordship.getSide() - argAmount) < 8) {
			vars.put("maxshrink", Integer.toString((lordship.getSide() - 8) / 2));
			P.p.getMM().msg(lPlayer, "cantshrinkthat", vars);
			return;
		}
		
		lordship.setSide(lordship.getSide() - argAmount);
		
		lordship.showBoundaries(lPlayer);
		
		vars.put("shrunkamount", Integer.toString(argAmount / 2));
		vars.put("newside", Integer.toString(lordship.getSide()));
		vars.put("newamount", Integer.toString(lordship.getArea()));
		P.p.getMM().lordshipMsg(lordship, "landshrunk", vars);
	}
}
