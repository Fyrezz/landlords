package net.fyrezz.me.landlords.cmds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.fyrezz.me.landlords.P;

public class CommandManager implements CommandExecutor {
	
	private Map<String, LordshipCommand> lordshipCommands;
	
	private LordshipCommand cmdCreateLordship;
	
	public CommandManager() {
		lordshipCommands = new HashMap<String, LordshipCommand>();
		cmdCreateLordship = new CmdCreateLordship();
		lordshipCommands.put(cmdCreateLordship.getCommand(), cmdCreateLordship);
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		CommandContent commandContent = new CommandContent(sender, new ArrayList<String>(Arrays.asList(args)));
		LordshipCommand lordshipCommand = lordshipCommands.get(commandContent.getSubCommand());
		
		if (lordshipCommand != null) {
			if (lordshipCommand.check(commandContent)) {
				lordshipCommand.perform(commandContent);
			}
		} else {
			sender.sendMessage("NO EXISTE");
		}
		return true;
	}

}
