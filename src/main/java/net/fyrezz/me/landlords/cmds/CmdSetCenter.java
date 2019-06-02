package net.fyrezz.me.landlords.cmds;

import java.util.Arrays;

import net.fyrezz.me.landlords.Lordship;
import net.fyrezz.me.landlords.P;
import net.fyrezz.me.landlords.utils.RequirementState;

public class CmdSetCenter extends LordshipCommand {

	@Override
	public void addAliases() {
		aliases.add("setcenter");
	}

	@Override
	public void setRequirements() {
		requirements.isPlayer = RequirementState.REQUIRED;
		requirements.hasLordship = RequirementState.REQUIRED;
		requirements.allowedRanks = Arrays.asList((byte) 0);
	}

	@Override
	public void setPermission() {
		permission = "landlords.player";
	}

	@Override
	public void perform(CommandContent commandContent) {
		for (String ID : P.p.getLordships().getLoadedLordships().keySet()) {
			Lordship lordship = P.p.getLordships().getByID(ID);
			
		}
		
		LazyLocation loc = new LazyLocation(commandContent.getLPlayer().getPlayer().getLocation());
		commandContent.getLordship().setCenterBlock(loc);
		
		vars.put("x", Integer.toString((int)loc.getLocation().getX()));
		vars.put("z", Integer.toString((int)loc.getLocation().getZ()));
		P.p.getMM().lordshipMsg(commandContent.getLordship(), "centerset", vars);
	}

}
