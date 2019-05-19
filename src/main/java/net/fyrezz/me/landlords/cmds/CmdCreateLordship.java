package net.fyrezz.me.landlords.cmds;

import net.fyrezz.me.landlords.P;
import net.fyrezz.me.landlords.utils.RequirementState;

public class CmdCreateLordship extends LordshipCommand {

	@Override
	public void addAliases() {
		this.aliases.add("create");
	}

	@Override
	public void setRequirements() {
		this.commandRequirements.isPlayer = RequirementState.REQUIRED;
		this.commandRequirements.hasLordship = RequirementState.EXCLUDED;
	}

	@Override
	public void setPermission() {
		this.permission = "landlords.player";
	}

	@Override
	public void perform(CommandContent commandContent) {
		P.p.getLordships().createLordship(commandContent.getLPlayer());
		P.p.getMM().msg(commandContent.getLPlayer(), "lordshipcreated");
	}

}
