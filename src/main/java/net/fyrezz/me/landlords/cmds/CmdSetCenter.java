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
		Lordship lordship = commandContent.getLordship();
		LPlayer lPlayer = commandContent.getLPlayer();
		LazyLocation lazyLoc = new LazyLocation(commandContent.getLPlayer().getPlayer().getLocation());
		
		if (P.p.getLordships().lordshipsNear(lPlayer, minDistanceBetweenLordships)) {
			P.p.getMM().msg(lPlayer, "lordshipnear");
			return;
		}
		
		lordship.setCenterBlock(lazyLoc);
		
		lordship.showBoundaries(lPlayer);
		
		vars.put("x", Integer.toString((int)lazyLoc.getLocation().getX()));
		vars.put("z", Integer.toString((int)lazyLoc.getLocation().getZ()));
		P.p.getMM().lordshipMsg(commandContent.getLordship(), "centerset", vars);
	}

}
