package net.fyrezz.me.landlords;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class Lordships {
	
	private List<Lordship> loadedLordships;
	
	public Lordships() {
		this.loadedLordships = new ArrayList<Lordship>();
	}
	
	public void load() {
		Integer count = 0;
		for (Lordship lordship : P.p.getDB().getSavedLordships()) {
			loadedLordships.add(lordship);
			count++;
		}
		P.p.getLogger().log(Level.INFO, "Loaded " + count + " Lordships into memory.");
	}
	
	public void clearMemory() {
		loadedLordships.clear();
	}
	
	public void loadLordship(Lordship lordship) {
		if (loadedLordships.contains(lordship)) {
			P.p.getLogger().log(Level.WARNING, "Cannot load Lordship " + lordship.getID() + ": It's already loaded!");
			return;
		}
		loadedLordships.add(lordship);
	}
	
	public void unloadLordship(Lordship lordship) {
		if (!loadedLordships.contains(lordship)) {
			P.p.getLogger().log(Level.WARNING, "Cannot unload Lordship " + lordship.getID() + ": It's not loaded!");
			return;
		}
		loadedLordships.add(lordship);
	}
	
	public List<Lordship> getLoadedLordships(){
		return loadedLordships;
	}
	

}
