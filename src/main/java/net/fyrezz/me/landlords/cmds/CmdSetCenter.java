package net.fyrezz.me.landlords.cmds;

import java.util.Arrays;

import net.fyrezz.me.landlords.LPlayer;
import net.fyrezz.me.landlords.Lordship;
import net.fyrezz.me.landlords.P;
import net.fyrezz.me.landlords.utils.LazyLocation;

public class CmdSetCenter extends LordshipCommand {
	
	private final double minDistanceBetweenLordships = P.p.getConfig().getDouble("mindistance");

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
		LPlayer lPlayer = commandContent.getLPlayer();
		Lordship lordship = lPlayer.getLordship();
		LazyLocation lazyLoc = lPlayer.getLazyLocation();
		
		if (P.p.getLordships().lordshipsNear(lPlayer, minDistanceBetweenLordships)) {
			P.p.getMM().msg(lPlayer, "lordshipnear");
			return;
		}
		
		lordship.setCenterBlock(lazyLoc);
		
		lordship.setHomeblock(lazyLoc);
		
		lordship.showBoundaries(lPlayer);
		
		vars.put("x", Integer.toString((int)lazyLoc.getX()));
		vars.put("z", Integer.toString((int)lazyLoc.getZ()));
		P.p.getMM().lordshipMsg(lordship, "centerset", vars);
	}

}
