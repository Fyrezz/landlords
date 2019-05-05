package net.fyrezz.me.landlords;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import net.fyrezz.me.landlords.utils.LUUID;

public class Database {
	
	private File dbfile;
	private char moduleseparator = '%';
	private char itemseparator = '|';
	
	private int totallordships;
	private Map<LUUID, Lordship> lordships = new HashMap<LUUID, Lordship>();
	
	public Database() {
		
	}
	
	public void load() {
		dbfile = new File(P.p.getDataFolder() + File.pathSeparator + "db.json");
		if (!dbfile.exists()) {
			try {
				dbfile.mkdir();
			} catch (Exception e) {
				P.p.getMm().error("FATAL DB ERROR: Couldn't create db.json");
				P.p.getMm().error("Shutdown in process...");
				P.p.getServer().shutdown();
			}
		}
		
		int linenum = 0;
		Scanner scan = new Scanner(dbfile);
		
		while (scan.hasNextLine()) {
			String line = scan.nextLine();
			linenum++;
			
			if (linenum == 1 && !(isNumeric(line))) {
				P.p.getMm().error("FATAL DB ERROR: 1st line isn't a number!");
				P.p.getMm().error("Shutdown in process...");
				P.p.getServer().shutdown();
			} else {
				totallordships = Integer.parseInt(line);
			}
			
			if (linenum > 1) {
				String[] split = line.split("%");

				/*
				 * Database line modules
				 * 
				 * 1 Player lord % 2 LUUID id % 3 Integer level % 4 Location homeblock % LAST List<Player> members
				 */
				
				if (split.length != 5) { //Change this number if arguments of Lordship() change
					P.p.getMm().error("FATAL DB ERROR --> Line " + linenum + " <-- String isn't correctly split (Perhaps wrong module separators?");
					P.p.getServer().shutdown();
				}
				
				// 1 Player lord
				
				Player lord = P.p.getServer().getPlayer(split[0]);
				
				// 3 Integer level
				
				int level = Integer.parseInt(split[2]);
				
				// 4 Location homeblock
				
				String[] locsplit = split[3].split("|");
				Location homeblock = new Location(Bukkit.getWorld(locsplit[0]), Integer.parseInt(locsplit[1]), Integer.parseInt(locsplit[2]), Integer.parseInt(locsplit[3]));
				
				// Last module List<Player> Members
				
				String[] membersplit = split[split.length-1].split("|");
				List<Player> members = new ArrayList<Player>();
				
				for (int i = 0; i < membersplit.length - 1; i++) {
					members.add(P.p.getServer().getPlayer((membersplit[i])));
				}
				Lordship lrdshp = new Lordship(lord, level, homeblock, members);
				lordships.put(lrdshp.getLUUID(), lrdshp);
			
			} else {
				P.p.getMm().error("DB empty!");
			}
		}
	}
	
	public boolean isNumeric(String str) {
		try {
			Double.parseDouble(str);
			return true;
		}
			catch (Exception e) {
				return false;
			}
		}
		

}
