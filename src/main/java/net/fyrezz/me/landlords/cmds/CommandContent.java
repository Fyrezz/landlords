package net.fyrezz.me.landlords.cmds;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.fyrezz.me.landlords.LPlayer;
import net.fyrezz.me.landlords.Lordship;
import net.fyrezz.me.landlords.Lordships;
import net.fyrezz.me.landlords.P;

public class CommandContent {
	
	private CommandSender sender;
	private List<String> args;
	
	private Player player;
	private LPlayer lPlayer;
	private Lordship lordship;
	private Byte rank;
	
	public CommandContent(CommandSender sender, List<String> args) {
		this.sender = sender;
		this.args = args;
		
		if (sender instanceof Player) {
			this.player = (Player) sender;
			Lordship lordshp = P.p.getLordships().getByPlayerName(player.getName());
			if (lordship != null) {
				this.lordship = lordshp;
				this.lPlayer = lordship.getLPlayerFromName(player.getName());
				this.rank = lordship.getRank(lPlayer);
			}
		}
	}
	
	public boolean isLord() {
		if (rank == 0) {
			return true;
		}
		return false;
	}
	
	public boolean isPlayer() {
		if (player == null) {
			return false;
		}
		return true;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public Lordship getLordship() {
		return lordship;
	}
	
	public LPlayer getLPlayer() {
		return lPlayer;
	}
	
	public Byte getRank() {
		return rank;
	}
	
	public String getArg(Integer index) {
		return args.get(index);
	}

}
