package net.fyrezz.me.landlords;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class LPlayers {

	private Map<String, LPlayer> loadedLPlayers;

	public LPlayers() {
		this.loadedLPlayers = new HashMap<String, LPlayer>();
	}
	
	public void load() {
		loadedLPlayers = P.p.getDB().getSavedLPlayers();
		P.p.getLogger().log(Level.INFO, "Loaded " + loadedLPlayers.size() + " LPlayers to memory.");
	}
	
	public void save() {
		P.p.getDB().saveLPlayers(loadedLPlayers);
	}

	public void clearMemory() {
		loadedLPlayers.clear();
	}

	public LPlayer getByUUID(String UUID) {
		return loadedLPlayers.get(UUID);
	}

	public Map<String, LPlayer> getLoadedLPlayers() {
		return loadedLPlayers;
	}

}
