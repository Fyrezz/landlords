package net.fyrezz.me.landlords.cmds;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import net.fyrezz.me.landlords.P;
import net.fyrezz.me.landlords.utils.RequirementState;

public class CmdWithdraw extends LordshipCommand {

	@Override
	public void addAliases() {
		aliases.add("withdraw");
		aliases.add("w");
	}

	@Override
	public void setRequirements() {
		requirements.isPlayer = RequirementState.REQUIRED;
		requirements.hasLordship = RequirementState.REQUIRED;
		requirements.allowedRanks = Arrays.asList((byte) 0, (byte) 1);
	}

	@Override
	public void setPermission() {
		permission = "landlords.player";
	}

	@Override
	public void perform(CommandContent commandContent) {
		int argAmount;
		int vaultAmount = commandContent.getLordship().getGold();

		try {
			argAmount = Integer.parseInt(commandContent.getArg(0));
		} catch (Exception exception) {
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

		vars.put("amount", Integer.toString(argAmount));
		vars.put("member", commandContent.getPlayer().getName());
		P.p.getMM().lordshipMsg(commandContent.getLordship(), "goldwithdraw", vars);
	}

}
