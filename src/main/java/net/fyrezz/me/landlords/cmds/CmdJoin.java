package net.fyrezz.me.landlords.cmds;

import org.bukkit.Bukkit;

import net.fyrezz.me.landlords.LPlayer;
import net.fyrezz.me.landlords.Lordship;
import net.fyrezz.me.landlords.Lordships;
import net.fyrezz.me.landlords.P;

public class CmdJoin extends LordshipCommand {

	@Override
	public void addAliases() {
		aliases.add("join");
	}

	@Override
	public void setRequirements() {
		requirements.isPlayer = RequirementState.REQUIRED;
		requirements.hasLordship = RequirementState.EXCLUDED;
		requirements.minArgs = 1;
	}

	@Override
	public void setPermission() {
		permission = "landlords.player";
	}

	@Override
	public void perform(CommandContent commandContent) {
		LPlayer lPlayer = commandContent.getLPlayer();
		String lordName = commandContent.getArg(0);

		Lordship lordship = P.p.getLordships().getByLordName(lordName);

		if (lordship == null) {
			P.p.getMM().msg(lPlayer, "invalidlordship");
			return;
		}

		if (!Lordships.invites.containsKey(lordship) || Lordships.invites.containsKey(lordship)
				&& !Lordships.invites.get(lordship).contains(lPlayer.getName())) {
			P.p.getMM().msg(lPlayer, "notinvited");
			return;
		}

		if (lordship.isFull()) {
			P.p.getMM().msg(lPlayer, "lordshipfull");
			return;
		}
		vars.put("player", lPlayer.getName());
		P.p.getMM().lordshipMsg(lordship, "playerjoined", vars);

		lPlayer.setLordship(lordship);
		lordship.addMember(lPlayer);

		vars.put("lordship", lordship.getLord().getName());
		P.p.getMM().msg(lPlayer, "joinsuccessful", vars);
	}

}
