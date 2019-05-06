package net.fyrezz.me.landlords;

import java.io.File;
import java.util.UUID;

public class LPlayer {

	private String nick;
	private String storeduuid;
	private File LPlayerData;

	public LPlayer(String storeduuid) {
		this.nick = null;
		this.storeduuid = storeduuid;
		this.LPlayerData = new File(P.p.getDataFolder() + File.separator + "LPlayers" + File.pathSeparator
				+ storeduuid.toString() + ".dat");
		init();
	}

	private void init() {
		if (!(LPlayerData.exists())) {
			try {
				LPlayerData.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public String getStoredUuid() {
		return storeduuid;
	}

}
