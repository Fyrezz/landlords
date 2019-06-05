package net.fyrezz.me.landlords.cmds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.fyrezz.me.landlords.LPlayer;
import net.fyrezz.me.landlords.P;
import net.fyrezz.me.landlords.utils.RequirementState;

public class CommandRequirements {
	
	public RequirementState isPlayer = RequirementState.ALLOWED;
	public RequirementState hasLordship = RequirementState.ALLOWED;
	public RequirementState isInOwnLand = RequirementState.ALLOWED;
	public List<Byte> allowedRanks = new ArrayList<Byte>(Arrays.asList((byte) 0,(byte) 1,(byte) 2,(byte) 3));
	
	public boolean check(CommandContent commandContent) {
		if (commandContent.isPlayer()) {
			LPlayer lPlayer = commandContent.getLPlayer();
			
			if (this.isPlayer == RequirementState.EXCLUDED) {
				P.p.getMM().msg(lPlayer, "consolecommandonly");
				return false;
			}
			
			if (lPlayer.hasLordship()) {
				if (hasLordship == RequirementState.EXCLUDED) {
					P.p.getMM().msg(lPlayer, "alreadyinalordship");
					return false;
				}
				
				if (!allowedRanks
						.contains(lPlayer.getLordship().getRank(lPlayer))) {
					P.p.getMM().msg(lPlayer, "notenoughrank");
					return false;
				}
				
				if (commandContent.getLordship().containsLazyLoc(lPlayer.getLazyLocation())) {
					if (isInOwnLand == RequirementState.EXCLUDED) {
						P.p.getMM().msg(lPlayer, "cantbeinownland");
						return false;
					}
	
				} else if (isInOwnLand == RequirementState.REQUIRED) {
					P.p.getMM().msg(lPlayer, "mustbeinownland");
					return false;
				}
				
			} else if (hasLordship == RequirementState.REQUIRED) {
				P.p.getMM().msg(lPlayer, "notinalordship");
				return false;
			}
			
		} else if (isPlayer == RequirementState.REQUIRED) {
			P.p.getMM().msg(commandContent.getSender(), "playercommandonly");
			return false;
		}
		return true;
	}
}
