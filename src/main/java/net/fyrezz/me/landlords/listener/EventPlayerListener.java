package net.fyrezz.me.landlords.listener;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import net.fyrezz.me.landlords.LPlayer;
import net.fyrezz.me.landlords.Lordship;
import net.fyrezz.me.landlords.P;

public class EventPlayerListener implements Listener {

	@EventHandler(priority = EventPriority.HIGH)
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

	@EventHandler(priority = EventPriority.HIGH)
	public void onBlockBreak(BlockBreakEvent event) {
		Location loc = event.getBlock().getLocation();
		
		Player player = event.getPlayer();
		LPlayer lPlayer = P.p.getLPlayers().getByUUID(player.getUniqueId().toString());
		
		Lordship lordship = P.p.getLordships().getByID(lPlayer.getUUID());

		Map<String, String> vars = new HashMap<String, String>();

		for (String ID : P.p.getLordships().getLoadedLordships().keySet()) {
			Lordship checkedLordship = P.p.getLordships().getByID(ID);
			if (checkedLordship.getID() != "DEFAULT" && lordship.getID() != checkedLordship.getID() && checkedLordship.isInsideLand(loc)) {
				if (checkedLordship.isProtected()) {
					event.setCancelled(true);
					vars.put("lordship", checkedLordship.getLord().getName());
					P.p.getMM().msg(lPlayer, "cantdothat", vars);
				} else {
					vars.put("enemy", event.getPlayer().getName());
					P.p.getMM().lordshipMsg(checkedLordship, "enemywarning", vars);
				}
			}
		}
	}
}
