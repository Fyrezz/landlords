package net.fyrezz.me.landlords.cmds;

import java.util.ArrayList;
import java.util.Arrays;

import net.fyrezz.me.landlords.utils.RequirementState;

public class CmdInvite extends LordshipCommand {
	
	public static Map<>

	@Override
	public void addAliases() {
		aliases.add("invite");
	}

	@Override
	public void setRequirements() {
		requirements.isPlayer = RequirementState.REQUIRED;
		requirements.hasLordship = RequirementState.REQUIRED;
		requirements.allowedRanks = new ArrayList<Byte>(Arrays.asList((byte) 0, (byte) 1));
	}

	@Override
	public void setPermission() {
		permission = "landlords.player";
	}

	@Override
	public void perform(CommandContent commandContent) {
		// TODO Auto-generated method stub
		
	}

}
