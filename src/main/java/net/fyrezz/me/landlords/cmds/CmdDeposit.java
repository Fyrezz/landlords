package net.fyrezz.me.landlords.cmds;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import net.fyrezz.me.landlords.P;
import net.fyrezz.me.landlords.utils.RequirementState;

public class CmdDeposit extends LordshipCommand {

	@Override
	public void addAliases() {
		this.aliases.add("deposit");
		this.aliases.add("d");
	}

	@Override
	public void setRequirements() {
		this.commandRequirements.isPlayer = RequirementState.REQUIRED;
		this.commandRequirements.hasLordship = RequirementState.REQUIRED;
	}

	@Override
	public void setPermission() {
		this.permission = "landlords.player";
	}

	@Override
	public void perform(CommandContent commandContent) {
		int argAmount;
		int invAmount = 0;

		try {
			argAmount = Integer.parseInt(commandContent.getArg(0));
		} catch (NumberFormatException exception) {
			P.p.getMM().undefinedMsg(commandContent.getLPlayer(), "&c/l deposit <amount>");
			return;
		}

		for (ItemStack item : commandContent.getPlayer().getInventory().getContents()) {
			if (item.getType().equals(Material.GOLD_INGOT)) {
				invAmount++;
			}
		}

		if (invAmount >= argAmount) {
			P.p.getMM().msg(commandContent.getLPlayer(), "notenoughgold");
			return;
		}

		commandContent.getLordship().addGold(argAmount);
		commandContent.getPlayer().getInventory().remove(Material.GOLD_INGOT);

		invAmount -= argAmount;
		commandContent.getPlayer().getInventory().addItem(new ItemStack(Material.GOLD_INGOT, invAmount));

		this.vars.put("amount", Integer.toString(argAmount));
		this.vars.put("member", commandContent.getPlayer().getName());
		P.p.getMM().lordshipMsg(commandContent.getLordship(), "golddeposit", vars);
	}

}
