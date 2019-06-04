package net.fyrezz.me.landlords;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import net.fyrezz.me.landlords.utils.LazyLocation;

public class Lordship {
	private String id;
	private int gold;
	private LazyLocation homeblock;
	private Map<LPlayer, Byte> members = new HashMap<LPlayer, Byte>();
	private int side;
	private LazyLocation centerblock;

	public Lordship(String id, int gold, LazyLocation homeblock, Map<LPlayer, Byte> members, int side, LazyLocation centerblock) {
		/* The Lordship's ID is always the Lord's UUID */
		this.id = id;
		this.gold = gold;
		this.homeblock = homeblock;
		this.members = members;
		this.side = side;
		this.centerblock = centerblock;
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
	
	public int getSide() {
		return side;
	}
	
	public LazyLocation getCenterBlock() {
		return centerblock;
	}

	public void setHomeblock(LazyLocation location) {
		homeblock = location;
	}
	
	public void setGold(int amount) {
		gold = amount;
	}
	
	public void setSide(int amount) {
		side = amount;
	}
	
	public void setCenterBlock(LazyLocation location) {
		centerblock = location;
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
	
	public int getArea() {
		return side * side;
	}
	
	public boolean isProtected() {
		int cost = getArea() * P.p.getConfig().getInt("blockcost");
		return gold >= cost;
	}
	
	public boolean canAffordExpansion(int expansion) {
		int cost = (side + expansion) * (side + expansion) * P.p.getConfig().getInt("blockcost");
		return gold >= cost;
	}
	
	public double getMaxX() {
		return (int) centerblock.getLocation().getX() + (side / 2) - 1;
	}
	
	public double getMinX() {
		return (int) centerblock.getLocation().getX() - (side / 2) + 1;
	}
	
	public double getMaxZ() {
		return (int) centerblock.getLocation().getZ() + (side / 2) - 1;
	}
	
	public double getMinZ() {
		return (int) centerblock.getLocation().getZ() - (side / 2) + 1;
	}
	
	public boolean isInsideLand(Location loc) {
		int maxX = (int) getMaxX();
		int minX = (int)  getMinX();
		int maxZ = (int)  getMaxZ();
		int minZ = (int)  getMinZ();
		
		return ((int) loc.getX() <= maxX && (int) loc.getX() >= minX && (int) loc.getZ() <= maxZ && (int) loc.getZ() >= minZ);
	}
	
	public boolean isInsideLand(LazyLocation lazyLoc) {
		return isInsideLand(lazyLoc.getLocation());
	}
	
	public boolean isInsideLand(LPlayer lPlayer) {
		return isInsideLand(lPlayer.getPlayer().getLocation());
	}
	
	public boolean isInsideLand(Player player) {
		return isInsideLand(player.getLocation());
	}
	
	public double distanceFromCenterTo(LazyLocation loc) {
		double dx = loc.getX() - this.getCenterBlock().getX();
		double dz = loc.getZ() - this.getCenterBlock().getZ();
		return Math.sqrt(dx * dx + dz * dz);
	}
	
	public List<LazyLocation> getBoundaries(){
		final int maxX = (int) getMaxX();
		final int minX = (int)  getMinX();
		final int maxZ = (int)  getMaxZ();
		final int minZ = (int)  getMinZ();
		final String worldString = this.getCenterBlock().getWorldName();
		
		List<LazyLocation> boundaryLocs = new ArrayList<LazyLocation>();
		
		for (int i = minZ; i == maxZ; i++) {
			boundaryLocs.add(new LazyLocation(worldString, maxX, i));
			boundaryLocs.add(new LazyLocation(worldString, minX, i));
		}
		
		for (int i = minX; i == maxX; i++) {
			boundaryLocs.add(new LazyLocation(worldString, i, maxZ));
			boundaryLocs.add(new LazyLocation(worldString, i, minZ));
		}
		
		return boundaryLocs;
	}
	
	public void showBoundaries(LPlayer lPlayer) {
		final World world = Bukkit.getWorld(this.getCenterBlock().getWorldName());
		
		for (LazyLocation lazyLoc : getBoundaries()) {
			world.spawnEntity(lazyLoc.getLocation(), EntityType.FIREWORK);
		}
	}
}
