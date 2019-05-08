package net.fyrezz.me.landlords.cmds;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.fyrezz.me.landlords.LPlayer;
import net.fyrezz.me.landlords.Lordship;
import net.fyrezz.me.landlords.P;

public class CommandContent {
	
	private CommandSender sender;
	private List<String> args;
	
	private Player player;
	private LPlayer lplayer;
	private Lordship lordship;
	private Byte rank;
	
	public CommandContent(CommandSender sender, List<String> args) {
		this.sender = sender;
		this.args = args;
		
		if (sender instanceof Player) {
			this.player = (Player) sender;
			this.lplayer = P.p.getLordships().getByPlayer(lPlayer)
			this.lordship = Lordships.getInstance().getBy
		}
	}

}
