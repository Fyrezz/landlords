package net.fyrezz.me.landlords.cmds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.fyrezz.me.landlords.P;

public class CommandListener implements CommandExecutor {
	
	private Map<String, LordshipCommand> lordshipCommands = new HashMap<String, LordshipCommand>();
	
	// All command classes
	private LordshipCommand cmdCreateLordship;
	
	public CommandListener() {
		lordshipCommands.put(cmdCreateLordship.getCommand(), new CmdCreateLordship());
	}
	
	/*
	 * Command listener
	 */
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		CommandContent commandContent = new CommandContent(sender, new ArrayList<String>(Arrays.asList(args)));
		LordshipCommand lordshipCommand = lordshipCommands.get(commandContent.getCommand());
		
		if (lordshipCommand != null) {
			lordshipCommand.execute(commandContent);
		} else {
			
		}
		return true;
	}

}
