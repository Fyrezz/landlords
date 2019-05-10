package net.fyrezz.me.landlords.cmds;

import net.fyrezz.me.landlords.P;

public class CmdCreateLordship extends LordshipCommand {
	
	public CmdCreateLordship() {
		this.command = "create";
		this.aliases.add("c");
		this.permission = "create";
		this.playerOnly = true;
		this.requireLordship = false;
		this.requireNoLordship = true;
	}

	@Override
	public void perform(CommandContent commandContent) {
		P.p.getLordships().createLordship(commandContent.getLPlayer());
		commandContent.getPlayer().sendMessage("CREAO!");
	}

}
