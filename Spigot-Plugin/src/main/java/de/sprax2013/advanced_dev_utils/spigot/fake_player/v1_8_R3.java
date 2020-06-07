package de.sprax2013.advanced_dev_utils.spigot.fake_player;

import com.mojang.authlib.GameProfile;
import de.sprax2013.advanced_dev_utils.spigot.utils.MCPacketUtils;
import net.minecraft.server.v1_8_R3.DataWatcher;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntity.PacketPlayOutEntityLook;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityHeadRotation;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo.EnumPlayerInfoAction;
import net.minecraft.server.v1_8_R3.WorldSettings.EnumGamemode;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftChatMessage;

import java.util.Collections;
import java.util.UUID;

public class v1_8_R3 {
    static PacketPlayOutNamedEntitySpawn createSpawnPacket(int entityID, UUID uuid, Location loc) {
        PacketPlayOutNamedEntitySpawn packet = new PacketPlayOutNamedEntitySpawn();

        // IDs
        MCPacketUtils.setValue(packet, "a", entityID);
        MCPacketUtils.setValue(packet, "b", uuid);

        // Location
        MCPacketUtils.setValue(packet, "c", MCPacketUtils.getFixPoint(loc.getX()));
        MCPacketUtils.setValue(packet, "d", MCPacketUtils.getFixPoint(loc.getY()));
        MCPacketUtils.setValue(packet, "e", MCPacketUtils.getFixPoint(loc.getZ()));
        MCPacketUtils.setValue(packet, "f", MCPacketUtils.getFixAngle(loc.getYaw()));
        MCPacketUtils.setValue(packet, "g", MCPacketUtils.getFixAngle(loc.getPitch()));

        // Current Item
        MCPacketUtils.setValue(packet, "h", 0);

        // MetaData
        DataWatcher dW = new DataWatcher(null);

        dW.a(6, 20F);
        dW.a(10, (byte) 127);

        MCPacketUtils.setValue(packet, "i", dW);

        return packet;
    }

    static PacketPlayOutPlayerInfo createAddPlayerInfoPacket(GameProfile gp) {
        PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.ADD_PLAYER);
        MCPacketUtils.setValue(packet, "b", Collections.singletonList(
                packet.new PlayerInfoData(gp, 1, EnumGamemode.NOT_SET, CraftChatMessage.fromString(gp.getName())[0])));

        return packet;
    }

    static PacketPlayOutPlayerInfo createRemovePlayerInfoPacket(GameProfile gp) {
        PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.REMOVE_PLAYER);
        MCPacketUtils.setValue(packet, "b", Collections.singletonList(
                packet.new PlayerInfoData(gp, 1, EnumGamemode.NOT_SET, CraftChatMessage.fromString(gp.getName())[0])));

        return packet;
    }

    static PacketPlayOutEntityLook createEntityLookPacket(int entityID, float yaw, float pitch) {
        return new PacketPlayOutEntityLook(entityID, MCPacketUtils.getFixAngle(yaw), MCPacketUtils.getFixAngle(pitch),
                true);
    }

    static PacketPlayOutEntityHeadRotation createEntityHeadRotationPacket(int entityID, float yaw) {
        PacketPlayOutEntityHeadRotation packet = new PacketPlayOutEntityHeadRotation();
        MCPacketUtils.setValue(packet, "a", entityID);
        MCPacketUtils.setValue(packet, "b", MCPacketUtils.getFixAngle(yaw));

        return packet;
    }
}