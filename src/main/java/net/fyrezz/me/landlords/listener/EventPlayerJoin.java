package net.fyrezz.me.landlords.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.fyrezz.me.landlords.P;

public class EventPlayerJoin implements Listener {

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerJoin(PlayerJoinEvent event) {
		LPlayer lPlayer = P.p.getLordships().get
	}

}
