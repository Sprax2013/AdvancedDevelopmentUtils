package de.sprax2013.advanced_dev_utils.spigot.chat.locale;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import de.sprax2013.advanced_dev_utils.spigot.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.UUID;

public class ClientSettingsAPI {
    static HashMap<UUID, ClientSettings> clientSettings = new HashMap<>();

    public static void init() {
        ProtocolLibrary.getProtocolManager().addPacketListener(
                new PacketAdapter(Main.getInstance(), ListenerPriority.MONITOR, PacketType.Play.Client.SETTINGS) {
                    @Override
                    public void onPacketReceiving(PacketEvent e) {
                        if (clientSettings.containsKey(e.getPlayer().getUniqueId())) {
                            if (e.getPacket().getHands().size() > 0) {
                                clientSettings.get(e.getPlayer().getUniqueId()).update(
                                        e.getPacket().getStrings().read(0), e.getPacket().getIntegers().read(0),
                                        ClientChatMode
                                                .getByProtocolLibHand(e.getPacket().getChatVisibilities().read(0)),
                                        e.getPacket().getBooleans().read(0), e.getPacket().getIntegers().read(1),
                                        ClientMainHand.getByProtocolLibHand(e.getPacket().getHands().read(0)));
                            } else {
                                clientSettings.put(e.getPlayer().getUniqueId(), new ClientSettings(
                                        e.getPacket().getStrings().read(0), e.getPacket().getIntegers().read(0),
                                        ClientChatMode
                                                .getByProtocolLibHand(e.getPacket().getChatVisibilities().read(0)),
                                        e.getPacket().getBooleans().read(0), e.getPacket().getIntegers().read(1)));
                            }
                        } else {
                            if (e.getPacket().getHands().size() > 0) {
                                clientSettings.put(e.getPlayer().getUniqueId(), new ClientSettings(
                                        e.getPacket().getStrings().read(0), e.getPacket().getIntegers().read(0),
                                        ClientChatMode
                                                .getByProtocolLibHand(e.getPacket().getChatVisibilities().read(0)),
                                        e.getPacket().getBooleans().read(0), e.getPacket().getIntegers().read(1),
                                        ClientMainHand.getByProtocolLibHand(e.getPacket().getHands().read(0))));
                            } else {
                                clientSettings.put(e.getPlayer().getUniqueId(), new ClientSettings(
                                        e.getPacket().getStrings().read(0), e.getPacket().getIntegers().read(0),
                                        ClientChatMode
                                                .getByProtocolLibHand(e.getPacket().getChatVisibilities().read(0)),
                                        e.getPacket().getBooleans().read(0), e.getPacket().getIntegers().read(1)));
                            }
                        }
                    }
                });
    }

    public static ClientSettings getClientSettings(Player p) {
        return clientSettings.get(p.getUniqueId());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    private void onQuit(PlayerQuitEvent e) {
        clientSettings.remove(e.getPlayer().getUniqueId());
    }
}