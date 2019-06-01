package net.fyrezz.me.landlords;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import net.fyrezz.me.landlords.utils.LazyLocation;

public class Lordship {
	private String id;
	private int gold;
	private LazyLocation homeblock;
	private Map<LPlayer, Byte> members = new HashMap<LPlayer, Byte>();

	public Lordship(String id, int gold, LazyLocation homeblock, Map<LPlayer, Byte> members) {
		/* The Lordship's ID is always the Lord's UUID */
		this.id = id;
		this.gold = gold;
		this.homeblock = homeblock;
		this.members = members;
	}

	public LazyLocation getHomeblock() {
		return homeblock;
	}

	public Map<LPlayer, Byte> getMembers() {
		return members;
	}

	public List<LPlayer> getMemberList() {
		return new ArrayList<LPlayer>(members.keySet());
	}

	public String getID() {
		return id;
	}

	public int getGold() {
		return gold;
	}

	public Byte getRank(LPlayer lPlayer) {
		return members.get(lPlayer);
	}

	public void setHomeblock(LazyLocation location) {
		homeblock = location;
	}
	
	public void setGold(int amount) {
		gold = amount;
	}
	
	public void addGold(int amount) {
		gold += amount;
	}
	
	public void removeGold(int amount) {
		gold -= amount;
	}
	
	public LPlayer getLPlayerFromUUID(String UUID) {
		for (LPlayer lPlayer : members.keySet()) {
			if (lPlayer.getUUID() == UUID) {
				return lPlayer;
			}
		}
		return null;
	}

	public LPlayer getLord() {
		for (LPlayer lPlayer : members.keySet()) {
			if (members.get(lPlayer) == 0) {
				return lPlayer;
			}
		}
		check();
		return null;
	}

	public LPlayer getLPlayerFromName(String Name) {
		for (LPlayer lPlayer : members.keySet()) {
			if (lPlayer.getName() == Name) {
				return lPlayer;
			}
		}
		return null;
	}

	public void check() {
		// 0: Lord, 1: Access to everything, 2: Not access to lvl 1, 3: Not access to
		// lvl 1 + lvl 2
		if (this.getID() == "DEFAULT") {
			return;
		}

		if (members.isEmpty() | members.size() < 1 | members == null) {
			P.p.getLogger().log(Level.WARNING, "Error with Lordship " + id + " members: It's empty!");
			return;
		}

		int count = 0;
		for (LPlayer lPlayer : members.keySet()) {
			lPlayer.setLordship(this);
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

	public void addMember(LPlayer lPlayer) {
		members.put(lPlayer, (byte) 3);
	}

	public void removeMember(LPlayer lPlayer) {
		members.remove(lPlayer);
	}
	
	public void message(String path) {
		for (LPlayer lPlayer : members.keySet()) {
			P.p.getMM().msg(lPlayer, path);
		}
	}
	
	public void message(String path, List<String> args) {
		for (LPlayer lPlayer : members.keySet()) {
			P.p.getMM().msg(lPlayer, path);
		}
	}

	public void setRank(LPlayer lPlayer, Byte rank) {
		members.put(lPlayer, rank);
	}

}
