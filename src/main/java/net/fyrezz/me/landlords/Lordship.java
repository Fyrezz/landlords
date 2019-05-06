package net.fyrezz.me.landlords;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

import net.fyrezz.me.landlords.utils.LUUID;
import net.fyrezz.me.landlords.utils.LazyLocation;

public class Lordship {

	private LPlayer lord;
	private LUUID id;
	private int level;
	private LazyLocation homeblock;
	private List<LPlayer> members = new ArrayList<LPlayer>();

	public Lordship(LPlayer lord, int level, LazyLocation homeblock, List<LPlayer> members) {
		this.lord = lord;
		this.id = new LUUID(lord);
		this.level = level;
		this.homeblock = homeblock;
		this.members = members;
	}

	public LPlayer getLord() {
		return lord;
	}

	public LazyLocation getHomeblock() {
		return homeblock;
	}

	public List<LPlayer> getMembers() {
		return members;
	}

	public List<String> getMembersStringUuids() {
		if (members.size() < 1 | members.isEmpty() | members == null) {
			P.p.getMm().error("Error loading members in " + lord.getNick(););
		}
	}

	public LUUID getLUUID() {
		return id;
	}

	public int getLevel() {
		return level;
	}

}
