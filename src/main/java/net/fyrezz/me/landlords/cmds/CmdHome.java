package net.fyrezz.me.landlords.cmds;

import net.fyrezz.me.landlords.P;

public class CmdHome extends LordshipCommand {

	@Override
	public void addAliases() {
		aliases.add("home");
		aliases.add("h");
	}

	@Override
	public void setRequirements() {
		requirements.isPlayer = RequirementState.REQUIRED;
		requirements.hasLordship = RequirementState.REQUIRED;
	}

	@Override
	public void setPermission() {
		permission = "landlords.player";
	}

	@Override
	public void perform(CommandContent commandContent) {
		vars.put("time", "30 seconds");
		P.p.getMM().msg(commandContent.getLPlayer(), "teleportwait");
	}

}
