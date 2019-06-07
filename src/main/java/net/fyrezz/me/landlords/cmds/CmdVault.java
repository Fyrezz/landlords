package net.fyrezz.me.landlords.cmds;

import org.bukkit.entity.Player;

import net.fyrezz.me.landlords.LPlayer;
import net.fyrezz.me.landlords.Lordship;
import net.fyrezz.me.landlords.P;

public class CmdVault extends LordshipCommand {

	@Override
	public void addAliases() {
		aliases.add("vault");
		aliases.add("v");
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
		
		int gold = lPlayer.getLordship().getGold();
		vars.put("amount", Integer.toString(gold));
		P.p.getMM().msg(lPlayer, "vault", vars);
	}

}
