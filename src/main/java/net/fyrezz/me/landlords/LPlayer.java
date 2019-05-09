package net.fyrezz.me.landlords;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.fyrezz.me.landlords.utils.LazyLocation;

public class LPlayer {

	private String StoredUUID;
	private String name;
	private LazyLocation lastLocation;

	public LPlayer(String UUID, String name, LazyLocation lastLocation) {
		this.StoredUUID = UUID;
		this.name = name;
		this.lastLocation = lastLocation;
	}

	public String getUUID() {
		return StoredUUID;
	}

	public String getName() {
		return name;
	}

	public Player getPlayer() {
		return Bukkit.getPlayer(UUID.fromString(StoredUUID));
	}
	
	public LazyLocation getLastLocation() {
		return lastLocation;
	}
	
	public boolean isOnline() {
		if (P.p.getServer().getPlayer(UUID.fromString(StoredUUID)) != null) {
			return true;
		}
		return false;
	}

	public void setName(String newName) {
		name = newName;
	}
	
	public void setLastLocation(LazyLocation location) {
		lastLocation = location;
	}
}
