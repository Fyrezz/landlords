package net.fyrezz.me.landlords.cmds;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import net.fyrezz.me.landlords.LPlayer;
import net.fyrezz.me.landlords.Lordship;
import net.fyrezz.me.landlords.P;
import net.fyrezz.me.landlords.utils.RequirementState;

public class CmdDelete extends LordshipCommand {

	public CmdDelete() {
		super();
	}

	@Override
	public void addAliases() {
		aliases.add("delete");
		aliases.add("del");
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

		vars.put("lordship", lPlayer.getName());
		P.p.getMM().broadcast("lordshipdeletedbroadcast", vars);

		if (lordship.getGold() > 0) {
			ItemStack goldGiven = new ItemStack(Material.GOLD_INGOT, commandContent.getLordship().getGold());
			commandContent.getPlayer().getWorld().dropItem(commandContent.getPlayer().getEyeLocation(), goldGiven);

			vars.put("amount", Integer.toString(goldGiven.getAmount()));
			P.p.getMM().lordshipMsg(commandContent.getLordship(), "lordshipdeleted", vars);
		}
		P.p.getLordships().unloadLordship(lordship);

		P.p.getDB().saveLoadedLordships();

		P.p.getLordships().clearMemory();
		P.p.getLordships().load();

		P.p.getLPlayers().clearMemory();
		P.p.getLPlayers().load();
	}

}
