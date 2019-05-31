package net.fyrezz.me.landlords.cmds;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import net.fyrezz.me.landlords.P;
import net.fyrezz.me.landlords.utils.RequirementState;

public class CmdWithdraw extends LordshipCommand {

	@Override
	public void addAliases() {
		this.aliases.add("withdraw");
		this.aliases.add("w");
	}

	@Override
	public void setRequirements() {
		this.commandRequirements.isPlayer = RequirementState.REQUIRED;
		this.commandRequirements.hasLordship = RequirementState.REQUIRED;
		this.commandRequirements.allowedRanks.remove((byte) 2);
		this.commandRequirements.allowedRanks.remove((byte) 3);
	}

	@Override
	public void setPermission() {
		this.permission = "landlords.player";
	}

	@Override
	public void perform(CommandContent commandContent) {
		int argAmount;
		int vaultAmount = commandContent.getLordship().getGold();

		try {
			argAmount = Integer.parseInt(commandContent.getArg(0));
		} catch (NumberFormatException exception) {
			P.p.getMM().undefinedMsg(commandContent.getLPlayer(), "&c/l withdraw <amount>");
			return;
		}

		if (vaultAmount < argAmount) {
			P.p.getMM().msg(commandContent.getLPlayer(), "notenoughgoldvault");
			return;
		}
		
		commandContent.getLordship().removeGold(argAmount);
		
		ItemStack goldWithdrawn = new ItemStack(Material.GOLD_INGOT, argAmount);
		commandContent.getPlayer().getWorld().dropItem(commandContent.getPlayer().getEyeLocation(), goldWithdrawn);

		this.vars.put("amount", Integer.toString(argAmount));
		this.vars.put("member", commandContent.getPlayer().getName());
		P.p.getMM().lordshipMsg(commandContent.getLordship(), "goldwithdraw", vars);

	}

}
