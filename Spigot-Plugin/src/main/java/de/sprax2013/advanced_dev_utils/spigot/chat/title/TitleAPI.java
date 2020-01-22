package de.sprax2013.advanced_dev_utils.spigot.chat.title;

import de.sprax2013.advanced_dev_utils.spigot.nms.chat.title.v1_14_R1;
import de.sprax2013.advanced_dev_utils.spigot.utils.BukkitServerUtils;
import de.sprax2013.advanced_dev_utils.spigot.utils.MCPacketUtils;
import org.bukkit.entity.Player;

public class TitleAPI {
    /**
     * Sends a title and sub-title to a player using NMS
     *
     * @param p        The player
     * @param title    The title
     * @param subTitle The sub-title
     *
     * @return false, if Server-Version not supported
     */
    @SuppressWarnings("deprecation")
    public static boolean sendTitle(Player p, String title, String subTitle) {
        switch (BukkitServerUtils.getBukkitVersion()) {
            case "v1_8_R3":
                v1_8_R3.sendTitle(p, title);
                v1_8_R3.sendSubTitle(p, subTitle);
                break;
            case "v1_14_R1":
                MCPacketUtils.sendPacket(p, v1_14_R1.getTitlePacket(title));
                MCPacketUtils.sendPacket(p, v1_14_R1.getSubTitlePacket(subTitle));
                break;
            default:
                try {
                    p.sendTitle(title, subTitle);
                } catch (@SuppressWarnings("unused") Throwable th) {
                    System.err.println("[!! WARNING !!] TitleAPI does not support Server-Version "
                            + BukkitServerUtils.getBukkitVersion());
                    return false;
                }
        }

        return true;
    }

    /**
     * Sends a title, sub-title and times to a player using NMS
     *
     * @param p        The player
     * @param title    The title
     * @param subTitle The sub-title
     * @param fadeIn   Time in Ticks
     * @param stay     Time in Ticks
     * @param fadeOut  Time in Ticks
     *
     * @return false, if Server-Version not supported
     */
    public static boolean sendTitle(Player p, String title, String subTitle, int fadeIn, int stay, int fadeOut) {
        switch (BukkitServerUtils.getBukkitVersion()) {
            case "v1_8_R3":
                v1_8_R3.setTimes(p, fadeIn, stay, fadeOut);

                v1_8_R3.sendTitle(p, title);
                v1_8_R3.sendSubTitle(p, subTitle);
                break;
            case "v1_14_R1":
                MCPacketUtils.sendPacket(p, v1_14_R1.getResetPacket());
                MCPacketUtils.sendPacket(p, v1_14_R1.getTimesPacket(fadeIn, stay, fadeOut));

                MCPacketUtils.sendPacket(p, v1_14_R1.getTitlePacket(title));
                MCPacketUtils.sendPacket(p, v1_14_R1.getSubTitlePacket(subTitle));
                break;
            default:
                try {
                    p.sendTitle(title, subTitle/*, fadeIn, stay, fadeOut*/);
                } catch (@SuppressWarnings("unused") Throwable th) {
                    System.err.println("[!! WARNING !!] TitleAPI does not support Server-Version "
                            + BukkitServerUtils.getBukkitVersion());
                    return false;
                }
        }

        return true;
    }

    /**
     * Sends a title to a player using NMS
     *
     * @param p     The player
     * @param title The title
     *
     * @return false, if Server-Version not supported
     */
    public static boolean sendTitle(Player p, String title) {
        switch (BukkitServerUtils.getBukkitVersion()) {
            case "v1_8_R3":
                v1_8_R3.sendTitle(p, title);
                break;
            case "v1_14_R1":
                MCPacketUtils.sendPacket(p, v1_14_R1.getTitlePacket(title));
                break;
            default:
                return false;
        }

        return true;
    }

