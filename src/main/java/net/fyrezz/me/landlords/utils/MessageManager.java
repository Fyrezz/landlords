package net.fyrezz.me.landlords.utils;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.fyrezz.me.landlords.P;

public class MessageManager {
	
	public MessageManager() {
		
	}
	
	public void bc(String path, List<String> info) {
		P.p.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', P.p.getLang().getString("prefix")) + ChatColor.RESET
				+ " " + ChatColor.translateAlternateColorCodes('&', P.p.getLang().getString(path)));
	}
	
	public void admin(String message) {
		P.p.getServer().getConsoleSender().sendMessage(ChatColor.DARK_RED + "{LL} " + ChatColor.RED + message);
		P.p.getServer().broadcast(message, "ll.admin");
	}
	
	public void msg(Player player, String path) {
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', P.p.getLang().getString("prefix")) + ChatColor.RESET
				+ " " + ChatColor.translateAlternateColorCodes('&', P.p.getLang().getString(path)));
	}
	
	public void msg(CommandSender sender, String path) {
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', P.p.getLang().getString("prefix")) + ChatColor.RESET
				+ " " + ChatColor.translateAlternateColorCodes('&', P.p.getLang().getString(path)));
	}
	
	/*
	 * For non configurable messages
	 */
	
	public void undefinedMsg(CommandSender sender, String path) {
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', P.p.getLang().getString("prefix")) + ChatColor.RESET
				+ " " + ChatColor.translateAlternateColorCodes('&', P.p.getLang().getString(path)));
	}

}
