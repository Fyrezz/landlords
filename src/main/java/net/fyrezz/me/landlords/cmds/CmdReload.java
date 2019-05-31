package net.fyrezz.me.landlords.cmds;

import net.fyrezz.me.landlords.P;

public class CmdReload extends LordshipCommand {

	@Override
	public void addAliases() {
		this.aliases.add("reload");
		this.aliases.add("r");
	}

	@Override
	public void setRequirements() {
	}

	@Override
	public void setPermission() {
		this.permission = "landlords.admin";
	}

	@Override
	public void perform(CommandContent commandContent) {
		P.p.onDisable();
		P.p.onEnable();
	}

}
