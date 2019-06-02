package net.fyrezz.me.landlords.cmds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.fyrezz.me.landlords.P;
import net.fyrezz.me.landlords.utils.RequirementState;

public abstract class LordshipCommand {

	protected List<String> aliases = new ArrayList<String>();
	protected CommandRequirements requirements = new CommandRequirements();
	protected String permission;
	protected Map<String, String> vars = new HashMap<String, String>();

	public LordshipCommand() {
		addAliases();
		setRequirements();
		setPermission();
		registerCommand();
	}

	public List<String> getAliases() {
		return aliases;
	}

	public abstract void addAliases();

	public abstract void setRequirements();

	public abstract void setPermission();

	public abstract void perform(CommandContent commandContent);

	public void execute(CommandContent commandContent) {
		if (commandContent.isPlayer()) {
			if (requirements.isPlayer == RequirementState.EXCLUDED) {
				P.p.getMM().msg(commandContent.getLPlayer(), "consolecommandonly");
				return;
			}
			if (commandContent.getLPlayer().hasLordship()) {
				if (requirements.hasLordship == RequirementState.EXCLUDED) {
					P.p.getMM().msg(commandContent.getLPlayer(), "alreadyinalordship");
					return;
				}
				if (!requirements.allowedRanks
						.contains(commandContent.getLPlayer().getLordship().getRank(commandContent.getLPlayer()))) {
					P.p.getMM().msg(commandContent.getLPlayer(), "notenoughrank");
					return;
				}
				if (commandContent.getLordship().isInsideLand(commandContent.getPlayer().getLocation())) {
					if (requirements.isInOwnLand == RequirementState.EXCLUDED) {
						P.p.getMM().msg(commandContent.getLPlayer(), "cantbeinownland");
						return;
					}
				} else if (requirements.isInOwnLand == RequirementState.REQUIRED) {
					P.p.getMM().msg(commandContent.getLPlayer(), "mustbeinownland");
					return;
				}
			} else if (requirements.hasLordship == RequirementState.REQUIRED) {
				P.p.getMM().msg(commandContent.getLPlayer(), "notinalordship");
				return;
			}
		} else if (requirements.isPlayer == RequirementState.REQUIRED) {
			P.p.getMM().msg(commandContent.getSender(), "playercommandonly");
			return;
		}
		perform(commandContent);
	}

	public void registerCommand() {
		for (String alias : this.aliases) {
			CommandListener.lordshipCommands.put(alias, this);
		}
	}

}
