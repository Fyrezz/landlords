package net.fyrezz.me.landlords.cmds;

import net.fyrezz.me.landlords.utils.RequirementState;

public class CmdHelp extends LordshipCommand {

	@Override
	public void addAliases() {
		this.aliases.add("help");
		this.aliases.add("h");
	}

	@Override
	public void setRequirements() {
		this.commandRequirements.isPlayer = RequirementState.REQUIRED;
	}

	@Override
	public void setPermission() {
		this.permission = "landlords.player";
	}

	@Override
	public void perform(CommandContent commandContent) {
		// TODO Auto-generated method stub
		
	}

}
