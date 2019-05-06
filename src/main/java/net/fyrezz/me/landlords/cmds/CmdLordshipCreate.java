package net.fyrezz.me.landlords.cmds;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.fyrezz.me.landlords.LPlayer;
import net.fyrezz.me.landlords.Lordship;
import net.fyrezz.me.landlords.P;

public class CmdLordshipCreate implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			LPlayer lplayer = new LPlayer(player.getUniqueId().toString());
			P.p.getDb().addLordship(new Lordship(lplayer, lplayer.get));
		}
		return false;
	}

}
