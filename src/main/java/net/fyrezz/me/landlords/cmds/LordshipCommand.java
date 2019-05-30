package net.fyrezz.me.landlords.cmds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import net.fyrezz.me.landlords.P;
import net.fyrezz.me.landlords.utils.RequirementState;

public abstract class LordshipCommand {

	protected List<String> aliases = new ArrayList<String>();
	protected CommandRequirements commandRequirements = new CommandRequirements();
	protected String permission;
	protected Map<String, String> vars = new HashMap<String, String>();

	public LordshipCommand() {
		addAliases();
		setRequirements();
		setPermission();
		registerCommand();

		if (this.aliases.isEmpty() | this.permission == null) {
			P.p.getLogger().log(Level.WARNING, "Couldn't register command " + this.getClass().toString());
		}
	}

	public List<String> getAliases() {
		return aliases;
	}

	public abstract void addAliases();

	public abstract void setRequirements();

	public abstract void setPermission();

	public abstract void perform(CommandContent commandContent);

	public void execute(CommandContent commandContent) {
		if (commandContent.isPlayer() && (commandRequirements.isPlayer == RequirementState.EXCLUDED)) {
			P.p.getMM().msg(commandContent.getLPlayer(), "consolecommandonly");
			return;
		}
		if (!commandContent.isPlayer() && (commandRequirements.isPlayer == RequirementState.REQUIRED)) {
			P.p.getMM().msg(commandContent.getSender(), "playercommandonly");
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

		if (commandContent.getLPlayer().hasLordship() && !commandRequirements.allowedRanks
				.contains(commandContent.getLPlayer().getLordship().getRank(commandContent.getLPlayer()))) {
			P.p.getMM().msg(commandContent.getLPlayer(), "notenoughrank");
			return;
		}
		
		/* TODO
		 * Check isInOwnLand
		 */
		
	}

	public void registerCommand() {
		for (String alias : this.aliases) {
			CommandListener.lordshipCommands.put(alias, this);
		}
	}

}
