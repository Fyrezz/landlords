package net.fyrezz.me.landlords;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.fyrezz.me.landlords.utils.LUUID;
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
		// 0: Lord, 1: Access to everything, 2: Not access to lvl 1, 3: Not access to lvl 1 + lvl 2
		if (members.isEmpty() | members.size() < 1 | members == null ) {
			P.p.getMm().error("Error loading Lordship " + id + " members: It's empty!" );
		} else {
			if (!(members.containsValue(0))) {
				P.p.getMm().error("Error loading Lordship " + id + " members: No Lord inside members!");
			} else {
				int count = 0;
				for (LPlayer lplayer : members.keySet()) {
					
				}
			}
		}
	}

}
