package net.fyrezz.me.landlords;

import java.util.UUID;

import org.bukkit.entity.Player;

public class LPlayer {
	
	private UUID uuid;
//	private boolean islord;
	
	public LPlayer(Player player, boolean islord) {
		this.uuid = player.getUniqueId();
//		this.islord = islord;
	}

}
