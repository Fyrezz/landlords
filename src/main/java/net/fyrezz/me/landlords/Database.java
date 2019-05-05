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

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import net.fyrezz.me.landlords.utils.LUUID;

public class Database {
	
	private File dbfile;
	private String moduleseparator = "%";
	private String itemseparator = "|";
	
	private int totallordships;
	private Map<LUUID, Lordship> lordships = new HashMap<LUUID, Lordship>();
	
	public Database() {
		this.dbfile = new File(P.p.getDataFolder() + File.pathSeparator + "db.json");
	}
	
	public void load() {
		if (!dbfile.exists()) {
			create();
		}
		
		int linenum = 0;
		Scanner scan;
		try {
			scan = new Scanner(dbfile);

			
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
					String[] split = line.split(moduleseparator);

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
					
					String[] locsplit = split[3].split(itemseparator);
					Location homeblock = new Location(Bukkit.getWorld(locsplit[0]), Integer.parseInt(locsplit[1]), Integer.parseInt(locsplit[2]), Integer.parseInt(locsplit[3]));
					
					// Last module List<Player> Members
					
					String[] membersplit = split[split.length-1].split(itemseparator);
					List<Player> members = new ArrayList<Player>();
					
					for (int i = 0; i < membersplit.length - 1; i++) {
						members.add(P.p.getServer().getPlayer((membersplit[i])));
					}
					
					Lordship lrdshp = new Lordship(lord, level, homeblock, members);
					lordships.put(lrdshp.getLUUID(), lrdshp);
				
				} else {
					P.p.getMm().error("DB empty!");
				}
				scan.close();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void save() {
		if (!dbfile.exists()) {
			create();
		}
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(dbfile.getAbsolutePath(), true));
			pw.flush();
			
			pw.println(totallordships);
			
			for (LUUID id : lordships.keySet()) {
				Lordship lrdshp = lordships.get(id);
				pw.print(lrdshp.getLord().toString());
				pw.print(moduleseparator);
				pw.print(lrdshp.getLUUID().toString());
				pw.print(moduleseparator);
				pw.print(lrdshp.getLevel());
				pw.print(moduleseparator);
				pw.print(lrdshp.getHomeblock().getWorld() + itemseparator + lrdshp.getHomeblock().getX() + itemseparator + lrdshp.getHomeblock().getY() + itemseparator + lrdshp.getHomeblock().getZ());
				pw.print(moduleseparator);
				pw.print(new String().join(itemseparator, lrdshp.getMembers().toString()));
			}
			
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void create() {
		try {
			dbfile.mkdir();
			PrintWriter pw = new PrintWriter(new FileWriter(dbfile.getAbsolutePath(), true));
			pw.println(0);
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
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
	
	public Map<LUUID, Lordship> getLordships(){
		return lordships;
	}

}
