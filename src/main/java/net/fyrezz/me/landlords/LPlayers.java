package net.fyrezz.me.landlords;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.entity.Player;

public class LPlayers {

	private Map<String, LPlayer> loadedLPlayers = new HashMap<String, LPlayer>();

	public LPlayers() {
	}
	
	public Map<String, LPlayer> getLoadedLPlayers() {
		return loadedLPlayers;
	}

	/*
	 * All Lordship members have a LPlayer, doesn't matter if they're not online.
	 * 
	 * All online players have a LPlayer. Offline players without Lordship might
	 * have a LPlayer.
	 */
	public void load() {
		for (String ID : P.p.getLordships().getLoadedLordships().keySet()) {
			Lordship lordship = P.p.getLordships().getByID(ID);
			
			for (LPlayer lPlayer : lordship.getMemberList()) {
				loadedLPlayers.put(lPlayer.getUUID(), lPlayer);
				
				lPlayer.setLordship(lordship);
			}
		}
		
		/* Make sure each online player has a LPlayer instance */
		for (Player player : P.p.getServer().getOnlinePlayers()) {
			if (!loadedLPlayers.containsKey(player.getUniqueId().toString())) {
				loadLPlayer(new LPlayer(player.getUniqueId().toString(), player.getName()));
			}
		}
		P.p.getLogger().log(Level.INFO, "Loaded " + loadedLPlayers.size() + " LPlayers to memory.");
	}

	public void clearMemory() {
		loadedLPlayers.clear();
	}

	public LPlayer getByUUID(String UUID) {
		return loadedLPlayers.get(UUID);
	}

	public void loadLPlayer(LPlayer lPlayer) {
		loadedLPlayers.put(lPlayer.getUUID(), lPlayer);
		P.p.getLogger().log(Level.FINE, "Loaded LPlayer " + lPlayer.getName() + " [" + lPlayer.getUUID() + "]");
	}

	public boolean isLoaded(LPlayer lPlayer) {
		return loadedLPlayers.containsKey(lPlayer.getUUID());
	}

	public boolean isLoaded(String UUID) {
		return loadedLPlayers.containsKey(UUID);
	}

}
