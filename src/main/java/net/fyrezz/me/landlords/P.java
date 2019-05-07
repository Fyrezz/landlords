package net.fyrezz.me.landlords;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import net.fyrezz.me.landlords.cmds.LordshipCommands;
import net.fyrezz.me.landlords.utils.MessageManager;

public class P extends JavaPlugin {
	
	public static P p;
	
	private DatabaseManager databaseManager;
	private MessageManager messageManager;
	
	private Lordships lordships;
	private LPlayers lPlayers;
	
	private FileConfiguration config;
	private FileConfiguration lang;
	
	public P() {
		p = this;
	}
	
	@Override
	public void onEnable() {
		// Make sure config files exist, and load them
		saveResource("config.yml", false);
		saveResource("lang.yml", false);
		
		config = YamlConfiguration.loadConfiguration(new File(this.getDataFolder(), "config.yml"));
		lang = YamlConfiguration.loadConfiguration(new File(this.getDataFolder(), "lang.yml"));
		
		// Load Database Manager
		databaseManager = new DatabaseManager();
		databaseManager.init();
		
		// Load Message Manager
		messageManager = new MessageManager();
		
		// Load LPlayers and Lordships
		lPlayers = new LPlayers();
		lPlayers.clearMemory();
		lPlayers.load();
		
		lordships = new Lordships();
		lordships.clearMemory();
		lordships.load();
		
		// Register commands
		getCommand("l").setExecutor(new LordshipCommands());
		
		// Finally, register event listeners
	}
	
	@Override
	public void onDisable() {
		// Save loaded Lordships
		databaseManager.saveLoadedLordships();
	}
	
	public Lordships getLordships() {
		return lordships;
	}
	
	public LPlayers getLPlayers() {
		return lPlayers;
	}
	
	public MessageManager getMM() {
		return messageManager;
	}
	
	public DatabaseManager getDB() {
		return databaseManager;
	}
	
	public FileConfiguration getConfig() {
		return config;
	}
	
	public FileConfiguration getLang() {
		return lang;
	}
	
}
