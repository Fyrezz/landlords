package net.fyrezz.me.landlords.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import net.fyrezz.me.landlords.P;

public class LazyLocation {

	private String worldName;
	private double x;
	private double y;
	private double z;
	private float yaw;
	private float pitch;
	private transient Location location = null;
	
	public LazyLocation(Location loc) {
		this(loc.getWorld().getName(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
	}

	public LazyLocation(final String worldName, final double x, final double y, final double z, final float yaw,
			final float pitch) {
		this.worldName = worldName;
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
	}
	
	public LazyLocation(final String worldName, final double x, final double y, final double z) {
		this(worldName, x, y, z, 0, 0);
	}
	
	public Location getLocation() {
		initLocation();
		return location;
	}
	
	private void initLocation() {
		if (location != null) {
			return;
		}
		
		World world = Bukkit.getWorld(worldName);
		if (world == null) {
			P.p.getMm().error("Error loading location's world.");
			return;
		}
		
		location = new Location(world, x, y, z, yaw, pitch);
	}
	
}
