package net.fyrezz.me.landlords.cmds;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.fyrezz.me.landlords.LPlayer;
import net.fyrezz.me.landlords.Lordship;
import net.fyrezz.me.landlords.P;
import net.fyrezz.me.landlords.utils.LazyLocation;

public class CmdLordshipCreate implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			LPlayer lplayer = new LPlayer(player.getUniqueId().toString());

			Map<LPlayer, Byte> members = new HashMap<LPlayer, Byte>();
			members.put(lplayer, (byte)0);
			P.p.getDb().addLordship(new Lordship(lplayer.getStoredUuid(), 1, new LazyLocation(player.getLocation()), members));
			P.p.getMm().error("CORRECTLY CREATED " + P.p.getDb().getLordships().toString());
		}
		return false;
	}

}
