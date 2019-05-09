package net.fyrezz.me.landlords.cmds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.fyrezz.me.landlords.LPlayer;
import net.fyrezz.me.landlords.Lordship;
import net.fyrezz.me.landlords.P;
import net.fyrezz.me.landlords.utils.LazyLocation;

public class LordshipCommands implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		CommandContent commandContent = new CommandContent(sender, new ArrayList<String>(Arrays.asList(args)));
		if (!commandContent.isPlayer()) {
			sender.sendMessage("Player command only!");
			return true;
		}
		if (commandContent.getArg(0).equalsIgnoreCase("create")) {
			if (commandContent.getLPlayer().getLordship() != null && commandContent.getLPlayer().getLordship().getID() != "DEFAULT") {
				commandContent.getPlayer().sendMessage("Ya tienes!");
				return true;
			} else {

				Map<LPlayer, Byte> members = new HashMap<LPlayer, Byte>();

				members.put(new LPlayer(commandContent.getPlayer().getUniqueId().toString(), commandContent.getPlayer().getName()), (byte)0);
				P.p.getLordships().loadLordship(new Lordship(commandContent.getPlayer().getUniqueId().toString(), 1, new LazyLocation(commandContent.getPlayer().getLocation()), members));
				commandContent.getPlayer().sendMessage("SI");
				return true;
			}
		}
		return false;
	}

}
