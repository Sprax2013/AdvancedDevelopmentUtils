package de.sprax2013.advanced_dev_utils.spigot.chat.actionbar;

import org.bukkit.entity.Player;

import de.sprax2013.advanced_dev_utils.spigot.utils.MCPacketUtils;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;

public class v1_8_R3 {
	/**
	 * @see ActionBarAPI#sendActionBar(Player, String)
	 */
	static void sendActionBar(Player p, String s) {
		MCPacketUtils.sendPacket(p, new PacketPlayOutChat(ChatSerializer.a("{\"text\":\"" + s + "\"}"), (byte) 2));
	}
}