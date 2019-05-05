package net.fyrezz.me.landlords;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import net.fyrezz.me.landlords.utils.LUUID;

public class Lordship {
	
	private Player lord;
	private LUUID id;
	private int level;
	private Location homeblock;
	private List<Player> members = new ArrayList<Player>();
	
	public Lordship(Player lord, int level, Location homeblock, List<Player> members) {
		this.lord = lord;
		this.id = new LUUID(lord);
		this.level = level;
		this.homeblock = homeblock;
		this.members = members;
	}
	
	public Player getLord() {
		return id.getPlayer();
	}
	
	public Location getHomeblock() {
		return homeblock;
	}
	
	public List<Player> getMembers(){
		return members;
	}
	
	public LUUID getLUUID() {
		return id;
	}
	
	public int getLevel() {
		return level;
	}

}
