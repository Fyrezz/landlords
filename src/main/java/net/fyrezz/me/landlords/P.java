package net.fyrezz.me.landlords;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import net.fyrezz.me.landlords.utils.MessageManager;

public class P extends JavaPlugin {
	
	public static P p;
	
	private Database db;
	private MessageManager mm;
	private FileConfiguration config;
	private FileConfiguration lang;
	
	public P() {
		p = this;
	}
	
	public void onEnable() {
		
		loadConfigs();
		
		loadManagers();
		
		db.load();
		
	}
	
	public void onDisable() {
		
	}
	
	private void loadConfigs() {
		saveResource("config.yml", false);
		saveResource("lang.yml", false);
		
		config = YamlConfiguration.loadConfiguration(new File(this.getDataFolder(), "config.yml"));
		lang = YamlConfiguration.loadConfiguration(new File(this.getDataFolder(), "lang.yml"));
	}
	
	private void loadData() {
		db.load();
	}
	
	private void loadManagers() {
		mm = new MessageManager();
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
