package de.sprax2013.advanced_dev_utils.spigot.chat.actionbar;

import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.entity.Player;

public class v1_8_R3 {
    /**
     * @see ActionBarAPI#sendActionBar(Player, String)
     */
    static Object createActionBarPacket(String s) {
        return new PacketPlayOutChat(ChatSerializer.a("{\"text\":\"" + s + "\"}"), (byte) 2);
    }
}