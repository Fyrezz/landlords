package net.fyrezz.me.landlords.cmds;

import net.fyrezz.me.landlords.Lordship;
import net.fyrezz.me.landlords.P;

public class CmdCreateLordship extends LordshipCommand {

	@Override
	public void addAliases() {
		aliases.add("create");
	}
	
	@Override
	public void setRequirements() {
		commandRequirements.allowConsole = false;
		commandRequirements.requireNoLordship = true;
	}

	@Override
	public void setPermission() {
		permission = "landlords.player";
	}
	
	@Override
	public void perform(CommandContent commandContent) {
		Lordship lordship = new Lordship(commandContent.getLPlayer());
		P.p.getLordships().loadLordship(lordship);
		P.p.getMM().msg(commandContent.getPlayer(), "");
	}

}
