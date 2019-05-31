package net.fyrezz.me.landlords.cmds;

import java.util.HashMap;
import java.util.Map;

import net.fyrezz.me.landlords.LPlayer;
import net.fyrezz.me.landlords.Lordship;
import net.fyrezz.me.landlords.P;
import net.fyrezz.me.landlords.utils.LazyLocation;
import net.fyrezz.me.landlords.utils.RequirementState;

public class CmdCreate extends LordshipCommand {

	public CmdCreate() {
		super();
	}

	@Override
	public void addAliases() {
		this.aliases.add("create");
	}

	@Override
	public void setRequirements() {
		this.commandRequirements.isPlayer = RequirementState.REQUIRED;
		this.commandRequirements.hasLordship = RequirementState.EXCLUDED;
	}

	@Override
	public void setPermission() {
		this.permission = "landlords.player";
	}

	@Override
	public void perform(CommandContent commandContent) {
		Map<LPlayer, Byte> newMembers = new HashMap<LPlayer, Byte>();
		newMembers.put(commandContent.getLPlayer(), (byte) 0);

		Lordship lordship = new Lordship(commandContent.getLPlayer().getUUID(), 1, new LazyLocation(commandContent.getPlayer().getLocation()),
				newMembers);
		P.p.getLordships().loadLordship(lordship);

		commandContent.getLPlayer().setLordship(lordship);

		P.p.getMM().msg(commandContent.getLPlayer(), "lordshipcreated");

		this.vars.put("lordship", commandContent.getPlayer().getName().toString());
		P.p.getMM().msg(commandContent.getLPlayer(), "lordshipcreatedbroadcast", this.vars);
	}

}
