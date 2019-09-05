package de.sprax2013.advanced_dev_utils.spigot.utils;

import org.bukkit.Bukkit;

public class BukkitServerUtils {
	private static String version;

	/**
	 * Example output: <code>v1_12_R1</code>
	 * 
	 * @return The Bukkit-Version of the server
	 */
	public static String getBukkitVersion() {
		if (version == null) {
			version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
		}

		return version;
	}
}