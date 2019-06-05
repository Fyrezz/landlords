package net.fyrezz.me.landlords.cmds;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;

import net.fyrezz.me.landlords.LPlayer;
import net.fyrezz.me.landlords.Lordship;
import net.fyrezz.me.landlords.Lordships;
import net.fyrezz.me.landlords.P;
import net.fyrezz.me.landlords.utils.RequirementState;

public class CmdDeinvite extends LordshipCommand {

	@Override
	public void addAliases() {
		aliases.add("deinvite");
	}

	@Override
	public void setRequirements() {
		requirements.isPlayer = RequirementState.REQUIRED;
		requirements.hasLordship = RequirementState.REQUIRED;
		requirements.allowedRanks = new ArrayList<Byte>(Arrays.asList((byte) 0, (byte) 1));
	}

	@Override
	public void setPermission() {
		permission = "landlords.player";
	}

	@Override
	public void perform(CommandContent commandContent) {
		Lordship lordship = commandContent.getLordship();
		LPlayer lPlayer = commandContent.getLPlayer();
		String deinvitedPlayer = commandContent.getArg(0);

		if (Lordships.invites.containsKey(lordship)) {
			Lordships.invites.get(lordship).remove(deinvitedPlayer);
		}

		vars.put("player", deinvitedPlayer);
		P.p.getMM().msg(lPlayer, "playerdeinvited", vars);
	}

}
