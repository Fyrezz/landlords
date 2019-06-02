package net.fyrezz.me.landlords.cmds;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import net.fyrezz.me.landlords.P;
import net.fyrezz.me.landlords.utils.RequirementState;

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
	}

	@Override
	public void setPermission() {
		permission = "landlords.player";
	}

	@Override
	public void perform(CommandContent commandContent) {
		int argAmount;

		try {
			argAmount = Integer.parseInt(commandContent.getArg(0));
		} catch (Exception exception) {
			P.p.getMM().undefinedMsg(commandContent.getLPlayer(), "&c/l deposit <amount>");
			return;
		}

		if (!commandContent.getLPlayer().hasMaterial(Material.GOLD_INGOT, argAmount)) {
			P.p.getMM().msg(commandContent.getLPlayer(), "notenoughgold");
			return;
		}
		
		commandContent.getLordship().addGold(argAmount);
		
		commandContent.getLPlayer().removeMaterial(Material.GOLD_INGOT, argAmount);

		vars.put("amount", Integer.toString(argAmount));
		vars.put("member", commandContent.getPlayer().getName());
		P.p.getMM().lordshipMsg(commandContent.getLordship(), "golddeposit", vars);
	}

}
