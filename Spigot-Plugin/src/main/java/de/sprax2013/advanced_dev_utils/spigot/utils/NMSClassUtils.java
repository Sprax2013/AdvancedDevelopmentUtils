package de.sprax2013.advanced_dev_utils.spigot.utils;

import java.util.HashMap;

public class NMSClassUtils {
	private static HashMap<String, Class<?>> cache = new HashMap<>();

	/**
	 * Returns an NMS-Class.<br>
	 * Use '<code>?</code>' to let it replaced by the Bukkit-Server-Version
	 * 
	 * @see BukkitServerUtils#getBukkitVersion()
	 * @see #getNMSClass(String, boolean)
	 *
	 * @param name The class name (example: net.minecraft.server.?.Packet)
	 * 
	 * @return the NMS class
	 */
	public static Class<?> getNMSClass(String name) {
		return getNMSClass(name, false);
	}

	/**
	 * Returns an NMS-Class.<br>
	 * Use '<code>?</code>' to let it replaced by the Bukkit-Server-Version
	 * 
	 * @see BukkitServerUtils#getBukkitVersion()
	 * @see #getNMSClass(String)
	 *
	 * @param name     The class name (example: net.minecraft.server.?.Packet)
	 * @param forceNew When true, the cached class will be ignored and a new one
	 *                 will be returned (and cached)
	 * 
	 * @return the NMS class
	 */
	public static Class<?> getNMSClass(String name, boolean forceNew) {
		Class<?> result = cache.get(name);

		try {
			if (result == null || forceNew) {
				result = Class.forName(name.replace("?", BukkitServerUtils.getBukkitVersion()));

				cache.put(name, result);
			}
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}

		return result;
	}
}