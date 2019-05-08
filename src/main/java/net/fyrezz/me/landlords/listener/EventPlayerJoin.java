package net.fyrezz.me.landlords.listener;

import java.util.logging.Level;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.fyrezz.me.landlords.LPlayer;
import net.fyrezz.me.landlords.Lordship;
import net.fyrezz.me.landlords.P;

public class EventPlayerJoin implements Listener {

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		Lordship lordship = P.p.getLordships().getByPlayerName(player.getName());
		if (lordship != null) {
			LPlayer lPlayer = lordship.getLPlayerFromUUID(player.getUniqueId().toString());
			lPlayer.setName(player.getName());
			P.p.getLogger().log(Level.INFO,  "UPDATED PLAYER " + lPlayer.getName());
		}
	}

}
