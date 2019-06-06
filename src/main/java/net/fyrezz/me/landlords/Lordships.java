package net.fyrezz.me.landlords;

import java.util.ArrayList;
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
	
	public List<Lordship> getLordshipsList() {
		List<Lordship> lordshipsList = new ArrayList<Lordship>();
		for (String ID : loadedLordships.keySet()) {
			lordshipsList.add(loadedLordships.get(ID));
		}
		return lordshipsList;
	}

	public Lordship getByLordName(String lordName) {
		for (Lordship lordship : getLordshipsList()) {
			String lordshipLord = lordship.getLord().getName();
			if (lordshipLord.equals(lordName)) {
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
		for (Lordship lordship : getLordshipsList()) {
			if (lordship.getID() != lPlayer.getLordship().getID()
					&& lordship.distanceFromCenterTo(lazyLoc) <= distance) {
				return true;
			}
		}
		return false;
	}
	
	public String getLordshipIDAt(LazyLocation lazyLoc) {
		for (Lordship lordship : getLordshipsList()) {
			if (lordship.containsLazyLoc(lazyLoc)) {
				return lordship.getID();
			}
		}
		return DEFAULT_ID;
	}
	
	public Lordship getLordshipAt(LazyLocation lazyLoc) {
		return loadedLordships.get(getLordshipIDAt(lazyLoc));
	}
}
