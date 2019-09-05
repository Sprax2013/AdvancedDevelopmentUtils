package de.sprax2013.advanced_dev_utils.spigot.chat.title;

import de.sprax2013.advanced_dev_utils.spigot.utils.MCPacketUtils;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import org.bukkit.entity.Player;

public class v1_8_R3 {
    /**
     * @see TitleAPI#sendTitle(Player, String)
     */
    static void sendTitle(Player p, String title) {
        MCPacketUtils.sendPacket(p,
                new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, IChatBaseComponent.ChatSerializer.a(
                        "{\"text\":\"" + title + "\"}")));
    }

    /**
     * @see TitleAPI#sendSubTitle(Player, String)
     */
    static void sendSubTitle(Player p, String subTitle) {
        MCPacketUtils.sendPacket(p,
                new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE,
                        IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + subTitle + "\"}")));
    }

    /**
     * @see TitleAPI#setTimes(Player, int, int, int)
     */
    static void setTimes(Player p, int fadeIn, int stay, int fadeOut) {
        reset(p);

        MCPacketUtils.sendPacket(p, new PacketPlayOutTitle(fadeIn, stay, fadeOut));
    }

    /**
     * @see TitleAPI#reset(Player)
     */
    static void reset(Player p) {
        MCPacketUtils.sendPacket(p, new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.RESET, null));
    }
}