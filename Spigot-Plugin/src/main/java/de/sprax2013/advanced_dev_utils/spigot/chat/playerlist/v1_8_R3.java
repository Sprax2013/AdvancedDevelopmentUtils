package de.sprax2013.advanced_dev_utils.spigot.chat.playerlist;

import de.sprax2013.advanced_dev_utils.spigot.utils.MCPacketUtils;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

public class v1_8_R3 {
    /**
     * @see PlayerListAPI#sendHeaderAndFooter(Player, String, String)
     */
    static void sendPlayerListHeaderAndFooter(Player p, String header, String footer) {
        try {
            IChatBaseComponent headerC = ChatSerializer.a("{\"translate\":\"\"}"),
                    footerC = ChatSerializer.a("{\"translate\":\"\"}");

            if (header != null && !header.trim().isEmpty()) {
                headerC = ChatSerializer.a("{\"text\":\"" + header + "\"}");
            }

            if (footer != null && !footer.trim().isEmpty()) {
                footerC = ChatSerializer.a("{\"text\":\"" + footer + "\"}");
            }

            PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();

            Field aField = packet.getClass().getDeclaredField("a");
            aField.setAccessible(true);
            aField.set(packet, headerC);

            Field bField = packet.getClass().getDeclaredField("b");
            bField.setAccessible(true);
            bField.set(packet, footerC);

            MCPacketUtils.sendPacket(p, packet);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}