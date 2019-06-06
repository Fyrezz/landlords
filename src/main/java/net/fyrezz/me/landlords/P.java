/*
 * @P.java
 * 
 * Copyright (c) 2019 Fyrezz
 * All rights reserved.
 * 
 * Feel free to use it! :)
 */

package net.fyrezz.me.landlords;

import java.io.File;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import net.fyrezz.me.landlords.cmds.CommandListener;
import net.fyrezz.me.landlords.listener.EventPlayerListener;
import net.fyrezz.me.landlords.utils.MessageManager;
import net.fyrezz.me.landlords.utils.PermissionChecker;

/**
 * Plugin's main class.
 * 
 * @version 1.0.0
 * @author Fyrezz
 */
public class P extends JavaPlugin {
	public static P p;
	
	private DatabaseManager databaseManager;
	private MessageManager messageManager;
	private Lordships lordships;
	private LPlayers lPlayers;
	private FileConfiguration config;
	private FileConfiguration lang;

	/**
	 * Single plugin instance
	 */
	public P() {
		p = this;
	}
	
	@Override
	public void onEnable() {
		// Make sure config files exist, and load them
		saveResource("config.yml", false);
		saveResource("lang.yml", false);

		this.config = YamlConfiguration.loadConfiguration(new File(this.getDataFolder(), "config.yml"));
		this.lang = YamlConfiguration.loadConfiguration(new File(this.getDataFolder(), "lang.yml"));

		getLogger().setLevel(Level.ALL);
		
		messageManager = new MessageManager();
		
		// Set permissions inside Lordships
		PermissionChecker.loadPermissions();

		// Load Database Manager
		databaseManager = new DatabaseManager();
		databaseManager.init();

		// Load Lordships
		lordships = new Lordships();
		lordships.clearMemory();
		lordships.load();

		// Then, load LPlayers
		lPlayers = new LPlayers();
		lPlayers.clearMemory();
		lPlayers.load();

		// Check Lordships, just for security
		for (String ID : lordships.getLoadedLordships().keySet()) {
			lordships.getByID(ID).check();
		}

		// Register commands
		getCommand("l").setExecutor(new CommandListener());

		// Finally, register event listeners
		getServer().getPluginManager().registerEvents(new EventPlayerListener(), this);
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
