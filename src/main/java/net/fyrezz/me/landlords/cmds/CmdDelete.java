package net.fyrezz.me.landlords.cmds;

import java.util.Arrays;

import org.bukkit.Bukkit;

import net.fyrezz.me.landlords.LPlayer;
import net.fyrezz.me.landlords.LPlayers;
import net.fyrezz.me.landlords.P;
import net.fyrezz.me.landlords.utils.RequirementState;

public class CmdDelete extends LordshipCommand {
	
	public CmdDelete() {
		super();
	}

	@Override
	public void addAliases() {
		this.aliases.add("delete");
		this.aliases.add("del");
	}

	@Override
	public void setRequirements() {
		this.commandRequirements.isPlayer = RequirementState.REQUIRED;
		this.commandRequirements.hasLordship = RequirementState.REQUIRED;
		this.commandRequirements.allowedRanks = Arrays.asList((byte) 0);
	}

	@Override
	public void setPermission() {
		this.permission = "landlords.player";
	}

	@Override
	public void perform(CommandContent commandContent) {
		P.p.getLordships().unloadLordship(commandContent.getLPlayer().getLordship());
		
		P.p.getMM().lordshipMsg(commandContent.getLordship(), "lordshipdeleted");
		
		this.vars.put("lordship", commandContent.getLordship().getLord().getName());
		P.p.getMM().broadcast("lordshipdeletedbroadcast", vars);
		
		P.p.getDB().saveLoadedLordships();
		
		P.p.getLordships().clearMemory();
		P.p.getLordships().load();
		
		P.p.getLPlayers().clearMemory();
		P.p.getLPlayers().load();
	}

}
