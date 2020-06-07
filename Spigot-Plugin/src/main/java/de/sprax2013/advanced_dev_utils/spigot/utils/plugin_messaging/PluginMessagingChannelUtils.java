package de.sprax2013.advanced_dev_utils.spigot.utils.plugin_messaging;

import de.sprax2013.advanced_dev_utils.spigot.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PluginMessagingChannelUtils {
    // TODO add JavaDocs
    // TODO add support for DataOutputStreams in forward methods
    // TODO throw custom Exception in case no player is online

    public static void init() {
        Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(Main.getInstance(), "BungeeCord");
        Bukkit.getMessenger().registerOutgoingPluginChannel(Main.getInstance(), "adu:customutils");

        Bukkit.getMessenger().registerIncomingPluginChannel(Main.getInstance(), "adu:customutils",
                new PluginMessageListener() {
                    @Override
                    public void onPluginMessageReceived(String channel, Player p, byte[] data) {
                        if (channel.equals("adu:customutils")) {
                            String rawMSG = new String(data);
                            String subChannel = rawMSG.substring(0, rawMSG.indexOf('|'));

                            Bukkit.getPluginManager().callEvent(new CustomPluginMessageEvent(subChannel,
                                    rawMSG.substring(subChannel.length() + 1, rawMSG.length())));
                        }
                    }
                });
    }

    public static boolean sendCustomToBungeeCord(String subChannel, String msg) {
        try {
            Bukkit.getOnlinePlayers().iterator().next().sendPluginMessage(Main.getInstance(), "adu:customutils",
                    (subChannel + "|" + msg).getBytes());

            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public static boolean sendPlayerToServer(Player p, String server) {
        Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(Main.getInstance(), "BungeeCord");

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(bytes);
        try {
            out.writeUTF("Connect");
            out.writeUTF(server);

            p.sendPluginMessage(Main.getInstance(), "BungeeCord", bytes.toByteArray());

            return true;
        } catch (IOException ex) {
            Bukkit.getConsoleSender().sendMessage(Main.prefix + "§cDer Spieler §e" + p.getName()
                    + " §ckonnte nicht zum Server §e" + server + " §cgesendet werde!");
            ex.printStackTrace();

            return false;
        }
    }

    public static boolean sendPlayerToServer(String playerName, String server) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(bytes);

        try {
            out.writeUTF("ConnectOther");
            out.writeUTF(playerName);
            out.writeUTF(server);

            Bukkit.getOnlinePlayers().iterator().next().sendPluginMessage(Main.getInstance(), "BungeeCord",
                    bytes.toByteArray());

            return true;
        } catch (IOException ex) {
            Bukkit.getConsoleSender().sendMessage(Main.prefix + "§cDer Spieler §e" + playerName
                    + " §ckonnte nicht zum Server §e" + server + " §cgesendet werde!");
            ex.printStackTrace();

            return false;
        }
    }

    public static boolean forwardMessageToServer(String srv, String channel, String msg) {
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(bytes);

            out.writeUTF("Forward");
            out.writeUTF(srv);
            out.writeUTF(channel);

            ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
            DataOutputStream msgout = new DataOutputStream(msgbytes);
            msgout.writeUTF(msg);

            out.writeShort(msgbytes.toByteArray().length);
            out.write(msgbytes.toByteArray());

            Bukkit.getOnlinePlayers().iterator().next().sendPluginMessage(Main.getInstance(), "BungeeCord",
                    bytes.toByteArray());

            msgout.close();
            out.close();

            return true;
        } catch (IOException ex) {
            ex.printStackTrace();

            return false;
        }
    }

    public static boolean forwardMessageToAllServers(String msg, String channel) {
        return forwardMessageToServer("ALL", channel, msg);
    }

    public static boolean forwardMessageToAllOnlineServers(String msg, String channel) {
        return forwardMessageToServer("ONLINE", channel, msg);
    }

    public static boolean forwardMessageToPlayer(String playerName, String channel, String msg) {
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(bytes);

            out.writeUTF("ForwardToPlayer");
            out.writeUTF(playerName);
            out.writeUTF(channel);

            ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
            DataOutputStream msgout = new DataOutputStream(msgbytes);
            msgout.writeUTF(msg);

            out.writeShort(msgbytes.toByteArray().length);
            out.write(msgbytes.toByteArray());

            Bukkit.getOnlinePlayers().iterator().next().sendPluginMessage(Main.getInstance(), "BungeeCord",
                    bytes.toByteArray());

            msgout.close();
            out.close();

            return true;
        } catch (IOException ex) {
            ex.printStackTrace();

            return false;
        }
    }

    public static boolean forwardMessageToPlayer(Player p, String channel, String msg) {
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(bytes);

            out.writeUTF("ForwardToPlayer");
            out.writeUTF(p.getName());
            out.writeUTF(channel);

            ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
            DataOutputStream msgout = new DataOutputStream(msgbytes);
            msgout.writeUTF(msg);

            out.writeShort(msgbytes.toByteArray().length);
            out.write(msgbytes.toByteArray());

            p.sendPluginMessage(Main.getInstance(), "BungeeCord", bytes.toByteArray());

            msgout.close();
            out.close();

            return true;
        } catch (IOException ex) {
            ex.printStackTrace();

            return false;
        }
    }

    public static boolean kickPlayer(String playerName, String kickMsg) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(bytes);
        try {
            out.writeUTF("KickPlayer");
            out.writeUTF(playerName);
            out.writeUTF(kickMsg);

            Bukkit.getOnlinePlayers().iterator().next().sendPluginMessage(Main.getInstance(), "BungeeCord",
                    bytes.toByteArray());

            return true;
        } catch (IOException ex) {
            Bukkit.getConsoleSender()
                    .sendMessage(Main.prefix + "§cDer Spieler §e" + playerName + " §ckonnte nicht gekickt werden!");
            ex.printStackTrace();

            return false;
        }
    }
}