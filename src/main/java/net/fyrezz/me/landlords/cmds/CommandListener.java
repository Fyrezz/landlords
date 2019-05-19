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
	
	public static Map<String, LordshipCommand> lordshipCommands = new HashMap<String, LordshipCommand>();
	
	private LordshipCommand cmdCreateLordship = new CmdCreateLordship();
	private LordshipCommand cmdHelp = new CmdHelp();
	
	/*
	 * Command listener
	 */
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		CommandContent commandContent = new CommandContent(sender, new ArrayList<String>(Arrays.asList(args)));
		
		if (args.length == 0) {
			cmdHelp.perform(commandContent);
			return true;
		}
		
		LordshipCommand lordshipCommand = lordshipCommands.get(commandContent.getCommand());
		
		if (lordshipCommand != null) {
			lordshipCommand.execute(commandContent);
		} else {
			P.p.getMM().msg(sender, "unknowncommand");
		}
		
		return true;
	}

}