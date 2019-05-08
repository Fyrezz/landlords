package net.fyrezz.me.landlords;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import net.fyrezz.me.landlords.utils.LazyLocation;

public class Lordship {

	private String id;
	private int level;
	private LazyLocation homeblock;
	private Map<LPlayer, Byte> members = new HashMap<LPlayer, Byte>();

	public Lordship(String id, int level, LazyLocation homeblock, Map<LPlayer, Byte> members) {
		// The Lordship's ID is always the Lord's UUID
		this.id = id;
		this.level = level;
		this.homeblock = homeblock;
		this.members = members;

		checkLordship();
	}

	public LazyLocation getHomeblock() {
		return homeblock;
	}

	public void setHomeblock(LazyLocation location) {
		// Security check
		if (location.getLocation() == null) {
			P.p.getLogger().log(Level.WARNING, "Error setting Homeblock of Lordship " + id + ": NULL Location");
			return;
		}
		homeblock = location;
	}

	/*
	 * Getting info
	 */

	public Map<LPlayer, Byte> getRankedMembers() {
		return members;
	}

	public List<LPlayer> getMembers() {
		return (List) members.keySet();
	}

	public String getID() {
		return id;
	}

	public int getLevel() {
		return level;
	}

	public LPlayer getLord() {
		for (LPlayer lPlayer : members.keySet()) {
			if (members.get(lPlayer) == 0) {
				return lPlayer;
			}
		}
		checkLordship();
		return null;
	}

	public Byte getRank(LPlayer lPlayer) {
		return members.get(lPlayer);
	}

	/*
	 * Security checks
	 */

	private void checkLordship() {
		// 0: Lord, 1: Access to everything, 2: Not access to lvl 1, 3: Not access to
		// lvl 1 + lvl 2
		if (members.isEmpty() | members.size() < 1 | members == null) {
			P.p.getLogger().log(Level.WARNING, "Error with Lordship " + id + " members: It's empty!");
			return;
		}

		int count = 0;
		for (LPlayer lPlayer : members.keySet()) {
			if (members.get(lPlayer) == 0) {
				count++;
			}
		}

		if (count != 1) {
			P.p.getLogger().log(Level.WARNING, "Error with Lordship " + id + " members: Incorrect Lord!");
		}
	}

	public boolean containsMember(LPlayer lPlayer) {
		return (members.containsKey(lPlayer));
	}

	public boolean isFull() {
		return (members.size() >= P.p.getConfig().getInt("maxmembers"));
	}

	/*
	 * Adding and removing members
	 */

	public void addMember(LPlayer lPlayer, Byte rank) {
		if (containsMember(lPlayer)) {
			P.p.getLogger().log(Level.WARNING, "Error adding member to Lordship " + id + ": Already a member!");
			return;
		}

		if (isFull()) {
			P.p.getLogger().log(Level.WARNING, "Error adding member to Lordship " + id + ": Max members reached!");
			return;
		}
		members.put(lPlayer, rank);
	}

	public void removeMember(LPlayer lPlayer, Byte rank) {
		if (!containsMember(lPlayer)) {
			P.p.getLogger().log(Level.WARNING, "Error removing member from Lordship " + id + ": Not a member!");
			return;
		}

		if (lPlayer == getLord()) {
			P.p.getLogger().log(Level.WARNING, "Error removing member from Lordship " + id + ": He is the Lord!");
		}

		if (members.size() < 2) {
			P.p.getLogger().log(Level.WARNING,
					"Error removing member from Lordship " + id + ": Lord is already the only member!");
		}
		members.remove(lPlayer);
	}

	/*
	 * Setting ranks
	 */

	public void setRank(LPlayer lPlayer, Byte rank) {
		if (!containsMember(lPlayer)) {
			P.p.getLogger().log(Level.WARNING, "Error setting rank in Lordship " + id + ": LPlayer not a member!");
			return;
		}
		if (rank < 1 | rank > 3) { // Cannot set Lord | Min rank is 3
			P.p.getLogger().log(Level.WARNING, "Error setting rank in Lordship " + id + ": Invalid rank!");
			return;
		}
		members.put(lPlayer, rank);
	}

}
