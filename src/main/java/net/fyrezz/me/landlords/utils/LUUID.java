package net.fyrezz.me.landlords.utils;

import java.util.UUID;

import org.bukkit.entity.Player;

public class LUUID {
	
	private Player player;
	private String luuid;
	
	public LUUID(Player player) {
		this.player = player;
		this.luuid = "L" + player.getUniqueId().toString();
	}
	
	public Player getPlayer() {
		return player;
	}

}