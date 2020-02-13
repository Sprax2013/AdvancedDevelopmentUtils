package de.sprax2013.advanced_dev_utils.spigot.chat.actionbar;

import chat.actionbar.v1_15_R1;
import de.sprax2013.advanced_dev_utils.spigot.nms.chat.actionbar.v1_12_R1;
import de.sprax2013.advanced_dev_utils.spigot.nms.chat.actionbar.v1_14_R1;
import de.sprax2013.advanced_dev_utils.spigot.utils.BukkitServerUtils;
import de.sprax2013.advanced_dev_utils.spigot.utils.MCPacketUtils;
import org.bukkit.entity.Player;

public class ActionBarAPI {
    /**
     * Displays an ActionBar to a player using NMS.
     *
     * @param p The Player
     * @param s The String to display
     *
     * @return false, if Server-Version not supported
     */
    public static boolean sendActionBar(Player p, String s) {
        switch (BukkitServerUtils.getBukkitVersion()) {
            case "v1_8_R3":
                MCPacketUtils.sendPacket(p, v1_8_R3.createActionBarPacket(s));
                break;
            case "v1_12_R1":
                MCPacketUtils.sendPacket(p, v1_12_R1.createActionBarPacket(s));
                break;
            case "v1_14_R1":
                MCPacketUtils.sendPacket(p, v1_14_R1.createActionBarPacket(s));
                break;
            case "v1_15_R1":
                MCPacketUtils.sendPacket(p, v1_15_R1.createActionBarPacket(s));
                break;
//		case "v1_13_R2":
//			v1_13_R2.sendActionBar(p, s);
//			break;
            default:
                System.err.println("[!! WARNING !!] ActionBarAPI does not support Server-Version "
                        + BukkitServerUtils.getBukkitVersion());
                return false;
        }

        return true;
    }
}