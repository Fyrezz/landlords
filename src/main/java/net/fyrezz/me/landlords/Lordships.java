package net.fyrezz.me.landlords;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import net.fyrezz.me.landlords.utils.LazyLocation;

public class Lordships {

	private Map<String, Lordship> loadedLordships = new HashMap<String, Lordship>();

	public Lordships() {
	}

	/*
	 * Get & Set
	 */

	public Map<String, Lordship> getLoadedLordships() {
		return loadedLordships;
	}

	/*
	 * Lordship memory management
	 */

	public void load() {
		loadedLordships = P.p.getDB().getSavedLordships();
		P.p.getLogger().log(Level.INFO, "Loaded " + loadedLordships.size() + " Lordships to memory.");

		// Add the DEFAULT Lordship, which will be the Lordship of all non-lordship
		// players
		Lordship lordship = new Lordship("DEFAULT", 0, new LazyLocation(), new HashMap<LPlayer, Byte>());
		loadLordship(lordship);
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
	 * Utils
	 */

	public void createLordship(LPlayer lPlayer) {
		if (lPlayer.getLordship().getID() != "DEFAULT") {
			P.p.getLogger().log(Level.WARNING, "Tried to create a Lordship for a player with Lordship!");
			return;
		}

		// Create the Lordship
		Map<LPlayer, Byte> newMembers = new HashMap<LPlayer, Byte>();
		newMembers.put(lPlayer, (byte) 0);
		Lordship lordship = new Lordship(lPlayer.getUUID(), 1, new LazyLocation(lPlayer.getPlayer().getLocation()),
				newMembers);

		// Load it
		loadLordship(lordship);
	}

	public Lordship getDefault() {
		return loadedLordships.get("DEFAULT");
	}

	public Lordship getByID(String ID) {
		return loadedLordships.get(ID);
	}

}
