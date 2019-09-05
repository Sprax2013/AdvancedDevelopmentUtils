//package de.sprax2013.advanced_dev_utils.spigot.nick;
//
//import java.util.Arrays;
//import java.util.HashSet;
//import java.util.Set;
//import java.util.UUID;
//import java.util.stream.Collectors;
//
//import org.bukkit.Bukkit;
//import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
//import org.bukkit.craftbukkit.v1_12_R1.util.CraftChatMessage;
//import org.bukkit.entity.Player;
//
//import com.mojang.authlib.GameProfile;
//
//import de.sprax2013.advanced_dev_utils.spigot.Main;
//import de.sprax2013.advanced_dev_utils.spigot.utils.MCPacketUtils;
//import net.minecraft.server.v1_12_R1.EntityPlayer;
//import net.minecraft.server.v1_12_R1.PacketPlayOutEntityDestroy;
//import net.minecraft.server.v1_12_R1.PacketPlayOutEntityHeadRotation;
//import net.minecraft.server.v1_12_R1.PacketPlayOutNamedEntitySpawn;
//import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerInfo;
//import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerInfo.EnumPlayerInfoAction;
//import net.minecraft.server.v1_12_R1.PacketPlayOutPosition;
//import net.minecraft.server.v1_12_R1.PacketPlayOutRespawn;
//
//public class v1_12_R1 {
//	/**
//	 * @see PlayerNick#nickPlayer(org.bukkit.plugin.java.JavaPlugin, Player, UUID,
//	 *      boolean, Runnable)
//	 */
//	static void nickPlayer(Player p, GameProfile publicGP, GameProfile selfGP) {
//		EntityPlayer eP = ((CraftPlayer) p).getHandle();
//
//		PacketPlayOutPlayerInfo removeInfo = createRemovePlayerInfoPacket(eP);
//
//		PacketPlayOutPlayerInfo addInfo = createAddPlayerInfoPacket(eP, publicGP);
//
//		PlayerNick.playersNick.put(p.getUniqueId(), publicGP);
//
//		Set<? extends Player> onlinePlayerNotP = Bukkit.getOnlinePlayers().stream()
//				.filter(online -> online != p && online.canSee(p)).collect(Collectors.toSet());
//
//		MCPacketUtils.sendPacket(onlinePlayerNotP, new PacketPlayOutEntityDestroy(p.getEntityId()));
//		MCPacketUtils.sendPacket(onlinePlayerNotP, removeInfo);
//
//		PlayerNick.sleep(125);
//
//		MCPacketUtils.sendPacket(onlinePlayerNotP, addInfo);
//		MCPacketUtils.sendPacket(onlinePlayerNotP, new PacketPlayOutNamedEntitySpawn(eP));
//
//		// Update Head-Rotation
//		MCPacketUtils.sendPacket(onlinePlayerNotP,
//				new PacketPlayOutEntityHeadRotation(eP, MCPacketUtils.getFixAngle(p.getLocation().getYaw())));
//
//		// if (updateSelf) {
//		MCPacketUtils.sendPacket(p, removeInfo);
//
//		PlayerNick.sleep(125);
//
//		MCPacketUtils.sendPacket(p, createAddPlayerInfoPacket(eP, (selfGP == null ? publicGP : selfGP)));
//
//		PlayerNick.sleep(125);
//
//		// Respawn Player
//		MCPacketUtils.sendPacket(p, createPlayerRespawnPacket(eP));
//
//		// Avoid Client stuck in void & wrong movement false-positive (with fake
//		// PacketID -> Server won't check it for false movement)
//		MCPacketUtils.sendPacket(p, new PacketPlayOutPosition(p.getLocation().getX(), p.getLocation().getY(),
//				p.getLocation().getZ(), p.getLocation().getYaw(), p.getLocation().getPitch(), new HashSet<>(), -9432));
//
//		// Prevent Client-Side Bugs
//		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
//			@Override
//			public void run() {
//				// Update Client-Side EXP
//				p.setExp(p.getExp());
//
//				// Update Client-Side Abilities
//				p.setWalkSpeed(p.getWalkSpeed());
//
//				// Update Client-Side
//				p.setHealthScale(p.getHealthScale());
//
//				// Update Client-Side Inventory-Content
//				p.updateInventory();
//
//				// Update Client-Side CurrentSlot
//				int oldSlot = p.getInventory().getHeldItemSlot();
//				if (oldSlot == 0) {
//					p.getInventory().setHeldItemSlot(1);
//				} else {
//					p.getInventory().setHeldItemSlot(0);
//				}
//				p.getInventory().setHeldItemSlot(oldSlot);
//			}
//		});
//		// }
//	}
//
//	/**
//	 * @see PlayerNick#unNickPlayer(org.bukkit.plugin.java.JavaPlugin, Player,
//	 *      boolean, Runnable)
//	 */
//	static void unNickPlayer(Player p) {
//		EntityPlayer eP = ((CraftPlayer) p).getHandle();
//
//		PacketPlayOutPlayerInfo removeInfo = createRemovePlayerInfoPacket(eP);
//
//		PacketPlayOutPlayerInfo addInfo = createAddPlayerInfoPacket(eP, eP.getProfile());
//
//		PlayerNick.playersNick.remove(p.getUniqueId());
//
//		Set<? extends Player> onlinePlayerNotP = Bukkit.getOnlinePlayers().stream()
//				.filter(online -> online != p && online.canSee(p)).collect(Collectors.toSet());
//
//		MCPacketUtils.sendPacket(onlinePlayerNotP, new PacketPlayOutEntityDestroy(p.getEntityId()));
//		MCPacketUtils.sendPacket(onlinePlayerNotP, removeInfo);
//
//		PlayerNick.sleep(125);
//
//		MCPacketUtils.sendPacket(onlinePlayerNotP, addInfo);
//		MCPacketUtils.sendPacket(onlinePlayerNotP, new PacketPlayOutNamedEntitySpawn(eP));
//
//		// Update Head-Rotation
//		MCPacketUtils.sendPacket(onlinePlayerNotP,
//				new PacketPlayOutEntityHeadRotation(eP, MCPacketUtils.getFixAngle(p.getLocation().getYaw())));
//
//		// if (updateSelf) {
//		MCPacketUtils.sendPacket(p, removeInfo);
//
//		PlayerNick.sleep(125);
//
//		MCPacketUtils.sendPacket(p, addInfo);
//
//		PlayerNick.sleep(125);
//
//		// Respawn Player
//		MCPacketUtils.sendPacket(p, createPlayerRespawnPacket(eP));
//
//		// Avoid Client stuck in void & wrong movement false-positive (with fake
//		// PacketID -> Server won't check it for false movement)
//		MCPacketUtils.sendPacket(p, new PacketPlayOutPosition(p.getLocation().getX(), p.getLocation().getY(),
//				p.getLocation().getZ(), p.getLocation().getYaw(), p.getLocation().getPitch(), new HashSet<>(), -9432));
//
//		// Prevent Client-Side Bugs
//		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
//			@Override
//			public void run() {
//				// Update Client-Side EXP
//				p.setExp(p.getExp());
//
//				// Update Client-Side Abilities
//				p.setWalkSpeed(p.getWalkSpeed());
//
//				// Update Client-Side
//				p.setHealthScale(p.getHealthScale());
//
//				// Update Client-Side Inventory-Content
//				p.updateInventory();
//
//				// Update Client-Side CurrentSlot
//				int oldSlot = p.getInventory().getHeldItemSlot();
//				if (oldSlot == 0) {
//					p.getInventory().setHeldItemSlot(1);
//				} else {
//					p.getInventory().setHeldItemSlot(0);
//				}
//				p.getInventory().setHeldItemSlot(oldSlot);
//			}
//		});
//		// }
//	}
//
//	// Packets
//	private static PacketPlayOutPlayerInfo createRemovePlayerInfoPacket(EntityPlayer eP) {
//		PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo();
//		MCPacketUtils.setValue(packet, "a", EnumPlayerInfoAction.REMOVE_PLAYER);
//		MCPacketUtils.setValue(packet, "b", Arrays.asList(packet.new PlayerInfoData(eP.getProfile(), -1, null, null)));
//
//		return packet;
//	}
//
//	private static PacketPlayOutPlayerInfo createAddPlayerInfoPacket(EntityPlayer eP, GameProfile publicGP) {
//		PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo();
//		MCPacketUtils.setValue(packet, "a", EnumPlayerInfoAction.ADD_PLAYER);
//		MCPacketUtils.setValue(packet, "b", Arrays.asList(packet.new PlayerInfoData(publicGP, eP.ping,
//				eP.playerInteractManager.getGameMode(), CraftChatMessage.fromString(publicGP.getName())[0])));
//
//		return packet;
//	}
//
//	@SuppressWarnings("deprecation")
//	private static PacketPlayOutRespawn createPlayerRespawnPacket(EntityPlayer eP) {
//		return new PacketPlayOutRespawn(eP.getWorld().getWorld().getEnvironment().getId(),
//				eP.getWorld().getDifficulty(), eP.getWorld().getWorldData().getType(),
//				eP.playerInteractManager.getGameMode());
//	}
//}