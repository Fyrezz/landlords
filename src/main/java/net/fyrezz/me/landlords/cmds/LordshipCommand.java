package net.fyrezz.me.landlords.cmds;

import java.util.ArrayList;
import java.util.List;

public abstract class LordshipCommand {
	
	public List<String> aliases = new ArrayList<String>();
	public CommandRequirements commandRequirements = new CommandRequirements();
	public String permission;
	
	public LordshipCommand() {
		addAliases();
		setRequirements();
	}
	
	/*
	 * Methods for commands
	 */
	
	public abstract void addAliases();
	
	public abstract void setRequirements();
	
	public abstract void setPermission();
	
	public abstract void perform(CommandContent commandContent);
	
	public void check() {
		
	}
	
	/*
	 * Get & Set
	 */
	
	public List<String> getAliases(){
		return aliases;
	}

}
