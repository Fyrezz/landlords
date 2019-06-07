package net.fyrezz.me.landlords.cmds;

import java.util.Arrays;

import org.bukkit.entity.Player;

import net.fyrezz.me.landlords.LPlayer;
import net.fyrezz.me.landlords.Lordship;
import net.fyrezz.me.landlords.P;
import net.fyrezz.me.landlords.utils.LazyLocation;

public class CmdSetHome extends LordshipCommand {
	
	public CmdSetHome() {
		super();
	}

	@Override
	public void addAliases() {
		aliases.add("sethome");
		aliases.add("sh");
	}

	@Override
	public void setRequirements() {
		requirements.hasLordship = RequirementState.REQUIRED;
		requirements.isPlayer = RequirementState.REQUIRED;
		requirements.isInOwnLand = RequirementState.REQUIRED;
		requirements.allowedRanks = Arrays.asList((byte) 0, (byte) 1);
	}

	@Override
	public void setPermission() {
		permission = "landlords.player";
	}

	@Override
	public void perform(CommandContent commandContent) {
		LPlayer lPlayer = commandContent.getLPlayer();
		Lordship lordship = lPlayer.getLordship();
		LazyLocation lazyLoc = lPlayer.getLazyLocation();
		
		lordship.setHomeblock(lazyLoc);
		
		vars.put("x", Integer.toString((int)lazyLoc.getX()));
		vars.put("y", Integer.toString((int)lazyLoc.getY()));
		vars.put("z", Integer.toString((int)lazyLoc.getZ()));
		P.p.getMM().lordshipMsg(lordship, "homeset", vars);
	}

}
