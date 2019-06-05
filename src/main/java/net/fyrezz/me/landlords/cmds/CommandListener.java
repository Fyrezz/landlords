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
	
	private LordshipCommand cmdCreate = new CmdCreate();
	private LordshipCommand cmdDeinvite = new CmdDeinvite();
	private LordshipCommand cmdDelete = new CmdDelete();
	private LordshipCommand cmdDeposit = new CmdDeposit();
	private LordshipCommand cmdExpand = new CmdExpand();
	private LordshipCommand cmdHelp = new CmdHelp();
	private LordshipCommand cmdHome = new CmdHome();
	private LordshipCommand cmdInvite = new CmdInvite();
	private LordshipCommand cmdJoin = new CmdJoin();
	private LordshipCommand CmdSetCenter = new CmdSetCenter();
	private LordshipCommand cmdSetHome = new CmdSetHome();
	private LordshipCommand CmdShowLimits = new CmdShowLimits();
	private LordshipCommand cmdShrink = new CmdShrink();
	private LordshipCommand cmdVault = new CmdVault();
	private LordshipCommand cmdWithdraw = new CmdWithdraw();
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		CommandContent commandContent = new CommandContent(sender, new ArrayList<String>(Arrays.asList(args)));
		
		if (args.length == 0) {
			cmdHelp.execute(commandContent);
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
