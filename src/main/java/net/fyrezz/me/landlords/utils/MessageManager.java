package net.fyrezz.me.landlords.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import net.fyrezz.me.landlords.P;

public class MessageManager {
	
	public MessageManager() {
		
	}
	
	public void bc(String path) {
		P.p.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', P.p.getLang().getString("prefix")) + ChatColor.RESET
				+ " " + ChatColor.translateAlternateColorCodes('&', P.p.getLang().getString(path)));
	}
	
	public void error(String message) {
		P.p.getServer().getConsoleSender().sendMessage(ChatColor.DARK_RED + "{LL} " + ChatColor.RED + message);
		P.p.getServer().broadcast(message, "ll.admin");
	}
	
	public static void msg(Player player, String path) {
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', P.p.getLang().getString("prefix")) + ChatColor.RESET
				+ " " + ChatColor.translateAlternateColorCodes('&', P.p.getLang().getString(path)));
	}

}
