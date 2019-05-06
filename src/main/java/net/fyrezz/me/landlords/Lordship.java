package net.fyrezz.me.landlords;

import java.util.HashMap;
import java.util.Map;

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
	
	public Lordship(String id, int level, LazyLocation homeblock, LPlayer lord) {
		Map<LPlayer, Byte> members = new HashMap<LPlayer, Byte>();
		members.put(lord, (byte)0);
	}

	public LazyLocation getHomeblock() {
		return homeblock;
	}

	public Map<LPlayer, Byte> getMembers() {
		return members;
	}

	public String getID() {
		return id;
	}

	public int getLevel() {
		return level;
	}

	private void checkLordship() {
		// 0: Lord, 1: Access to everything, 2: Not access to lvl 1, 3: Not access to
		// lvl 1 + lvl 2
		if (members.isEmpty() | members.size() < 1 | members == null) {
			P.p.getMm().error("Error loading Lordship " + id + " members: It's empty!");
			return;
		}
		
		int count = 0;
		for (LPlayer lplayer : members.keySet()) {
			if (members.get(lplayer) == 0) {
				count++;
			}
		}
		
		if (count != 1) {
			P.p.getMm().error("Error loading Lordship " + id + " members: Incorrect Lord!");
		}
	}

	public void addMember(LPlayer lplayer, Byte rank) {
		if (members.containsKey(lplayer)) {
			P.p.getMm().error("Error adding member to Lordship " + id + ": Already a member!");
			return;
		}		
		members.put(lplayer, rank);
	}

}
