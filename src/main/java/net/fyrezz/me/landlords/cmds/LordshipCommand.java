package net.fyrezz.me.landlords.cmds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		if (requirements.check(commandContent)) {
			perform(commandContent);
		}
	}

	public void registerCommand() {
		for (String alias : this.aliases) {
			CommandListener.lordshipCommands.put(alias, this);
		}
	}

}
