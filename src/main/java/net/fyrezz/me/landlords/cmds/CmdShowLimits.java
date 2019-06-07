package net.fyrezz.me.landlords.cmds;

import org.bukkit.entity.Player;

import net.fyrezz.me.landlords.LPlayer;
import net.fyrezz.me.landlords.Lordship;
import net.fyrezz.me.landlords.P;

public class CmdShowLimits extends LordshipCommand {

	private final String whiteSeparator = "                                     ";
	private final String horizontalLine = "       &cx&fxxxxxxxxxxxxxxxxxxxxxxxxx&cx";

	@Override
	public void addAliases() {
		aliases.add("showlimits");
		aliases.add("sl");
	}

	@Override
	public void setRequirements() {
		requirements.isPlayer = RequirementState.REQUIRED;
		requirements.hasLordship = RequirementState.REQUIRED;
	}

	@Override
	public void setPermission() {
		permission = "landlords.player";
	}

	@Override
	public void perform(CommandContent commandContent) {
		LPlayer lPlayer = commandContent.getLPlayer();
		Lordship lordship = lPlayer.getLordship();

		lordship.showBoundaries(lPlayer);

		final String upleft = "&7(X " + lordship.getMaxX() + ", Z " + lordship.getMinZ() + ")";
		final String upright = "&7(X " + lordship.getMaxX() + ", Z " + lordship.getMaxZ() + ")";
		final String downleft = "&7(X " + lordship.getMinX() + ", Z " + lordship.getMinZ() + ")";
		final String downright = "&7(X " + lordship.getMinX() + ", Z " + lordship.getMaxZ() + ")";

		P.p.getMM().undefinedMsg(lPlayer, "");
		P.p.getMM().undefinedMsg(lPlayer, upleft + "                         " + upright);
		P.p.getMM().undefinedMsg(lPlayer, horizontalLine);
		for (int i = 0; i < 14; i++) {
			P.p.getMM().undefinedMsg(lPlayer, "       x" + whiteSeparator + "x");
		}
		P.p.getMM().undefinedMsg(lPlayer, horizontalLine);
		P.p.getMM().undefinedMsg(lPlayer, downleft + "                         " + downright);
	}

}
