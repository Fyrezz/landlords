package net.fyrezz.me.landlords.cmds;

import java.util.Arrays;

import org.bukkit.entity.Player;

import net.fyrezz.me.landlords.LPlayer;
import net.fyrezz.me.landlords.Lordship;
import net.fyrezz.me.landlords.P;

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
			P.p.getMM().undefinedMsg(lPlayer, "&c/l expand <amountBlocks>");
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
		
		lordship.showBoundaries(lPlayer);
		
		vars.put("expandamount", Integer.toString(argAmount / 2));
		vars.put("newside", Integer.toString(lordship.getSide()));
		vars.put("newamount", Integer.toString(lordship.getArea()));
		P.p.getMM().lordshipMsg(lordship, "landexpanded", vars);
	}
}