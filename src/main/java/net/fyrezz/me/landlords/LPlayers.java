package net.fyrezz.me.landlords;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class LPlayers {

	private Map<String, LPlayer> loadedLPlayers = new HashMap<String, LPlayer>();

	public LPlayers() {
	}

	/*
	 * Get & Set
	 */

	public Map<String, LPlayer> getLoadedLPlayers() {
		return loadedLPlayers;
	}

	/*
	 * Utils
	 * 
	 * All Lordship members have a LPlayer, doesn't matter if they're not online.
	 * 
	 * All online players have a LPlayer. Offline players without Lordship might
	 * have a LPlayer.
	 */

	public void load() {
		// LPlayers are loaded from Lordships. No Database needed.
		for (String ID : P.p.getLordships().getLoadedLordships().keySet()) {
			Lordship lordship = P.p.getLordships().getByID(ID);
			for (LPlayer lPlayer : lordship.getMemberList()) {
				loadedLPlayers.put(lPlayer.getUUID(), lPlayer);
				lPlayer.setLordship(lordship); // Set the Lordship of the loaded LPlayer
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
		return (loadedLPlayers.containsKey(lPlayer.getUUID()));
	}

	public boolean isLoaded(String UUID) {
		return (loadedLPlayers.containsKey(UUID));
	}

}
