package net.fyrezz.me.landlords.cmds;

import net.fyrezz.me.landlords.P;
import net.fyrezz.me.landlords.utils.RequirementState;

public class CmdVault extends LordshipCommand {

	@Override
	public void addAliases() {
		aliases.add("vault");
		aliases.add("v");
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
		int gold = commandContent.getLordship().getGold();
		vars.put("amount", Integer.toString(gold));
		P.p.getMM().msg(commandContent.getLPlayer(), "vault", vars);
	}

}
