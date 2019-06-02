package net.fyrezz.me.landlords.cmds;

import java.util.Arrays;

import net.fyrezz.me.landlords.P;
import net.fyrezz.me.landlords.utils.LazyLocation;
import net.fyrezz.me.landlords.utils.RequirementState;

public class CmdSetHome extends LordshipCommand {
	
	public CmdSetHome() {
		super();
	}

	@Override
	public void addAliases() {
		aliases.add("sethome");
		aliases.add("sh");
	}

	@Override
	public void setRequirements() {
		requirements.hasLordship = RequirementState.REQUIRED;
		requirements.isPlayer = RequirementState.REQUIRED;
		requirements.isInOwnLand = RequirementState.REQUIRED;
		requirements.allowedRanks = Arrays.asList((byte) 0, (byte) 1);
	}

	@Override
	public void setPermission() {
		permission = "landlords.player";
	}

	@Override
	public void perform(CommandContent commandContent) {
		LazyLocation loc = new LazyLocation(commandContent.getPlayer().getLocation());
		commandContent.getLordship().setHomeblock(loc);
		
		vars.put("x", Integer.toString((int)loc.getLocation().getX()));
		vars.put("y", Integer.toString((int)loc.getLocation().getY()));
		vars.put("z", Integer.toString((int)loc.getLocation().getZ()));
		P.p.getMM().lordshipMsg(commandContent.getLordship(), "homeset", vars);
	}

}
