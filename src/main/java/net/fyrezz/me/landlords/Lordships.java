package net.fyrezz.me.landlords;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class Lordships {
	
	private static Lordships instance;
	
	private static List<Lordship> loadedLordships = new ArrayList<Lordship>();
	
	public Lordships() {
		instance = this;
	}
	
	public static Lordships getInstance() {
		return instance;
	}
	
	public static void load() {
		Integer count = 0;
		for (Lordship lordship : P.p.getDB().getSavedLordships()) {
			loadedLordships.add(lordship);
			count++;
		}
		P.p.getLogger().log(Level.INFO, "Loaded " + count + " Lordships into memory.");
	}
	
	public static void clearMemory() {
		loadedLordships.clear();
	}
	
	public static void loadLordship(Lordship lordship) {
		if (loadedLordships.contains(lordship)) {
			P.p.getLogger().log(Level.WARNING, "Cannot load Lordship " + lordship.getID() + ": It's already loaded!");
			return;
		}
		loadedLordships.add(lordship);
	}
	
	public static void unloadLordship(Lordship lordship) {
		if (!loadedLordships.contains(lordship)) {
			P.p.getLogger().log(Level.WARNING, "Cannot unload Lordship " + lordship.getID() + ": It's not loaded!");
			return;
		}
		loadedLordships.add(lordship);
	}
	
	public static List<Lordship> getLoadedLordships(){
		return loadedLordships;
	}
	

}
