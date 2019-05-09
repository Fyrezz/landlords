package net.fyrezz.me.landlords;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import net.fyrezz.me.landlords.utils.LazyLocation;

public class Lordships {

	private Map<String, Lordship> loadedLordships;

	public Lordships() {
		this.loadedLordships = new HashMap<String, Lordship>();
	}

	public void load() {
		loadedLordships = P.p.getDB().getSavedLordships();
		P.p.getLogger().log(Level.INFO, "Loaded " + loadedLordships.size() + " Lordships to memory.");
		
		// Add the DEFAULT Lordship, which will be the Lordship of all non-lordship players
		Lordship lordship = new Lordship("DEFAULT", 0, new LazyLocation(), new HashMap<LPlayer, Byte>());
		loadedLordships.put(lordship.getID(), lordship);
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

	/*
	 * Getting a specific Lordship
	 */

	public Lordship getByID(String ID) {
		return loadedLordships.get(ID);
	}
	
	public Lordship getDefault() {
		return loadedLordships.get("DEFAULT");
	}

	/*
	 * Get all Lordships
	 */

	public Map<String, Lordship> getLoadedLordships() {
		return loadedLordships;
	}
	
	/*
	 * Utilities
	 */
	
	public void createLordship(LPlayer lPlayer) {
		if (lPlayer.getLordship().getID() != "DEFAULT") {
			return;
		}
		// Create the Lordship
		Map<LPlayer, Byte> newMembers = new HashMap<LPlayer, Byte>();
		newMembers.put(lPlayer, (byte) 0);
		Lordship lordship = new Lordship(lPlayer.getUUID(), 1, new LazyLocation(lPlayer.getPlayer().getLocation()), newMembers);
		// Load it
		loadLordship(lordship);
	}

}
