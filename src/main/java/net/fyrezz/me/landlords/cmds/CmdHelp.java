package net.fyrezz.me.landlords.cmds;

import java.util.HashMap;
import java.util.Map;

import net.fyrezz.me.landlords.P;
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
		Map<String, String> helpVars = new HashMap<String, String>();
		helpVars.put("version", P.p.getDescription().getVersion());
		helpVars.put("authors", P.p.getDescription().getAuthors().toString());
		P.p.getMM().msg(commandContent.getLPlayer(), "help", helpVars);
	}

}
