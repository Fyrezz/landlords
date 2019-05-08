package net.fyrezz.me.landlords;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;

import net.fyrezz.me.landlords.utils.LazyLocation;

public class DatabaseManager {

	private File lordshipsDBFile;
	private File lPlayersDBFolder;
	private String moduleSeparator = "%";
	private String itemSeparator = ",";

	public DatabaseManager() {
		this.lordshipsDBFile = new File(P.p.getDataFolder() + File.separator + "database.json");
		this.lPlayersDBFolder = new File(P.p.getDataFolder() + File.separator + "LPlayers");

		init();
	}

	public List<Lordship> loadSavedLordships() {
		List<Lordship> lordships = new ArrayList<Lordship>();
		int lineNum = 0;
		Scanner scan;
		try {
			scan = new Scanner(lordshipsDBFile);
			while (scan.hasNextLine()) {
				String line = scan.nextLine();
				String[] split = line.split(moduleSeparator);

				/*
				 * Database line modules
				 * 
				 * 1 String ID % 2 Integer level % 3 LazyLocation Homeblock % 4 Map<LPlayer,
				 * Byte> members
				 */

				if (split.length != 4) { // Change this number if arguments of Lordship() change
					P.p.getLogger().log(Level.SEVERE, "FATAL DB ERROR --> Line " + lineNum
							+ " <-- String isn't correctly split (Perhaps wrong module separators?");
				}

				// 1 String ID
				String id = split[0];

				// 2 Integer level
				int level = Integer.parseInt(split[1]);

				// 3 LazyLocation homeblock
				String[] locSplit = split[2].split(itemSeparator);
				LazyLocation homeblock = new LazyLocation(locSplit[0], Double.parseDouble(locSplit[1]),
						Double.parseDouble(locSplit[2]), Double.parseDouble(locSplit[3]));

				// 4 Map<LPlayer, Byte> Members
				String[] memberSplit = split[3].split(itemSeparator);
				Map<LPlayer, Byte> members = new HashMap<LPlayer, Byte>();

				for (int i = 0; i <= memberSplit.length - 1; i++) {
					// Members are saved: "RANK_BYTE=UUID=NAME"
					String[] memberSplitSplit = memberSplit[i].split("=");
					String UUID = memberSplitSplit[1];
					String name = memberSplitSplit[2];
					Byte rank = Byte.parseByte(memberSplitSplit[0]);

					members.put(new LPlayer(UUID, name), rank);
				}
				// Create the lordship
				Lordship lordship = new Lordship(id, level, homeblock, members);

				// Add it to the lordships list
				lordships.add(lordship);
			}
			scan.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lordships;
	}

	public void saveLoadedLordships() {
		try {
			// First clear file
			lordshipsDBFile.delete();

			List<Lordship> loadedLordships = P.p.getLordships().getLoadedLordships();

			// Don't do anything if there is anything to do
			if (loadedLordships.isEmpty() | loadedLordships.size() < 1 | loadedLordships == null) {
				P.p.getLogger().log(Level.INFO, "No lordships loaded! Saving no thing...");
				return;
			}

			// If there's work, print each loaded lordship in a new line
			PrintWriter pw = new PrintWriter(new FileWriter(lordshipsDBFile.getAbsolutePath(), false));

			/*
			 * Database line modules
			 * 
			 * 1 String ID % 2 Integer level % 3 LazyLocation Homeblock % 4 Map<LPlayer,
			 * Byte> members
			 */

			for (Lordship lordship : loadedLordships) {
				List<String> modules = new ArrayList<String>();

				// 1 String ID
				modules.add(lordship.getID());

				// 2 Integer Level
				modules.add(String.valueOf(lordship.getLevel()));

				// 3 LazyLocation Homeblock
				List<String> locationArray = new ArrayList<String>();
				locationArray.add(lordship.getHomeblock().getLocation().getWorld().getName());
				locationArray.add(String.valueOf(lordship.getHomeblock().getLocation().getX()));
				locationArray.add(String.valueOf(lordship.getHomeblock().getLocation().getY()));
				locationArray.add(String.valueOf(lordship.getHomeblock().getLocation().getZ()));
				modules.add(new String().join(itemSeparator, locationArray).replace("[", "").replace("]", ""));

				// 4 Map<LPlayer, Byte> members
				List<String> memberArray = new ArrayList<String>();
				for (LPlayer lPlayer : lordship.getRankedMembers().keySet()) {
					memberArray.add(lordship.getRankedMembers().get(lPlayer) + "=" + lPlayer.getUUID() + "="
							+ lPlayer.getName());
				}
				modules.add(new String().join(itemSeparator, memberArray).replace("[", "").replace("]", ""));

				// Save all the info in a new line
				String newline = new String().join(moduleSeparator, modules).replace("[", "").replace("]", "");
				pw.println(newline);
			}
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<LPlayer> loadSavedLPlayers() {
		List<LPlayer> lPlayers = new ArrayList<LPlayer>();
		int count = 0;
		for (File lPlayerFile : lPlayersDBFolder.listFiles()) {
			String[] UUID = lPlayerFile.getName().split(".");
			LPlayer lPlayer = new LPlayer(UUID[0]);
			lPlayers.add(lPlayer);
			count++;
		}
		if (lPlayers.size() != count) {
			P.p.getLogger().log(Level.WARNING,
					"Possible error when loading saved LPlayers to memory: List size doesn't match LPlayers");
		}
		P.p.getLogger().log(Level.INFO, "Loaded " + count + "LPlayers to memory.");
		return lPlayers;
	}

	public void init() {
		// Create Lordships Database file
		if (!lordshipsDBFile.exists()) {
			try {
				lordshipsDBFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// Create LPlayers Folder
		if (!lPlayersDBFolder.exists()) {
			try {
				lPlayersDBFolder.mkdir();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public boolean isNumeric(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
