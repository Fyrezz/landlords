package net.fyrezz.me.landlords;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class LPlayers {

	private List<LPlayer> loadedLPlayers;
	
	public LPlayers() {
		this.loadedLPlayers = new ArrayList<LPlayer>();
	}
	
	public void load() {
		Integer count = 0;
		for (LPlayer lPlayer : P.p.getDB().getSavedLPlayers()) {
			loadedLPlayers.add(lPlayer);
			count++;
		}
		P.p.getLogger().log(Level.INFO, "Loaded " + count + " LPlayers into memory.");
	}
	
	public void clearMemory() {
		loadedLPlayers.clear();
	}

}
