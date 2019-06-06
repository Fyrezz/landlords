package net.fyrezz.me.landlords.listener;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import net.fyrezz.me.landlords.LPlayer;
import net.fyrezz.me.landlords.Lordship;
import net.fyrezz.me.landlords.P;
import net.fyrezz.me.landlords.utils.LazyLocation;
import net.fyrezz.me.landlords.utils.PermissionChecker;
import net.fyrezz.me.landlords.utils.PlayerAction;

public class EventPlayerListener implements Listener {

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		LPlayer lPlayer = P.p.getLPlayers().getByUUID(player.getUniqueId().toString());

		/* All online players must have a LPlayer loaded */
		if (lPlayer == null) {
			P.p.getLPlayers().loadLPlayer(new LPlayer(player.getUniqueId().toString(), player.getName()));
			lPlayer = P.p.getLPlayers().getByUUID(player.getUniqueId().toString());
		}

		if (lPlayer == null) {
			P.p.getLogger().log(Level.WARNING,
					"Couldn't load LPlayer " + player.getName() + " [" + player.getUniqueId() + "]");
		}
		lPlayer.setName(player.getName());
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onBlockBreak(BlockBreakEvent event) {
		LPlayer lPlayer = P.p.getLPlayers().getByUUID(event.getPlayer().getUniqueId().toString());
		LazyLocation lazyLoc = new LazyLocation(event.getBlock().getLocation());

		boolean shouldCancel = checkPlayerAction(lPlayer, lazyLoc, PlayerAction.PLACE_BLOCK);

		event.setCancelled(shouldCancel);
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onBlockPlace(BlockPlaceEvent event) {
		LPlayer lPlayer = P.p.getLPlayers().getByUUID(event.getPlayer().getUniqueId().toString());
		LazyLocation lazyLoc = new LazyLocation(event.getBlock().getLocation());

		boolean shouldCancel = checkPlayerAction(lPlayer, lazyLoc, PlayerAction.PLACE_BLOCK);

		event.setCancelled(shouldCancel);
	}

	/**
	 * Checks for player action. If the action takes place inside a Lordship, check
	 * if the player is member. If he is, check if he is allowed to perform it. If
	 * he is not, cancel it if the Lordship has enough Gold to be protected.
	 * 
	 * @param lPlayer
	 * @param lazyLoc
	 * @param action
	 * @return true if action should be cancelled
	 */
	private boolean checkPlayerAction(LPlayer lPlayer, LazyLocation lazyLoc, PlayerAction action) {
		for (String ID : P.p.getLordships().getLoadedLordships().keySet()) {
			Lordship checkedLordship = P.p.getLordships().getByID(ID);
			if (checkedLordship.containsLazyLoc(lazyLoc)) {
				Map<String, String> vars = new HashMap<String, String>();

				/* Depending on the action, the event might be cancelled if */
				/* the player is not allowed to perform it inside his Lordship. */
				if (checkedLordship.getID() == lPlayer.getLordship().getID()) {
					if (!PermissionChecker.isAllowed(lPlayer, action)) {
						P.p.getMM().msg(lPlayer, "notallowed");
						return true;
					}

					/* For all other actions, allow it */
					return false;
				}

				/* All actions should be cancelled if the Lordship is protected. */
				if (checkedLordship.isProtected()) {
					vars.put("lordship", checkedLordship.getLord().getName());
					P.p.getMM().msg(lPlayer, "cantdothat", vars);
					return true;
				} else {
					vars.put("enemy", lPlayer.getName());
					P.p.getMM().lordshipMsg(checkedLordship, "enemywarning", vars);
					return false;
				}
			}
		}
		return false;
	}

}
