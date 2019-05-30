package net.fyrezz.me.landlords.cmds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.fyrezz.me.landlords.utils.RequirementState;

public class CommandRequirements {
	
	public RequirementState isPlayer = RequirementState.ALLOWED;
	public RequirementState hasLordship = RequirementState.ALLOWED;
	public List<Byte> allowedRanks = new ArrayList<Byte>(Arrays.asList((byte) 0,(byte) 1,(byte) 2,(byte) 3));
	public RequirementState isInOwnLand = RequirementState.ALLOWED;
}
