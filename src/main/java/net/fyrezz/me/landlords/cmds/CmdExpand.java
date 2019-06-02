package net.fyrezz.me.landlords.cmds;

import java.util.Arrays;

import net.fyrezz.me.landlords.LPlayer;
import net.fyrezz.me.landlords.Lordship;
import net.fyrezz.me.landlords.P;
import net.fyrezz.me.landlords.utils.RequirementState;

public class CmdExpand extends LordshipCommand {

	@Override
	public void addAliases() {
		aliases.add("expand");
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
			P.p.getMM().undefinedMsg(commandContent.getLPlayer(), "&c/l expand <amountBlocks>");
			return;
		}

		if (argAmount < 1 | argAmount > 512) {
			P.p.getMM().msg(lPlayer, "invalidamount");
			return;
		}
		
		if (lordship.getSide() >= P.p.getConfig().getInt("maxside")) {
			P.p.getMM().msg(lPlayer, "cantexpandmore");
			return;
		}
		
		if ((lordship.getSide() + argAmount) > P.p.getConfig().getInt("maxside")) {
			vars.put("maxexpand", Integer.toString(P.p.getConfig().getInt("maxside") - lordship.getSide()));
			P.p.getMM().msg(lPlayer, "cantexpandthat", vars);
			return;
		}
		
		if (!lordship.canAffordExpansion(argAmount)) {
			P.p.getMM().msg(lPlayer, "notenoughgoldtoexpand");
			return;
		}
		lordship.setSide(lordship.getSide() + argAmount);
		
		vars.put("expansionamount", Integer.toString(argAmount));
		vars.put("newside", Integer.toString(lordship.getSide()));
		vars.put("newamount", Integer.toString(lordship.getArea()));
		P.p.getMM().lordshipMsg(lordship, "landexpanded", vars);
	}
}