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
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import net.fyrezz.me.landlords.utils.LazyLocation;

public class Database {

	private File dbfile;
	private String moduleseparator = "%";
	private String itemseparator = ",";

	private Map<String, Lordship> lordships = new HashMap<String, Lordship>();

	public Database() {
		this.dbfile = new File(P.p.getDataFolder() + File.separator + "database.json");

		init();
	}

	public void load() {
		int linenum = 0;
		Scanner scan;
		try {
			lordships.clear();

			scan = new Scanner(dbfile);
			while (scan.hasNextLine()) {
				String line = scan.nextLine();
				String[] split = line.split(moduleseparator);

				/*
				 * Database line modules
				 * 
				 * 1 LPlayer lord % 2 LUUID id % 3 Integer level % 4 LazyLocation homeblock % 5
				 * List<Player> members
				 */

				if (split.length != 5) { // Change this number if arguments of Lordship() change
					P.p.getMm().error("FATAL DB ERROR --> Line " + linenum
							+ " <-- String isn't correctly split (Perhaps wrong module separators?");
					P.p.getServer().shutdown();
				}

				// 1 Player lord

				LPlayer lord = new LPlayer(UUID.fromString(split[0]));

				// 3 Integer level

				int level = Integer.parseInt(split[2]);

				// 4 Location homeblock

				String[] locsplit = split[3].split(itemseparator);
				LazyLocation homeblock = new LazyLocation(locsplit[0], Double.parseDouble(locsplit[1]),
						Double.parseDouble(locsplit[2]), Double.parseDouble(locsplit[3]));

				// 5 List<Player> Members

				String[] membersplit = split[4].split(itemseparator);
				List<UUID> members = new ArrayList<UUID>();

				for (int i = 0; i <= membersplit.length - 1; i++) {
					members.add(UUID.fromString(membersplit[i]));
				}

				Lordship lrdshp = new Lordship(lord, level, homeblock, members);
				lordships.put(lrdshp.getLUUID().getString(), lrdshp);

			}
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void save() {
		try {
			//First clear file
			dbfile.delete();
			
			//Then, save each loaded Lordship
			PrintWriter pw = new PrintWriter(new FileWriter(dbfile.getAbsolutePath(), false));
			for (String luuid : lordships.keySet()) {
				Lordship lordship = lordships.get(luuid);
				
				//Serialize members
				List<UUID> members = new ArrayList<UUID>();
				for (LPlayer lplayer : lordship.getMembers()) {
					members.add(lplayer.getStoredUuid());
				}
				
				//Save all the info in a new line
				String newline = lordship.getLord().getStoredUuid() + moduleseparator + lordship.getLUUID().getString()
						+ moduleseparator + lordship.getLevel() + moduleseparator
						+ lordship.getHomeblock().getLocation().getWorld().getName() + itemseparator
						+ lordship.getHomeblock().getLocation().getX() + itemseparator
						+ lordship.getHomeblock().getLocation().getY() + itemseparator
						+ lordship.getHomeblock().getLocation().getZ() + moduleseparator
						+ new String().join(itemseparator, members.toString().replace("[", "").replace("]", ""));

				pw.println(newline);
			}

			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void init() {
		if (!(dbfile.exists())) {
			try {
				dbfile.createNewFile();
			} catch (IOException e) {
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

	public Map<String, Lordship> getLordships() {
		return lordships;
	}

	public void addLordship(Lordship lordship) {
		if (!(lordships.containsKey(lordship.getLUUID()))) {
			lordships.put(lordship.getLUUID().getString(), lordship);
		} else {
			P.p.getMm().error("Error trying to load " + lordship.getLUUID() + " Lordship");
		}
	}

	public void removeLordship(Lordship lordship) {
		if (lordships.containsKey(lordship.getLUUID())) {
			lordships.remove(lordship.getLUUID());
		} else {
			P.p.getMm().error(
					"Error trying to remove " + lordship.getLUUID() + "'s Lordship from loaded lordships");
		}
	}

}
