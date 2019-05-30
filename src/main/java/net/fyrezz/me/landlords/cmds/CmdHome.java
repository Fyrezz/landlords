package net.fyrezz.me.landlords.cmds;

import net.fyrezz.me.landlords.P;
import net.fyrezz.me.landlords.utils.RequirementState;

public class CmdHome extends LordshipCommand {

	@Override
	public void addAliases() {
		this.aliases.add("home");
		this.aliases.add("h");
	}

	@Override
	public void setRequirements() {
		this.commandRequirements.isPlayer = RequirementState.REQUIRED;
		this.commandRequirements.hasLordship = RequirementState.REQUIRED;
	}

	@Override
	public void setPermission() {
		this.permission = "landlords.player";
	}

	@Override
	public void perform(CommandContent commandContent) {
		commandContent.getLPlayer().getPlayer().teleport(commandContent.getLordship().getHomeblock().getLocation());
		P.p.getMM().msg(commandContent.getLPlayer(), "teleportedhome");
	}

}
