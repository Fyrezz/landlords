package net.fyrezz.me.landlords;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class LPlayer {

	private String StoredUUID;
	private String name;

	public LPlayer(String UUID, String name) {
		this.StoredUUID = UUID;
		this.name = name;
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

	public void setName(String newName) {
		name = newName;
	}
}
