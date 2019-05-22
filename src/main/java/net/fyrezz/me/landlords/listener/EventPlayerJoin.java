package net.fyrezz.me.landlords.listener;

import java.util.logging.Level;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.fyrezz.me.landlords.LPlayer;
import net.fyrezz.me.landlords.P;

public class EventPlayerJoin implements Listener {

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		LPlayer lPlayer = P.p.getLPlayers().getByUUID(player.getUniqueId().toString());
		
		// All online players must have a LPlayer loaded
		if (lPlayer == null) {
			P.p.getLPlayers().loadLPlayer(new LPlayer(player.getUniqueId().toString(), player.getName()));
			lPlayer = P.p.getLPlayers().getByUUID(player.getUniqueId().toString());
		}

		if (lPlayer == null) {
		P.p.getLogger().log(Level.WARNING,
				"Couldn't load LPlayer " + player.getName() + " [" + player.getUniqueId() + "]");
		}
		
		// Update player's name
		lPlayer.setName(player.getName());
	}

}
