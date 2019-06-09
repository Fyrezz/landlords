package net.fyrezz.me.landlords.utils;

import java.util.logging.Level;

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

	public LazyLocation(Location loc) {
		this(loc.getWorld().getName(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
	}

	public LazyLocation() {
		this("world", 0, 0, 0);
	}

	public LazyLocation(int x, int z) {
		this("world", x, 0, z);
	}

	public LazyLocation(String world, int x, int z) {
		this(world, x, 0, z);
	}

	public LazyLocation(String world, double x, double z) {
		this(world, x, 0, z);
	}
	
	public String getWorldName() {
		return worldName;
	}
	
	public double getX() {
		return x;
	}
	
	public int getBlockX() {
		return floor(x);
	}
	
	public int getBlockY() {
		return floor(y);
	}

	public int getBlockZ() {
		return floor(z);
	}
	
	public double getY() {
		return y;
	}
	
	public double getZ() {
		return z;
	}
	
	public float getYaw() {
		return yaw;
	}
	
	public float getPitch() {
		return pitch;
	}
	
	public void setY(double newY) {
		y = newY;
	}

	public Location getLocation() {
		initLocation();
		return location;
	}
	
    private int floor(double num) {
        final int floor = (int) num;
        return floor == num ? floor : floor - (int) (Double.doubleToRawLongBits(num) >>> 63);
    }
	
	private void initLocation() {
		if (location != null) {
			return;
		}
		World world = Bukkit.getWorld(worldName);
		if (world == null) {
			P.p.getLogger().log(Level.WARNING, "Error loading location's world.");
			return;
		}
		location = new Location(world, x, y, z, yaw, pitch);
	}
}
