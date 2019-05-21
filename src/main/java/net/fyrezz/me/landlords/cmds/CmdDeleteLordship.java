package net.fyrezz.me.landlords.cmds;

import net.fyrezz.me.landlords.utils.RequirementState;

public class CmdDeleteLordship extends LordshipCommand {

	@Override
	public void addAliases() {
		this.aliases.add("delete");
		this.aliases.add("del");
	}

	@Override
	public void setRequirements() {
		this.commandRequirements.hasLordship = RequirementState.REQUIRED;
		this.commandRequirements.allowedRanks.remove(Byte.valueOf((byte) 3));
		this.commandRequirements.allowedRanks.remove(Byte.valueOf((byte) 2));
		this.commandRequirements.allowedRanks.remove(Byte.valueOf((byte) 1));
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
