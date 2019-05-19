package net.fyrezz.me.landlords.cmds;

import java.util.ArrayList;
import java.util.List;

import net.fyrezz.me.landlords.utils.RequirementState;

public abstract class LordshipCommand {
	
	public List<String> aliases = new ArrayList<String>();
	public CommandRequirements commandRequirements = new CommandRequirements();
	public String permission;
	
	public LordshipCommand() {
		addAliases();
		setRequirements();
		setPermission();
	}
	
	/*
	 * Get & Set
	 */
	
	public List<String> getAliases(){
		return aliases;
	}
	
	/*
	 * Methods for commands
	 */
	
	public abstract void addAliases();
	
	public abstract void setRequirements();
	
	public abstract void setPermission();
	
	public abstract void perform(CommandContent commandContent);
	
	/*
	 * Class methods
	 */
	
	public void execute(CommandContent commandContent) {
		if (commandContent.isPlayer() && (commandRequirements.isPlayer == RequirementState.EXCLUDED)) {
			return;
		}
		if (!commandContent.isPlayer() && (commandRequirements.isPlayer == RequirementState.REQUIRED)) {
			return;
		}
		
		if (commandContent.getLPlayer().hasLordship() && (commandRequirements.hasLordship == RequirementState.EXCLUDED)) {
			return;
		}
		if (!commandContent.getLPlayer().hasLordship() && (commandRequirements.hasLordship == RequirementState.REQUIRED)) {
			return;
		}
		
		if (!commandRequirements.allowedRanks.contains(commandContent.getLPlayer().getLordship().getRank(commandContent.getLPlayer()))) {
			return;
		}
		perform(commandContent);
	}

}
