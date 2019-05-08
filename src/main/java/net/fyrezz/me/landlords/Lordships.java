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

	public void loadLordship(Lordship newLordship) {
		for (Lordship lordship : loadedLordships) {
			if (lordship.getID() == newLordship.getID()) {
				P.p.getLogger().log(Level.WARNING,
						"Cannot load Lordship " + lordship.getID() + ": It's already loaded!");
				return;
			}
		}
		loadedLordships.add(newLordship);
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

	public Lordship getByLPlayer(LPlayer lPlayer) {
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

	public Lordship getByPlayerName(String name) {
		Lordship finalLordship = null;
		for (Lordship lordship : loadedLordships) {
			for (LPlayer lPlayer : lordship.getMembers()) {
				if (lPlayer.getName() == name) {
					finalLordship = lordship;
				}
			}
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
