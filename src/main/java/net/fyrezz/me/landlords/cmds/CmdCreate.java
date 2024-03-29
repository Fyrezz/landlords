package net.fyrezz.me.landlords.cmds;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;

import net.fyrezz.me.landlords.LPlayer;
import net.fyrezz.me.landlords.Lordship;
import net.fyrezz.me.landlords.P;
import net.fyrezz.me.landlords.utils.LazyLocation;

public class CmdCreate extends LordshipCommand {
	
	private final double minDistanceBetweenLordships = P.p.getConfig().getDouble("mindistance");

	public CmdCreate() {
		super();
	}

	@Override
	public void addAliases() {
		aliases.add("create");
	}

	@Override
	public void setRequirements() {
		requirements.isPlayer = RequirementState.REQUIRED;
		requirements.hasLordship = RequirementState.EXCLUDED;
		requirements.playerCost = 121;
	}

	@Override
	public void setPermission() {
		permission = "landlords.player";
	}

	@Override
	public void perform(CommandContent commandContent) {
		LPlayer lPlayer = commandContent.getLPlayer();
		
		LazyLocation lazyLoc = lPlayer.getLazyLocation();
		
		if (P.p.getLordships().lordshipsNear(lPlayer, minDistanceBetweenLordships)) {
			P.p.getMM().msg(lPlayer, "lordshipnear");
			return;
		}
		
		lPlayer.removeMaterial(Material.GOLD_INGOT, 121);

		Map<LPlayer, Byte> newMembers = new HashMap<LPlayer, Byte>();
		newMembers.put(lPlayer, (byte) 0);
		Lordship lordship = new Lordship(commandContent.getLPlayer().getUUID(), 0, lazyLoc, newMembers, 11, lazyLoc);

		P.p.getLordships().loadLordship(lordship);
		lPlayer.setLordship(lordship);
		lordship.showBoundaries(lPlayer);
		
		lordship.addGold(121);

		P.p.getMM().msg(lPlayer, "lordshipcreated");
		vars.put("lordship", lPlayer.getName().toString());
		P.p.getMM().msg(lPlayer, "lordshipcreatedbroadcast", vars);
	}

}
