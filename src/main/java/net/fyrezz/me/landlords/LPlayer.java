package net.fyrezz.me.landlords;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.fyrezz.me.landlords.utils.LazyLocation;

public class LPlayer {

	private String StoredUUID;
	private String name;
	private Lordship lordship;

	public LPlayer(String UUID, String name) {
		this.StoredUUID = UUID;
		this.name = name;
		this.lordship = P.p.getLordships().getByID("DEFAULT");
	}
	
	/*
	 * Get & Set
	 */
	
	public Lordship getLordship() {
		return lordship;
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
	
	public void setLordship(Lordship newLordship) {
		this.lordship = newLordship;
	}
	
	/*
	 * Utils
	 */
	
	public boolean isOnline() {
		return (P.p.getServer().getPlayer(UUID.fromString(StoredUUID)).isOnline());
	}
	
	public boolean hasLordship() {
		return !(lordship.getID() == P.p.getLordships().getDefault().getID());
	}
}
