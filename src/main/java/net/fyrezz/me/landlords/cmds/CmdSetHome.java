package net.fyrezz.me.landlords.cmds;

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
		this.commandRequirements.allowedRanks.remove((byte) 3);
		this.commandRequirements.allowedRanks.remove((byte) 2);
	}

	@Override
	public void setPermission() {
		this.permission = "landlords.player";
	}

	@Override
	public void perform(CommandContent commandContent) {
		LazyLocation lazyLocation = new LazyLocation(commandContent.getPlayer().getLocation());
		commandContent.getLordship().setHomeblock(lazyLocation);
		vars.put("x", Double.toString(lazyLocation.getLocation().getX()));
		vars.put("y", Double.toString(lazyLocation.getLocation().getY()));
		vars.put("z", Double.toString(lazyLocation.getLocation().getX()));
		P.p.getMM().lordshipMsg(commandContent.getLordship(), "homeset", vars);
	}

}
