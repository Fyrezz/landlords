package net.fyrezz.me.landlords.tasks;

import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import net.fyrezz.me.landlords.LPlayer;
import net.fyrezz.me.landlords.P;
import net.fyrezz.me.landlords.utils.LazyLocation;

public class TaskTeleport extends MyTask {
	private LPlayer lPlayer;
	private LazyLocation lazyLoc;
	
	public TaskTeleport(LPlayer lPlayer, LazyLocation lazyLoc) {
		this.lPlayer = lPlayer;
		this.lazyLoc = lazyLoc;
	}

	@Override
	public void init() {
		
	}

	@Override
	public void execute() {
		lPlayer.getPlayer().teleport(lazyLoc.getLocation(), TeleportCause.PLUGIN);
		P.p.getMM().msg(lPlayer, "teleported");
	}

}
