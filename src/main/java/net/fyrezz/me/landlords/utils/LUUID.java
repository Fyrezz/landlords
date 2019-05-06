package net.fyrezz.me.landlords.utils;

import java.util.UUID;

import org.bukkit.entity.Player;

import net.fyrezz.me.landlords.LPlayer;

public class LUUID {
	
	private String luuid;
	
	public LUUID(LPlayer lplayer) {
		this.luuid = "L" + lplayer.getStoredUuid().toString();
	}
	
	public String getString() {
		return luuid;
	}

}