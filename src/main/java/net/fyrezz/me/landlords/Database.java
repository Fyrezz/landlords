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
				 * 1 String ID % 2 Integer level % 3 LazyLocation Homeblock % 4 Map<LPlayer,
				 * Byte> members
				 */

				if (split.length != 5) { // Change this number if arguments of Lordship() change
					P.p.getMm().error("FATAL DB ERROR --> Line " + linenum
							+ " <-- String isn't correctly split (Perhaps wrong module separators?");
					P.p.getServer().shutdown();
				}

				// 1 String ID
				String id = split[0];

				// 2 Integer level
				int level = Integer.parseInt(split[1]);

				// 3 LazyLocation homeblock
				String[] locsplit = split[2].split(itemseparator);
				LazyLocation homeblock = new LazyLocation(locsplit[0], Double.parseDouble(locsplit[1]),
						Double.parseDouble(locsplit[2]), Double.parseDouble(locsplit[3]));

				// 4 Map<LPlayer, Byte> Members
				String[] membersplit = split[4].split(itemseparator);
				Map<LPlayer, Byte> members = new HashMap<LPlayer, Byte>();

				for (int i = 0; i <= membersplit.length - 1; i++) {
					// Members are saved: "RANK_BYTE=UUID"
					Byte rank = Byte.parseByte(membersplit[i].substring(0, 1));
					LPlayer lplayer = new LPlayer(membersplit[i].substring(2));

					members.put(lplayer, rank);
				}
				// Create the lordship
				Lordship lordship = new Lordship(id, level, homeblock, members);

				// Add it to the loaded lordship's map
				lordships.put(lordship.getID(), lordship);

			}
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void save() {
		try {
			// First clear file
			dbfile.delete();
			
			// Don't do anything if there is anything to do
			if (lordships.isEmpty() | lordships.size() < 1 | lordships == null) {
				P.p.getMm().error("No lordships loaded! Saving no thing...");
				return;
			}

			// If there's work, print each loaded lordship in a new line
			PrintWriter pw = new PrintWriter(new FileWriter(dbfile.getAbsolutePath(), false));
			
			/*
			 * Database line modules
			 * 
			 * 1 String ID % 2 Integer level % 3 LazyLocation Homeblock % 4 Map<LPlayer,
			 * Byte> members
			 */

			for (String ID : lordships.keySet()) {
				Lordship lordship = lordships.get(ID);
				List<String> modules = new ArrayList<String>();

				// 1 String ID
				modules.add(lordship.getID());
				
				// 2 Integer Level
				modules.add(String.valueOf(lordship.getLevel()));
				
				// 3 LazyLocation Homeblock
				List<String> locationarray = new ArrayList<String>();
				locationarray.add(lordship.getHomeblock().getLocation().getWorld().getName());
				locationarray.add(String.valueOf(lordship.getHomeblock().getLocation().getX()));
				locationarray.add(String.valueOf(lordship.getHomeblock().getLocation().getY()));
				locationarray.add(String.valueOf(lordship.getHomeblock().getLocation().getZ()));
				modules.add(new String().join(itemseparator,  locationarray).replace("[", "").replace("]", ""));
				
				// 4 Map<LPlayer, Byte> members
				List<String> memberarray = new ArrayList<String>();
				for (LPlayer lplayer : lordship.getMembers().keySet()) {
					memberarray.add(lordship.getMembers().get(lplayer) + "=" + lplayer.getStoredUuid());
				}
				modules.add(new String().join(itemseparator, memberarray).replace("[", "").replace("]", ""));

				// Save all the info in a new line
				String newline = new String().join(moduleseparator, modules).replace("[", "").replace("]", "");
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
		if (!(lordships.containsKey(lordship.getID()))) {
			lordships.put(lordship.getID(), lordship);
		} else {
			P.p.getMm().error("Error trying to load " + lordship.getID() + " Lordship");
		}
	}

	public void removeLordship(Lordship lordship) {
		if (lordships.containsKey(lordship.getID())) {
			lordships.remove(lordship.getID());
		} else {
			P.p.getMm().error("Error trying to remove " + lordship.getID() + "'s Lordship from loaded lordships");
		}
	}

}
