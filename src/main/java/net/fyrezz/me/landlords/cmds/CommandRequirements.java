package net.fyrezz.me.landlords.cmds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.fyrezz.me.landlords.P;
import net.fyrezz.me.landlords.utils.RequirementState;

public class CommandRequirements {
	
	public RequirementState isPlayer = RequirementState.ALLOWED;
	public RequirementState hasLordship = RequirementState.ALLOWED;
	public RequirementState isInOwnLand = RequirementState.ALLOWED;
	public List<Byte> allowedRanks = new ArrayList<Byte>(Arrays.asList((byte) 0,(byte) 1,(byte) 2,(byte) 3));
	
	public boolean check(CommandContent commandContent) {
		if (commandContent.isPlayer()) {
			if (this.isPlayer == RequirementState.EXCLUDED) {
				P.p.getMM().msg(commandContent.getLPlayer(), "consolecommandonly");
				return false;
			}
			if (commandContent.getLPlayer().hasLordship()) {
				if (hasLordship == RequirementState.EXCLUDED) {
					P.p.getMM().msg(commandContent.getLPlayer(), "alreadyinalordship");
					return false;
				}
				if (!allowedRanks
						.contains(commandContent.getLPlayer().getLordship().getRank(commandContent.getLPlayer()))) {
					P.p.getMM().msg(commandContent.getLPlayer(), "notenoughrank");
					return false;
				}
				if (commandContent.getLordship().containsLazyLoc(commandContent.getPlayer().getLocation())) {
					if (isInOwnLand == RequirementState.EXCLUDED) {
						P.p.getMM().msg(commandContent.getLPlayer(), "cantbeinownland");
						return false;
					}
				} else if (isInOwnLand == RequirementState.REQUIRED) {
					P.p.getMM().msg(commandContent.getLPlayer(), "mustbeinownland");
					return false;
				}
			} else if (hasLordship == RequirementState.REQUIRED) {
				P.p.getMM().msg(commandContent.getLPlayer(), "notinalordship");
				return false;
			}
		} else if (isPlayer == RequirementState.REQUIRED) {
			P.p.getMM().msg(commandContent.getSender(), "playercommandonly");
			return false;
		}
		return true;
	}
}
