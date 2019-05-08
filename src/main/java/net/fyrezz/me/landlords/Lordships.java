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
		loadedLordships = P.p.getDB().loadSavedLordships();
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

	/*
	 * Getting a specific Lordship
	 */

	public Lordship getByID(String ID) {
		Lordship finalLordship = null;
		for (Lordship lordship : loadedLordships) {
			if (lordship.getID() == ID) {
				finalLordship = lordship;
			}
		}
		if (finalLordship == null) {
			P.p.getLogger().log(Level.WARNING, "Error getting Lordship " + ID);
		}
		return finalLordship;
	}

	public Lordship getByPlayer(LPlayer lPlayer) {
		Lordship finalLordship = null;
		for (Lordship lordship : loadedLordships) {
			if (lordship.getMembers().contains(lPlayer)) {
				finalLordship = lordship;
			}
		}
		if (finalLordship == null) {
			P.p.getLogger().log(Level.WARNING, "Error getting Lordship of LPlayer " + lPlayer.getUUID());
		}
		return finalLordship;
	}

	/*
	 * Get all Lordships
	 */

	public List<Lordship> getLoadedLordships() {
		return loadedLordships;
	}

}
