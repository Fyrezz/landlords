package net.fyrezz.me.landlords.cmds;

import net.fyrezz.me.landlords.P;
import net.fyrezz.me.landlords.utils.RequirementState;

public class CmdCreateLordship extends LordshipCommand {

	@Override
	public void addAliases() {
		aliases.add("create");
	}
	
	@Override
	public void setRequirements() {
		commandRequirements.isPlayer = RequirementState.REQUIRED;
		commandRequirements.hasLordship = RequirementState.EXCLUDED;
	}

	@Override
	public void setPermission() {
		permission = "landlords.player";
	}
	
	@Override
	public void perform(CommandContent commandContent) {
		P.p.getLordships().createLordship(commandContent.getLPlayer());
		P.p.getMM().undefinedMsg(commandContent.getLPlayer(), "Lordship creado!");
	}

}
