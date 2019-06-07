package net.fyrezz.me.landlords;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

import net.fyrezz.me.landlords.utils.LazyLocation;

public class Lordship {

	private String id;
	private int gold;
	private LazyLocation homeblock;
	private Map<LPlayer, Byte> members = new HashMap<LPlayer, Byte>();
	private int side;
	private LazyLocation centerblock;

	public Lordship(String id, int gold, LazyLocation homeblock, Map<LPlayer, Byte> members, int side,
			LazyLocation centerblock) {
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

	public LPlayer getLord() {
		for (LPlayer lPlayer : members.keySet()) {
			if (members.get(lPlayer) == 0) {
				return lPlayer;
			}
		}
		check();
		return null;
	}

	public void check() {
		if (members == null || members.isEmpty() | members.size() < 1) {
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

	public void message(String path, Map<String, String> vars) {
		for (LPlayer lPlayer : members.keySet()) {
			P.p.getMM().msg(lPlayer, path, vars);
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

	public int getMaxX() {
		return centerblock.getBlockX() + ((side - 1) / 2);
	}

	public int getMinX() {
		return centerblock.getBlockX() - ((side - 1) / 2);
	}

	public int getMaxZ() {
		return centerblock.getBlockZ() + ((side - 1) / 2);
	}

	public int getMinZ() {
		return centerblock.getBlockZ() - ((side - 1) / 2);
	}

	public boolean containsLazyLoc(LazyLocation lazyLoc) {
		return (lazyLoc.getBlockX() <= getMaxX() && lazyLoc.getBlockX() >= getMinX()
				&& lazyLoc.getBlockZ() <= getMaxZ() && lazyLoc.getBlockZ() >= getMinZ());
	}

	public double distanceFromCenterTo(LazyLocation lazyLoc) {
		double dx = lazyLoc.getX() - this.getCenterBlock().getX();
		double dz = lazyLoc.getZ() - this.getCenterBlock().getZ();
		return Math.sqrt(dx * dx + dz * dz);
	}

	/**
	 * Get the limits of the Lordship's land. It is increased by 1.5 on each
	 * direction to correctly show particles.
	 * 
	 * @return Lordship's limits + 1.5
	 */
	public List<LazyLocation> getBoundaries() {
		final String worldString = this.getCenterBlock().getWorldName();

		List<LazyLocation> boundaryLocs = new ArrayList<LazyLocation>();

		boundaryLocs.add(new LazyLocation(worldString, getMinX(), getMinZ()));
		boundaryLocs.add(new LazyLocation(worldString, getMinX(), getMaxZ() + 1));
		boundaryLocs.add(new LazyLocation(worldString, getMaxX() + 1, getMinZ()));
		boundaryLocs.add(new LazyLocation(worldString, getMaxX() + 1, getMaxZ() + 1));

		for (int i = getMinZ(); i <= getMaxZ(); i++) {
			boundaryLocs.add(new LazyLocation(worldString, getMaxX() + 1, i));
			boundaryLocs.add(new LazyLocation(worldString, getMinX(), i));
		}

		for (int i = getMinX(); i <= getMaxX(); i++) {
			boundaryLocs.add(new LazyLocation(worldString, i, getMaxZ() + 1));
			boundaryLocs.add(new LazyLocation(worldString, i, getMinZ()));
		}
		return boundaryLocs;
	}

	private Particle getParticle() {
		if (isProtected()) {
			return Particle.VILLAGER_HAPPY;
		}
		return Particle.BARRIER;
	}

	public void showBoundaries(LPlayer lPlayer) {
		final World world = Bukkit.getWorld(this.getCenterBlock().getWorldName());
		final List<LazyLocation> limits = getBoundaries();

		new BukkitRunnable() {
			int i = 15;

			public void run() {
				if (i <= 1) {
					this.cancel();
				}
				i--;
				for (int i = 1; i <= 255; i++) {
					for (LazyLocation lazyLoc : limits) {
						lazyLoc.setY((i + 0.5)); // +0.5 for visual purposes
						world.spawnParticle(getParticle(), lazyLoc.getX(), lazyLoc.getY(), lazyLoc.getZ(), 1);
					}
				}
			}
		}.runTaskTimer(P.p, 0, 5L);
	}

	/**
	 * Depending on their Gold, teleport time is different.
	 * 
	 * @return seconds for Lordship teleport time
	 */
	public int getTeleportSeconds() {
		int reduction = (int) Math.sqrt(gold);
		return (60 - reduction > 5) ? 60 - reduction : 10;
	}
}
