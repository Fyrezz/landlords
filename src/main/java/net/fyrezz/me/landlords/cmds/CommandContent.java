package net.fyrezz.me.landlords.cmds;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.fyrezz.me.landlords.LPlayer;
import net.fyrezz.me.landlords.P;

public class CommandContent {

	private CommandSender sender;
	private List<String> args;

	private LPlayer lPlayer;

	public CommandContent(CommandSender sender, List<String> args) {
		this.sender = sender;
		this.args = args;
		if (sender instanceof Player) {
			this.lPlayer = P.p.getLPlayers().getByUUID(((Player) sender).getUniqueId().toString());
		}
	}

	public CommandSender getSender() {
		return sender;
	}

	public LPlayer getLPlayer() {
		return lPlayer;
	}

	public List<String> getArgs() {
		return args;
	}

	public boolean isPlayer() {
		return sender instanceof Player;
	}

	/* Returns the subcommand, treated as commands "/l <command>" */
	public String getCommand() {
		return args.get(0);
	}

	public String getArg(Integer index) {
		/* The first arg (index 0) is after the subcommand */
		return args.get(index + 1);
	}

}
