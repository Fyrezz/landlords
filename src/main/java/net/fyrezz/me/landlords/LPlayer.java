package net.fyrezz.me.landlords;

import java.io.File;
import java.util.UUID;

public class LPlayer {

	private String nick;
	private String storedUUID;
	private File lPlayerData;

	public LPlayer(String storedUUID) {
		this.nick = null;
		this.storedUUID = storedUUID;
		this.lPlayerData = new File(P.p.getDataFolder() + File.separator + "LPlayers" + File.separator
				+ storedUUID.toString() + ".dat");
		init();
	}

	private void init() {
		if (!(lPlayerData.exists())) {
			try {
				lPlayerData.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public String getStoredUUID() {
		return storedUUID;
	}

}
