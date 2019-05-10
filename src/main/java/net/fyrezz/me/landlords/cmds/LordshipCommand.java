package net.fyrezz.me.landlords.cmds;

import java.util.ArrayList;
import java.util.List;

public abstract class LordshipCommand {
	
	public String command;
	public List<String> aliases;
	public boolean playerOnly;
	public boolean requireLordship;
	public boolean requireNoLordship;
	public String permission;
	
	public LordshipCommand() {
		this.aliases = new ArrayList<String>();
		this.playerOnly = false;
	}
	
	public abstract void perform(CommandContent commandContent);
	
	public boolean check(CommandContent commandContent) {
		if (playerOnly && !commandContent.isPlayer()) {
			commandContent.getSender().sendMessage("Player command only!");
			return false;
		}
		
		if (requireLordship && !commandContent.getLPlayer().hasLordship()) {
			commandContent.getSender().sendMessage("You need a Lordship to perform this command!");
			return false;
		}
		
		if (requireNoLordship && commandContent.getLPlayer().hasLordship()) {
			commandContent.getSender().sendMessage("You can't perform this command because you're in a Lordship!");
			return false;
		}
		
		/*
		 * TODO: Permission check
		 */
		
		return true;
	}
	
	public String getCommand() {
		return command;
	}

}
