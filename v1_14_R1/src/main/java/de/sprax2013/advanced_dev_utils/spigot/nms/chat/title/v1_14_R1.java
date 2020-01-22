package de.sprax2013.advanced_dev_utils.spigot.nms.chat.title;

import net.minecraft.server.v1_14_R1.IChatBaseComponent;
import net.minecraft.server.v1_14_R1.PacketPlayOutTitle;

public class v1_14_R1 {
    public static Object getTitlePacket(String title) {
        return new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE,
                IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + title + "\"}"));
    }

    public static Object getSubTitlePacket(String subTitle) {
        return new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE,
                IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + subTitle + "\"}"));
    }

    public static Object getTimesPacket(int fadeIn, int stay, int fadeOut) {
        return new PacketPlayOutTitle(fadeIn, stay, fadeOut);
    }

    public static Object getResetPacket() {
        return new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.RESET, null);
    }
}