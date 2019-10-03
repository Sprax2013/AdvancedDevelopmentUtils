package de.sprax2013.advanced_dev_utils.spigot.nms.chat.actionbar;

import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle;

public class v1_12_R1 {
    public static Object createActionBarPacket(String s) {
        return new PacketPlayOutTitle(
                PacketPlayOutTitle.EnumTitleAction.ACTIONBAR,
                IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + s + "\"}")
        );
    }
}