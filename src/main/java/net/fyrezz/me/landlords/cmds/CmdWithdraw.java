package net.fyrezz.me.landlords.cmds;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.fyrezz.me.landlords.LPlayer;
import net.fyrezz.me.landlords.Lordship;
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
		Lordship lordship = commandContent.getLordship();
		LPlayer lPlayer = commandContent.getLPlayer();
		Player player = commandContent.getPlayer();
		
		int argAmount;
		int vaultAmount = lordship.getGold();

		try {
			argAmount = Integer.parseInt(commandContent.getArg(0));
		} catch (Exception exception) {
			P.p.getMM().undefinedMsg(lPlayer, "&c/l withdraw <amount>");
			return;
		}

		if (argAmount < 1 | argAmount > 2304) {
			P.p.getMM().msg(lPlayer, "invalidamount");
			return;
		}

		if (vaultAmount < argAmount) {
			P.p.getMM().msg(lPlayer, "notenoughgoldvault");
			return;
		}
		
		lordship.removeGold(argAmount);
		
		ItemStack goldWithdrawn = new ItemStack(Material.GOLD_INGOT, argAmount);
		player.getWorld().dropItem(player.getEyeLocation(), goldWithdrawn);

		vars.put("amount", Integer.toString(argAmount));
		vars.put("member", lPlayer.getName());
		P.p.getMM().lordshipMsg(lordship, "goldwithdraw", vars);
	}

}
