package net.fyrezz.me.landlords.cmds;

import net.fyrezz.me.landlords.LPlayer;
import net.fyrezz.me.landlords.Lordship;
import net.fyrezz.me.landlords.utils.RequirementState;

public class CmdShowLimits extends LordshipCommand {

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
		Lordship lordship = commandContent.getLordship();
		LPlayer lPlayer = commandContent.getLPlayer();
		
		lordship.showBoundaries(lPlayer);
		/*
		 * TODO Message with limits
		 * 
		 *  (x, y)  xxxxxxxxx (x, y)
		 * 		    x       x
		 * 		    x       x
		 *   (x, y) xxxxxxxxx (x, y)
		 *   
		 */
	}

}
