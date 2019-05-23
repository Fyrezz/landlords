package net.fyrezz.me.landlords.cmds;

import net.fyrezz.me.landlords.P;
import net.fyrezz.me.landlords.utils.RequirementState;

public class CmdDeleteLordship extends LordshipCommand {
	
	public CmdDeleteLordship() {
		super();
	}

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
		P.p.getLordships().unloadLordship(commandContent.getLPlayer().getLordship());
		commandContent.getLPlayer().setLordship(P.p.getLordships().getDefault());
		P.p.getMM().msg(commandContent.getLPlayer(), "lordshipdeleted");
	}

}