    /**
     * Sends a title and times to a player using NMS
     *
     * @param p       The player
     * @param title   The title
     * @param fadeIn  Time in Ticks
     * @param stay    Time in Ticks
     * @param fadeOut Time in Ticks
     *
     * @return false, if Server-Version not supported
     */
    public static boolean sendTitle(Player p, String title, int fadeIn, int stay, int fadeOut) {
        switch (BukkitServerUtils.getBukkitVersion()) {
            case "v1_8_R3":
                v1_8_R3.setTimes(p, fadeIn, stay, fadeOut);
                v1_8_R3.sendTitle(p, title);
                break;
            case "v1_14_R1":
                MCPacketUtils.sendPacket(p, v1_14_R1.getResetPacket());
                MCPacketUtils.sendPacket(p, v1_14_R1.getTimesPacket(fadeIn, stay, fadeOut));

                MCPacketUtils.sendPacket(p, v1_14_R1.getTitlePacket(title));
                break;
            default:
                return false;
        }

        return true;
    }

    /**
     * Sends a sub-title to a player using NMS
     *
     * @param p        The player
     * @param subTitle The sub-title
     *
     * @return false, if Server-Version not supported
     */
    public static boolean sendSubTitle(Player p, String subTitle) {
        switch (BukkitServerUtils.getBukkitVersion()) {
            case "v1_8_R3":
                v1_8_R3.sendSubTitle(p, subTitle);
                break;
            case "v1_14_R1":
                MCPacketUtils.sendPacket(p, v1_14_R1.getSubTitlePacket(subTitle));
                break;
            default:
                return false;
        }

        return true;
    }

    /**
     * Sends a sub-title and times to a player using NMS
     *
     * @param p        The player
     * @param subTitle The sub-title
     * @param fadeIn   Time in Ticks
     * @param stay     Time in Ticks
     * @param fadeOut  Time in Ticks
     *
     * @return false, if Server-Version not supported
     */
    public static boolean sendSubTitle(Player p, String subTitle, int fadeIn, int stay, int fadeOut) {
        switch (BukkitServerUtils.getBukkitVersion()) {
            case "v1_8_R3":
                v1_8_R3.setTimes(p, fadeIn, stay, fadeOut);
                v1_8_R3.sendSubTitle(p, subTitle);
                break;
            case "v1_14_R1":
                MCPacketUtils.sendPacket(p, v1_14_R1.getResetPacket());
                MCPacketUtils.sendPacket(p, v1_14_R1.getTimesPacket(fadeIn, stay, fadeOut));

                MCPacketUtils.sendPacket(p, v1_14_R1.getSubTitlePacket(subTitle));
                break;
            default:
                return false;
        }

        return true;
    }

    /**
     * Sends times to a player using NMS
     *
     * @param p       The player
     * @param fadeIn  Time in Ticks
     * @param stay    Time in Ticks
     * @param fadeOut Time in Ticks
     *
     * @return false, if Server-Version not supported
     */
    public static boolean setTimes(Player p, int fadeIn, int stay, int fadeOut) {
        switch (BukkitServerUtils.getBukkitVersion()) {
            case "v1_8_R3":
                v1_8_R3.setTimes(p, fadeIn, stay, fadeOut);
                break;
            case "v1_14_R1":
                MCPacketUtils.sendPacket(p, v1_14_R1.getResetPacket());
                MCPacketUtils.sendPacket(p, v1_14_R1.getTimesPacket(fadeIn, stay, fadeOut));
                break;
            default:
                return false;
        }

        return true;
    }

    /**
     * Sends a Reset-Packet to a player using NMS
     *
     * @param p The player
     *
     * @return false, if Server-Version not supported
     */
    public static boolean reset(Player p) {
        switch (BukkitServerUtils.getBukkitVersion()) {
            case "v1_8_R3":
                v1_8_R3.reset(p);
                break;
            case "v1_14_R1":
                MCPacketUtils.sendPacket(p, v1_14_R1.getResetPacket());
                break;
            default:
                return false;
        }

        return true;
    }
}