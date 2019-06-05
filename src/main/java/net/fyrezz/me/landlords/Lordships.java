package net.fyrezz.me.landlords;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import net.fyrezz.me.landlords.utils.LazyLocation;

public class Lordships {
	
	public static final String DEFAULT_ID = "DEFAULT";
	public static Map<Lordship, List<String>> invites = new HashMap<Lordship, List<String>>();
	
	private Map<String, Lordship> loadedLordships = new HashMap<String, Lordship>();

	public Lordships() {
	}

	public Map<String, Lordship> getLoadedLordships() {
		return loadedLordships;
	}

	public void load() {
		loadedLordships = P.p.getDB().getSavedLordships();
		P.p.getLogger().log(Level.INFO, "Loaded " + loadedLordships.size() + " Lordships to memory.");
	}

	public void clearMemory() {
		loadedLordships.clear();
	}

	public void loadLordship(Lordship newLordship) {
		loadedLordships.put(newLordship.getID(), newLordship);
	}

	public void unloadLordship(Lordship lordship) {
		loadedLordships.remove(lordship.getID());
	}

	public Lordship getByID(String ID) {
		return loadedLordships.get(ID);
	}

	public Lordship getByLordName(String lordName) {
		for (String ID : loadedLordships.keySet()) {
			Lordship lordship = loadedLordships.get(ID);
			if (lordship.getLord().getName() == "lordName") {
				return lordship;
			}
		}
		return null;
	}
	
	public boolean lordshipExists(String lordName) {
		return getByLordName(lordName) != null;
	}

	public boolean lordshipsNear(LPlayer lPlayer, double distance) {
		LazyLocation lazyLoc = new LazyLocation(lPlayer.getPlayer().getLocation());

		for (String ID : loadedLordships.keySet()) {
			Lordship checkedLordship = loadedLordships.get(ID);
			if (checkedLordship.getID() != lPlayer.getLordship().getID()
					&& checkedLordship.distanceFromCenterTo(lazyLoc) <= distance) {
				return true;
			}
		}
		return false;
	}
}
