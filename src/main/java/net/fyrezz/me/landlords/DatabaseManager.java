package net.fyrezz.me.landlords;

import java.io.File;
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

	private File lordshipsDBFile = new File(P.p.getDataFolder() + File.separator + "database.json");;
	private String moduleSeparator = "%";
	private final String itemSeparator = ",";

	public DatabaseManager() {
		init();
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
	}

	public Map<String, Lordship> getSavedLordships() {
		Map<String, Lordship> lordships = new HashMap<String, Lordship>();
		int lineNum = 0;

		try {
			Scanner scan = new Scanner(lordshipsDBFile);
			while (scan.hasNextLine()) {
				String line = scan.nextLine();
				String[] split = line.split(moduleSeparator);

				if (split.length != 6) { // Change this number if arguments of Lordship() change
					P.p.getLogger().log(Level.SEVERE, "FATAL DB ERROR --> Line " + lineNum
							+ " <-- String isn't correctly split (Perhaps wrong module separators?");
				}

				// 1 String ID
				String id = split[0];

				// 2 Integer gold
				int gold = Integer.parseInt(split[1]);

				// 3 LazyLocation homeblock
				String[] locSplit = split[2].split(itemSeparator);
				LazyLocation homeblock = new LazyLocation(locSplit[0], Double.parseDouble(locSplit[1]),
						Double.parseDouble(locSplit[2]), Double.parseDouble(locSplit[3]), Float.parseFloat(locSplit[4]),
						Float.parseFloat(locSplit[5]));

				// 4 Map<LPlayer, Byte> Members
				String[] memberSplit = split[3].split(itemSeparator);
				Map<LPlayer, Byte> members = new HashMap<LPlayer, Byte>();

				for (int i = 0; i <= memberSplit.length - 1; i++) {
					// Members are saved: "RANK_BYTE=UUID=NAME"
					String[] memberSplitSplit = memberSplit[i].split("=");
					String UUID = memberSplitSplit[1];
					String name = memberSplitSplit[2];
					Byte rank = Byte.parseByte(memberSplitSplit[0]);

					LPlayer lPlayer = new LPlayer(UUID, name);
					members.put(lPlayer, rank);
				}

				// 5 Integer side
				int side = Integer.parseInt(split[4]);

				// 6 LazyLocation centerblock
				String[] centerSplit = split[5].split(itemSeparator);
				LazyLocation centerblock = new LazyLocation(Integer.parseInt(centerSplit[0]),
						Integer.parseInt(centerSplit[1]));

				// Create the lordship
				Lordship lordship = new Lordship(id, gold, homeblock, members, side, centerblock);

				// Add it to the lordships list
				lordships.put(id, lordship);
			}
			scan.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lordships;
	}

	public void saveLoadedLordships() {
		try {
			init();
			lordshipsDBFile.delete();

			int count = 0;
			Map<String, Lordship> loadedLordships = P.p.getLordships().getLoadedLordships();

			if (loadedLordships.isEmpty() | loadedLordships.size() < 1 | loadedLordships == null) {
				P.p.getLogger().log(Level.INFO, "No lordships loaded Saving no thing...");
				init();
				return;
			}

			PrintWriter pw = new PrintWriter(new FileWriter(lordshipsDBFile.getAbsolutePath(), false));

			for (Lordship lordship : P.p.getLordships().getLordshipsList()) {
				List<String> modules = new ArrayList<String>();

				// 1 String ID
				modules.add(lordship.getID());

				// 2 Integer Gold
				modules.add(String.valueOf(lordship.getGold()));

				// 3 LazyLocation Homeblock
				List<String> locationArray = new ArrayList<String>();
				locationArray.add(lordship.getHomeblock().getLocation().getWorld().getName());
				locationArray.add(String.valueOf(lordship.getHomeblock().getLocation().getX()));
				locationArray.add(String.valueOf(lordship.getHomeblock().getLocation().getY()));
				locationArray.add(String.valueOf(lordship.getHomeblock().getLocation().getZ()));
				locationArray.add(String.valueOf(lordship.getHomeblock().getLocation().getYaw()));
				locationArray.add(String.valueOf(lordship.getHomeblock().getLocation().getPitch()));
				modules.add(new String().join(itemSeparator, locationArray).replace("[", "").replace("]", ""));

				// 4 Map<LPlayer, Byte> members
				List<String> memberArray = new ArrayList<String>();
				for (LPlayer lPlayer : lordship.getMembers().keySet()) {
					memberArray.add(
							lordship.getMembers().get(lPlayer) + "=" + lPlayer.getUUID() + "=" + lPlayer.getName());
				}
				modules.add(new String().join(itemSeparator, memberArray).replace("[", "").replace("]", ""));

				// 5 Integer side
				modules.add(String.valueOf(lordship.getSide()));

				// 6 LazyLocation centerblock
				List<String> centerArray = new ArrayList<String>();
				centerArray.add(String.valueOf((int) lordship.getCenterBlock().getBlockX()));
				centerArray.add(String.valueOf((int) lordship.getCenterBlock().getBlockZ()));
				modules.add(new String().join(itemSeparator, centerArray).replace("[", "").replace("]", ""));

				// Save all the info in a new line
				String newline = new String().join(moduleSeparator, modules).replace("[", "").replace("]", "");
				pw.println(newline);
				count++;
			}

			P.p.getLogger().log(Level.INFO, "Saved " + count + " Lordships to Database");
			pw.close();
			init();
		} catch (Exception e) {
			e.printStackTrace();
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
