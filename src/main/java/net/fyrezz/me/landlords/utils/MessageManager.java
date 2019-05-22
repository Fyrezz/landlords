package net.fyrezz.me.landlords.utils;

import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import net.fyrezz.me.landlords.LPlayer;
import net.fyrezz.me.landlords.P;

public class MessageManager {

	public void msg(LPlayer lPlayer, String path, Map<String, String> vars) {
		String base = P.p.getLang().getString(path);
		for (String var : vars.keySet()) {
			base = base.replace("%"+var+"%", vars.get(var));
		}
		lPlayer.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', P.p.getLang().getString("prefix"))
				+ ChatColor.RESET + " " + ChatColor.translateAlternateColorCodes('&', base));
	}

	public void msg(CommandSender sender, String path, Map<String, String> vars) {
		String base = P.p.getLang().getString(path);
		for (String var : vars.keySet()) {
			base.replace("%"+var+"%", vars.get(var));
		}
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', P.p.getLang().getString("prefix"))
				+ ChatColor.RESET + " " + ChatColor.translateAlternateColorCodes('&', base));
	}

	public void msg(LPlayer lPlayer, String path) {
		String base = P.p.getLang().getString(path);
		lPlayer.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', P.p.getLang().getString("prefix"))
				+ ChatColor.RESET + " " + ChatColor.translateAlternateColorCodes('&', base));
	}

	public void msg(CommandSender sender, String path) {
		String base = P.p.getLang().getString(path);
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', P.p.getLang().getString("prefix"))
				+ ChatColor.RESET + " " + ChatColor.translateAlternateColorCodes('&', base));
	}

	/*
	 * For non configurable messages
	 */

	public void undefinedMsg(LPlayer lPlayer, String message) {
		lPlayer.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', P.p.getLang().getString("prefix"))
				+ ChatColor.RESET + " " + ChatColor.translateAlternateColorCodes('&', message));
	}

	public void undefinedMsg(CommandSender sender, String message) {
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', P.p.getLang().getString("prefix"))
				+ ChatColor.RESET + " " + ChatColor.translateAlternateColorCodes('&', message));
	}

}
