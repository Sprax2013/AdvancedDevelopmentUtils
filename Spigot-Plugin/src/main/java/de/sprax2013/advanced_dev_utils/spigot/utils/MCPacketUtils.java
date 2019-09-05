package de.sprax2013.advanced_dev_utils.spigot.utils;

import java.util.Arrays;
import java.util.Collection;

import org.bukkit.entity.Player;

import de.sprax2013.advanced_dev_utils.misc.ReflectionUtils;

public class MCPacketUtils {
	/**
	 * Sets the value of an field.<br>
	 * Example usage would be, when creating a packet.
	 *
	 * @param obj   The Object that contains the field to modify
	 * @param name  The name of the field that should be modified
	 * @param value The Value that should be set
	 * 
	 * @return false if an exception occurred. The StackTrace is printed to the
	 *         console.
	 */
	public static boolean setValue(Object obj, String name, Object value) {
		try {
			ReflectionUtils.getField(obj, name).set(obj, value);

			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return false;
	}

	/**
	 * Gets the value of an field.<br>
	 * Example usage would be, when creating a packet.
	 *
	 * @param obj  The Object that contains the field to get the value from
	 * @param name The name of the field
	 * 
	 * @return value of the field or null if failed. <b>Keep in mind that the value
	 *         of the field could also be null.</b>
	 */
	public static Object getValue(Object obj, String name) {
		try {
			return ReflectionUtils.getField(obj, name).get(obj);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	/**
	 * Sends a packet to a player using Reflections.<br>
	 * Short for:
	 * <code>MCPacketUtils.sendPacket(Arrays.asList(player), packet);</code>
	 * 
	 * @see #sendPacket(Collection, Object)
	 *
	 * @param player The player
	 * @param packet The packet
	 */
	public static void sendPacket(Player player, Object packet) {
		sendPacket(Arrays.asList(player), packet);
	}

	/**
	 * Sends a packet to players using Reflections.<br>
	 * If an Exception occurs, it is printed to the console.
	 *
	 * @see #sendPacket(Player, Object)
	 *
	 * @param players The players
	 * @param packet  The packet
	 */
	public static void sendPacket(Collection<? extends Player> players, Object packet) {
		for (Player p : players) {
			try {
				Object handle = p.getClass().getMethod("getHandle").invoke(p);
				Object playerConnection = handle.getClass().getField("playerConnection").get(handle);

				playerConnection.getClass()
						.getMethod("sendPacket", NMSClassUtils.getNMSClass("net.minecraft.server.?.Packet"))
						.invoke(playerConnection, packet);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public static byte getFixAngle(float angle) {
		return (byte) (angle * 256 / 360);
	}

	public static int getFixPoint(double nonFixPoint) {
		return (int) (nonFixPoint * 32.0D);
	}

	public static double fromFixPoint(int fixPoint) {
		return fixPoint / 32.0D;
	}
}