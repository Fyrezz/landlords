package net.fyrezz.me.landlords;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class LPlayer {

	private String StoredUUID;
	private String name;
	private Lordship lordship;

	public LPlayer(String UUID, String name) {
		this.StoredUUID = UUID;
		this.name = name;
		this.lordship = P.p.getLordships().getByID("DEFAULT");
	}

	/*
	 * Get & Set
	 */

	public Lordship getLordship() {
		return lordship;
	}

	public String getUUID() {
		return StoredUUID;
	}

	public String getName() {
		return name;
	}

	public Player getPlayer() {
		return Bukkit.getPlayer(UUID.fromString(StoredUUID));
	}

	public void setName(String newName) {
		name = newName;
	}

	public void setLordship(Lordship newLordship) {
		this.lordship = newLordship;
	}

	/*
	 * Utils
	 */

	public boolean isOnline() {
		return P.p.getServer().getPlayer(UUID.fromString(StoredUUID)).isOnline();
	}

	public boolean hasLordship() {
		return !(lordship.getID() == P.p.getLordships().getDefault().getID());
	}

	public boolean hasMaterial(Material material, int amount) {
		ItemStack[] inventory = getPlayer().getInventory().getContents();
		int invAmount = 0;

		for (ItemStack itemStack : inventory) {
			if (itemStack != null && itemStack.getType() != Material.AIR) {
				if (itemStack.getType() == (material)) {
					invAmount += itemStack.getAmount();
				}
			}
		}
		return invAmount >= amount;
	}

	public void removeMaterial(Material material, int amount) {
		ItemStack[] inventory = getPlayer().getInventory().getContents();
		int count = amount;

		for (ItemStack itemStack : inventory) {
			if (itemStack != null && itemStack.getType() != Material.AIR) {
				if (itemStack.getType() == material) {
					int times = itemStack.getAmount();
					for (int n = 0; n < times; n++) {
						if (count > 0) {
							itemStack.setAmount(itemStack.getAmount() - 1);
							count--;
						}
					}
				}
			}
		}
	}

}
