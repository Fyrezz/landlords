package net.fyrezz.me.landlords;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class LPlayers {
	
	private static LPlayers instance;
	
	private static List<LPlayer> loadedLPlayers = new ArrayList<LPlayer>();
	
	public LPlayers() {
		instance = this;
	}
	
	public static LPlayers getInstance() {
		return instance;
	}
	
	public static void load() {
		Integer count = 0;
		for (Lordship lordship : P.p.getDB().getSavedLordships()) {
			loadedlordships.add(lordship);
			count++;
		}
		P.p.getLogger().log(Level.INFO, "Loaded " + count + " Lordships into memory.");
	}
	
	public static void clearMemory() {
		loadedlordships.clear();
	}
	
	public static void loadLordship(Lordship lordship) {
		if (loadedlordships.contains(lordship)) {
			P.p.getLogger().log(Level.WARNING, "Cannot load Lordship " + lordship.getID() + ": It's already loaded!");
			return;
		}
		loadedlordships.add(lordship);
	}
	
	public static void unloadLordship(Lordship lordship) {
		if (!loadedlordships.contains(lordship)) {
			P.p.getLogger().log(Level.WARNING, "Cannot unload Lordship " + lordship.getID() + ": It's not loaded!");
			return;
		}
		loadedlordships.add(lordship);
	}
	
	public static List<Lordship> getLoadedLordships(){
		return loadedlordships;
	}
	

}
