package net.fyrezz.me.landlords.cmds;

import net.fyrezz.me.landlords.P;
import net.fyrezz.me.landlords.utils.RequirementState;

public class CmdVault extends LordshipCommand {

	@Override
	public void addAliases() {
		this.aliases.add("vault");
		this.aliases.add("v");
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
		int gold = commandContent.getLordship().getGold();
		this.vars.put("amount", Integer.toString(gold));
		P.p.getMM().msg(commandContent.getLPlayer(), "vault", vars);
	}

}
