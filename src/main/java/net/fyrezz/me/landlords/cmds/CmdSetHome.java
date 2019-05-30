package net.fyrezz.me.landlords.cmds;

import net.fyrezz.me.landlords.utils.RequirementState;

public class CmdSetHome extends LordshipCommand {
	
	public CmdSetHome() {
		super();
	}

	@Override
	public void addAliases() {
		this.aliases.add("sethome");
		this.aliases.add("sh");
	}

	@Override
	public void setRequirements() {
		this.commandRequirements.hasLordship = RequirementState.REQUIRED;
		this.commandRequirements.isPlayer = RequirementState.REQUIRED;
		this.commandRequirements.isInOwnLand = RequirementState.REQUIRED;
		this.commandRequirements.allowedRanks.remove((byte) 3);
		this.commandRequirements.allowedRanks.remove((byte) 2);
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
