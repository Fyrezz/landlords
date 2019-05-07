package net.fyrezz.me.landlords;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import net.fyrezz.me.landlords.cmds.LordshipCommands;
import net.fyrezz.me.landlords.utils.MessageManager;

public class P extends JavaPlugin {
	
	public static P p;
	
	private Database db;
	private MessageManager mm;
	private LordshipCommands lc;
	private FileConfiguration config;
	private FileConfiguration lang;
	
	public P() {
		p = this;
	}
	
	public void onEnable() {
		loadConfigs();
		loadManagers();
		loadDatabase();
		registerCommands();
	}
	
	public void onDisable() {
		db.save();
	}
	
	private void loadConfigs() {
		saveResource("config.yml", false);
		config = YamlConfiguration.loadConfiguration(new File(this.getDataFolder(), "config.yml"));
		
		saveResource("lang.yml", false);
		lang = YamlConfiguration.loadConfiguration(new File(this.getDataFolder(), "lang.yml"));
	}
	
	private void loadDatabase() {
		db = new Database();
		db.load();
	}
	
	private void loadManagers() {
		mm = new MessageManager();
	}
	
	public void registerCommands() {
		lc = new LordshipCommands();
		getCommand("l").setExecutor(lc);
	}
	
	public MessageManager getMm() {
		return mm;
	}
	
	public Database getDb() {
		return db;
	}
	
	public FileConfiguration getConfig() {
		return config;
	}
	
	public FileConfiguration getLang() {
		return lang;
	}
	
}
