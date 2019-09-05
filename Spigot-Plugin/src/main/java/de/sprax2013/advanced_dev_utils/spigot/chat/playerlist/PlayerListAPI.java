package de.sprax2013.advanced_dev_utils.spigot.chat.playerlist;

import org.bukkit.entity.Player;

import de.sprax2013.advanced_dev_utils.spigot.utils.BukkitServerUtils;

public class PlayerListAPI {
	/**
	 * Sends a Playerlist-Header an -Footer to a player using NMS
	 *
	 * @param p      The player
	 * @param header The header
	 * @param footer The footer
	 *
	 * @return false, if Server-Version not supported
	 */
	public static boolean sendHeaderAndFooter(Player p, String header, String footer) {
		switch (BukkitServerUtils.getBukkitVersion()) {
		case "v1_8_R3":
			v1_8_R3.sendPlayerListHeaderAndFooter(p, header, footer);
			break;
//		case "v1_12_R1":
//			v1_12_R1.sendPlayerListHeaderAndFooter(p, header, footer);
//			break;
		default:
//			try {
//				p.setPlayerListHeaderFooter(header, footer);
//			} catch (@SuppressWarnings("unused") Throwable th) {
				System.err.println("[!! WARNING !!] PlayerListAPI does not support Server-Version "
						+ BukkitServerUtils.getBukkitVersion());
				return false;
//			}
		}

		return true;
	}
}