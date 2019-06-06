package net.fyrezz.me.landlords.cmds;

import org.bukkit.Material;

import net.fyrezz.me.landlords.LPlayer;
import net.fyrezz.me.landlords.Lordship;
import net.fyrezz.me.landlords.P;

public class CmdDeposit extends LordshipCommand {

	@Override
	public void addAliases() {
		aliases.add("deposit");
		aliases.add("d");
	}

	@Override
	public void setRequirements() {
		requirements.isPlayer = RequirementState.REQUIRED;
		requirements.hasLordship = RequirementState.REQUIRED;
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
		int argAmount;

		try {
			argAmount = Integer.parseInt(commandContent.getArg(0));
		} catch (Exception exception) {
			P.p.getMM().undefinedMsg(lPlayer, "&c/l deposit <amount>");
			return;
		}

		if (argAmount < 1 | argAmount > 2304) {
			P.p.getMM().msg(lPlayer, "invalidamount");
			return;
		}

		if (!lPlayer.hasMaterial(Material.GOLD_INGOT, argAmount)) {
			P.p.getMM().msg(lPlayer, "notenoughgold");
			return;
		}
		lPlayer.removeMaterial(Material.GOLD_INGOT, argAmount);

		lordship.addGold(argAmount);

		vars.put("amount", Integer.toString(argAmount));
		vars.put("member", lPlayer.getName());
		P.p.getMM().lordshipMsg(lordship, "golddeposit", vars);
	}

}
