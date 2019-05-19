package net.fyrezz.me.landlords.cmds;

import java.util.ArrayList;
import java.util.List;

import net.fyrezz.me.landlords.P;
import net.fyrezz.me.landlords.utils.RequirementState;

public abstract class LordshipCommand {

	public List<String> aliases = new ArrayList<String>();
	public CommandRequirements commandRequirements = new CommandRequirements();
	public String permission;

	public LordshipCommand() {
		addAliases();
		setRequirements();
		setPermission();
		registerCommand();
	}

	/*
	 * Get & Set
	 */

	public List<String> getAliases() {
		return aliases;
	}

	/*
	 * Methods for commands
	 */

	public abstract void addAliases();

	public abstract void setRequirements();

	public abstract void setPermission();

	public abstract void perform(CommandContent commandContent);

	/*
	 * Class methods
	 */

	public void execute(CommandContent commandContent) {
		if (commandContent.isPlayer() && (commandRequirements.isPlayer == RequirementState.EXCLUDED)) {
			P.p.getMM().msg(commandContent.getLPlayer(), "consolecommandonly");
			return;
		}
		if (!commandContent.isPlayer() && (commandRequirements.isPlayer == RequirementState.REQUIRED)) {
			P.p.getMM().msg(commandContent.getLPlayer(), "playercommandonly");
			return;
		}

		if (commandContent.getLPlayer().hasLordship()
				&& (commandRequirements.hasLordship == RequirementState.EXCLUDED)) {
			P.p.getMM().msg(commandContent.getLPlayer(), "alreadyinalordship");
			return;
		}
		if (!commandContent.getLPlayer().hasLordship()
				&& (commandRequirements.hasLordship == RequirementState.REQUIRED)) {
			P.p.getMM().msg(commandContent.getLPlayer(), "notinalordship");
			return;
		}

		if (!commandRequirements.allowedRanks
				.contains(commandContent.getLPlayer().getLordship().getRank(commandContent.getLPlayer()))) {
			P.p.getMM().msg(commandContent.getLPlayer(), "notenoughrank");
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
