package net.fyrezz.me.landlords.listener;

import java.util.HashMap;
import java.util.Map;

import net.fyrezz.me.landlords.LPlayer;

public class PermissionChecker {

	public static Map<PlayerAction, Byte> permissionsMap = new HashMap<PlayerAction, Byte>();

	/**
	 * The minimum required rank for each PlayerAction is defined in this Map.
	 * 0 == Lord, 1 == Top rank, 2 == Mid rank, 3 == Lowest rank
	 * ONLY for actions of Lordship members
	 */
	public static void loadPermissions() {
		PermissionChecker.permissionsMap.put(PlayerAction.BREAK_BLOCK, (byte) 2);
		PermissionChecker.permissionsMap.put(PlayerAction.PLACE_BLOCK, (byte) 2);
		PermissionChecker.permissionsMap.put(PlayerAction.USE, (byte) 3);
		PermissionChecker.permissionsMap.put(PlayerAction.OPEN_CHEST, (byte) 2);
		PermissionChecker.permissionsMap.put(PlayerAction.OPEN_FURNACE, (byte) 3);
	}

	public static boolean isAllowed(LPlayer lPlayer, PlayerAction action) {
		
		/* Security check. Probably not necessary */
		if (!lPlayer.hasLordship()) {
			return false;
		}
		return lPlayer.getRank() <= permissionsMap.get(action);
	}

}
