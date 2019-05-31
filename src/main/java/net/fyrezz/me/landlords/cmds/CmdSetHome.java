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
		this.aliases.add("sethome");
		this.aliases.add("sh");
	}

	@Override
	public void setRequirements() {
		this.commandRequirements.hasLordship = RequirementState.REQUIRED;
		this.commandRequirements.isPlayer = RequirementState.REQUIRED;
		this.commandRequirements.isInOwnLand = RequirementState.REQUIRED;
		this.commandRequirements.allowedRanks = Arrays.asList((byte) 0, (byte) 1);
	}

	@Override
	public void setPermission() {
		this.permission = "landlords.player";
	}

	@Override
	public void perform(CommandContent commandContent) {
		LazyLocation lazyLocation = new LazyLocation(commandContent.getPlayer().getLocation());
		commandContent.getLordship().setHomeblock(lazyLocation);
		
		this.vars.put("x", Integer.toString((int)lazyLocation.getLocation().getX()));
		this.vars.put("y", Integer.toString((int)lazyLocation.getLocation().getY()));
		this.vars.put("z", Integer.toString((int)lazyLocation.getLocation().getX()));
		P.p.getMM().lordshipMsg(commandContent.getLordship(), "homeset", vars);
	}

}
