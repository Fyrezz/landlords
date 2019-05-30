package net.fyrezz.me.landlords.cmds;

import java.util.HashMap;
import java.util.Map;

import net.fyrezz.me.landlords.P;
import net.fyrezz.me.landlords.utils.RequirementState;

public class CmdHelp extends LordshipCommand {
	
	public CmdHelp() {
		super();
	}
	
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
		vars.put("version", P.p.getDescription().getVersion());
		vars.put("authors", P.p.getDescription().getAuthors().toString());
		
		P.p.getMM().msg(commandContent.getLPlayer(), "help", vars);
	}

}
