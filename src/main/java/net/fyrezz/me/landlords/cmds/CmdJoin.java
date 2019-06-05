package net.fyrezz.me.landlords.cmds;

import org.bukkit.Bukkit;

import net.fyrezz.me.landlords.LPlayer;
import net.fyrezz.me.landlords.Lordship;
import net.fyrezz.me.landlords.Lordships;
import net.fyrezz.me.landlords.P;
import net.fyrezz.me.landlords.utils.RequirementState;

public class CmdJoin extends LordshipCommand {

	@Override
	public void addAliases() {
		aliases.add("join");
	}

	@Override
	public void setRequirements() {
		requirements.isPlayer = RequirementState.REQUIRED;
		requirements.hasLordship = RequirementState.EXCLUDED;
	}

	@Override
	public void setPermission() {
		permission = "landlords.player";
	}

	@Override
	public void perform(CommandContent commandContent) {
		LPlayer lPlayer = commandContent.getLPlayer();
		String lordName = commandContent.getArg(0);

		if (!P.p.getLordships().lordshipExists(lordName)) {
			P.p.getMM().msg(lPlayer, "invalidlordship");
			return;
		}
		
		Lordship lordship = P.p.getLordships().getByLordName(lordName);
		
		if (!Lordships.invites.get(lordship).contains(lPlayer.getName())) {
			P.p.getMM().msg(lPlayer, "notinvited");
			return;
		}

		if (lordship.isFull()) {
			P.p.getMM().msg(lPlayer, "lordshipfull");
			return;
		}
		
		vars.put("player", deinvitedPlayer);
		P.p.getMM().msg(lPlayer, "playerdeinvited", vars);
	}

}
