package de.sprax2013.advanced_dev_utils.spigot.utils;

import org.bukkit.entity.Player;

public class PlayerUtils {
	public static int getPing(Player p) {
		try {
			Object entityPlayer = p.getClass().getMethod("getHandle").invoke(p);
			return (int) entityPlayer.getClass().getField("ping").get(entityPlayer);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return 0;
	}
}