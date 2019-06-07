package net.fyrezz.me.landlords.cmds;

import org.bukkit.Material;

import net.fyrezz.me.landlords.LPlayer;
import net.fyrezz.me.landlords.Lordship;
import net.fyrezz.me.landlords.P;
import net.fyrezz.me.landlords.tasks.TaskTeleport;
import net.fyrezz.me.landlords.utils.LazyLocation;

public class CmdHome extends LordshipCommand {

	@Override
	public void addAliases() {
		aliases.add("home");
		aliases.add("h");
	}

	@Override
	public void setRequirements() {
		requirements.isPlayer = RequirementState.REQUIRED;
		requirements.hasLordship = RequirementState.REQUIRED;
		requirements.playerCost = 8;
	}

	@Override
	public void setPermission() {
		permission = "landlords.player";
	}

	@Override
	public void perform(CommandContent commandContent) {
		LPlayer lPlayer = commandContent.getLPlayer();
		Lordship lordship = lPlayer.getLordship();
		
		int seconds = lordship.getTeleportSeconds();
		LazyLocation lazyLoc = lordship.getHomeblock();
		
		lPlayer.removeMaterial(Material.GOLD_INGOT, requirements.playerCost);
		
		P.p.getCountdowner().addTask(new TaskTeleport(lPlayer, lazyLoc), (long) (seconds * 20));
		
		vars.put("time", Integer.toString(seconds));
		P.p.getMM().msg(commandContent.getLPlayer(), "teleportwait", vars);
	}

}
