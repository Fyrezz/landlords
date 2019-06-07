package net.fyrezz.me.landlords.utils;

import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.fyrezz.me.landlords.LPlayer;
import net.fyrezz.me.landlords.Lordship;
import net.fyrezz.me.landlords.P;

public class MessageManager {

	public void msg(LPlayer lPlayer, String path, Map<String, String> vars) {
		String base = P.p.getLang().getString(path);
		for (String var : vars.keySet()) {
			base = base.replace("%"+var+"%", vars.get(var));
		}
		lPlayer.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', P.p.getLang().getString("prefix"))
				+ ChatColor.RESET + ChatColor.translateAlternateColorCodes('&', base));
	}

	public void msg(CommandSender sender, String path, Map<String, String> vars) {
		String base = P.p.getLang().getString(path);
		for (String var : vars.keySet()) {
			base.replace("%"+var+"%", vars.get(var));
		}
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', P.p.getLang().getString("prefix"))
				+ ChatColor.RESET + ChatColor.translateAlternateColorCodes('&', base));
	}

	public void msg(LPlayer lPlayer, String path) {
		String base = P.p.getLang().getString(path);
		lPlayer.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', P.p.getLang().getString("prefix"))
				+ ChatColor.RESET + ChatColor.translateAlternateColorCodes('&', base));
	}
	
	public void msg(Player player, String path, Map<String, String> vars) {
		String base = P.p.getLang().getString(path);
		for (String var : vars.keySet()) {
			base = base.replace("%"+var+"%", vars.get(var));
		}
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', P.p.getLang().getString("prefix"))
				+ ChatColor.RESET + ChatColor.translateAlternateColorCodes('&', base));
	}

	public void msg(CommandSender sender, String path) {
		String base = P.p.getLang().getString(path);
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', P.p.getLang().getString("prefix"))
				+ ChatColor.RESET + ChatColor.translateAlternateColorCodes('&', base));
	}
	
	public void lordshipMsg(Lordship lordship, String path, Map<String, String> vars) {
		for (LPlayer lPlayer : lordship.getMemberList()) {
			msg(lPlayer, path, vars);
		}
	}
	
	public void lordshipMsg(Lordship lordship, String path) {
		for (LPlayer lPlayer : lordship.getMemberList()) {
			msg(lPlayer, path);
		}
	}
	
	public void undefinedMsg(LPlayer lPlayer, String message) {
		lPlayer.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', P.p.getLang().getString("prefix"))
				+ ChatColor.RESET + ChatColor.translateAlternateColorCodes('&', message));
	}

	public void undefinedMsg(CommandSender sender, String message) {
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', P.p.getLang().getString("prefix"))
				+ ChatColor.RESET + ChatColor.translateAlternateColorCodes('&', message));
	}

	public void broadcast(String path, Map<String, String> vars) {
		String base = P.p.getLang().getString(path);
		
		for (String var : vars.keySet()) {
			base = base.replace("%"+var+"%", vars.get(var));
		}
		
		for (Player player : P.p.getServer().getOnlinePlayers()) {
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', P.p.getLang().getString("prefix"))
				+ ChatColor.RESET + ChatColor.translateAlternateColorCodes('&', base));
		}
	}
	
	public void broadcast(String path) {
		for (Player player : P.p.getServer().getOnlinePlayers()) {
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', P.p.getLang().getString("prefix"))
				+ ChatColor.RESET + ChatColor.translateAlternateColorCodes('&', P.p.getLang().getString(path)));
		}
	}

	public void broadcastUndefined(String message) {
		for (Player player : P.p.getServer().getOnlinePlayers()) {
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', P.p.getLang().getString("prefix"))
				+ ChatColor.RESET + ChatColor.translateAlternateColorCodes('&', message));
		}
	}

}
