package net.fyrezz.me.landlords.cmds;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.fyrezz.me.landlords.LPlayer;
import net.fyrezz.me.landlords.Lordship;
import net.fyrezz.me.landlords.P;

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
		LPlayer lPlayer = commandContent.getLPlayer();
		Lordship lordship = lPlayer.getLordship();
		Player player = lPlayer.getPlayer();

		vars.put("lordship", lPlayer.getName());
		P.p.getMM().broadcast("lordshipdeletedbroadcast", vars);

		if (lordship.getGold() > 0) {
			ItemStack goldGiven = new ItemStack(Material.GOLD_INGOT, lordship.getGold());
			player.getWorld().dropItem(player.getEyeLocation(), goldGiven);

			vars.put("amount", Integer.toString(goldGiven.getAmount()));
			P.p.getMM().lordshipMsg(lordship, "lordshipdeleted", vars);
		}
		
		P.p.getLordships().unloadLordship(lordship);

		for (LPlayer member : lordship.getMemberList()) {
			P.p.getLPlayers().unloadLPlayer(member);
			P.p.getLPlayers().loadLPlayer(new LPlayer(member.getUUID(), member.getName()));
		}
	}

}
