package net.fyrezz.me.landlords.cmds;

import net.fyrezz.me.landlords.LPlayer;
import net.fyrezz.me.landlords.P;
import net.fyrezz.me.landlords.utils.RequirementState;

public class CmdDelete extends LordshipCommand {
	
	public CmdDelete() {
		super();
	}

	@Override
	public void addAliases() {
		this.aliases.add("delete");
		this.aliases.add("del");
	}

	@Override
	public void setRequirements() {
		this.commandRequirements.isPlayer = RequirementState.REQUIRED;
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
		P.p.getLordships().unloadLordship(commandContent.getLPlayer().getLordship());
		
		P.p.getMM().lordshipMsg(commandContent.getLordship(), "lordshipdeleted");
		
		this.vars.put("lordship", commandContent.getLordship().getLord().getName());
		P.p.getMM().broadcast("lordshipdeletedbroadcast", vars);
	}

}
