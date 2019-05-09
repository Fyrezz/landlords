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
	private LPlayer lPlayer;

	public CommandContent(CommandSender sender, List<String> args) {
		this.sender = sender;
		this.args = args;

		if (sender instanceof Player) {
			this.player = (Player) sender;
			this.lPlayer = P.p.getLPlayers().getByUUID(player.getUniqueId().toString());
		}
	}

	public boolean isPlayer() {
		if (player == null) {
			return false;
		}
		return true;
	}
	
	public CommandSender getSender() {
		return sender;
	}

	public Player getPlayer() {
		return player;
	}

	public LPlayer getLPlayer() {
		return lPlayer;
	}

	public String getArg(Integer index) {
		return args.get(index);
	}

}
