package net.fyrezz.me.landlords.cmds;

import java.util.Arrays;

import net.fyrezz.me.landlords.LPlayer;
import net.fyrezz.me.landlords.Lordship;
import net.fyrezz.me.landlords.P;
import net.fyrezz.me.landlords.utils.RequirementState;

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
	}

	@Override
	public void setPermission() {
		permission = "landlords.player";
	}

	@Override
	public void perform(CommandContent commandContent) {
		Lordship lordship = commandContent.getLordship();
		LPlayer lPlayer = commandContent.getLPlayer();
		int argAmount;
		
		try {
			argAmount = Integer.parseInt(commandContent.getArg(0));
		} catch (Exception exception) {
			P.p.getMM().undefinedMsg(commandContent.getLPlayer(), "&c/l shrink <amountBlocks>");
			return;
		}
		
		if (lordship.getSide() <= 8) {
			P.p.getMM().msg(lPlayer, "cantshrinkmore");
			return;
		}
		
		if ((lordship.getSide() - argAmount) < 8) {
			vars.put("maxshrink", Integer.toString(lordship.getSide() - 8));
			P.p.getMM().msg(lPlayer, "cantshrinkthat", vars);
			return;
		}
		lordship.setSide(lordship.getSide() - argAmount);
		
		vars.put("shrunkamount", Integer.toString(argAmount));
		vars.put("newside", Integer.toString(lordship.getSide()));
		vars.put("newamount", Integer.toString(lordship.getArea()));
		P.p.getMM().lordshipMsg(lordship, "landshrunk", vars);
	}
}
