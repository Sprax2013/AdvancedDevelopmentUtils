package de.sprax2013.advanced_dev_utils.spigot.fake_player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers.EntityUseAction;
import de.sprax2013.advanced_dev_utils.spigot.Main;
import de.sprax2013.advanced_dev_utils.spigot.utils.BukkitServerUtils;
import de.sprax2013.advanced_dev_utils.spigot.utils.MCPacketUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

public class FakePlayerManager {
    private static boolean registered;

    static Set<FakePlayer> npcs = new HashSet<>();
    static HashMap<Integer, Long> potentialEntityIDs = new HashMap<>();
    static Set<UUID> interacting = new HashSet<>();

    static void add(FakePlayer npc) {
        registerListener();

        npcs.add(npc);
    }

    static void remove(FakePlayer npc) {
        npcs.remove(npc);
    }

    public static void debug() {
        registerListener();
    }

    private static void registerListener() {
        if (!registered) {
            ProtocolLibrary.getProtocolManager()
                    .addPacketListener(new PacketAdapter(Main.getInstance(), ListenerPriority.HIGH,
                            PacketType.Play.Client.USE_ENTITY, PacketType.Play.Server.SPAWN_ENTITY_LIVING,
                            PacketType.Play.Server.ENTITY_METADATA) {
                        @Override
                        public void onPacketReceiving(PacketEvent e) {
                            if (!e.isCancelled()) {
                                for (FakePlayer npc : npcs) {
                                    try {
                                        if (npc.isSpawned()
                                                && e.getPacket().getIntegers().read(0) == npc.getEntityID()) {
                                            e.setCancelled(true);

                                            EntityUseAction action = e.getPacket().getEntityUseActions().read(0);

                                            if (action != EntityUseAction.INTERACT) {
                                                if (!interacting.contains(e.getPlayer().getUniqueId())) {
                                                    interacting.add(e.getPlayer().getUniqueId());

                                                    int currSlot = e.getPlayer().getInventory().getHeldItemSlot();

                                                    // Switch HeldSlot client-side to prevent using the item anyway
                                                    e.getPlayer().getInventory().setHeldItemSlot(currSlot == 0 ? 1 : 0);
                                                    Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(),
                                                            () -> {
                                                                e.getPlayer().getInventory().setHeldItemSlot(currSlot);
                                                                interacting.remove(e.getPlayer().getUniqueId());
                                                            });
                                                }

                                                npc.callInteractEvent(e.getPlayer(), action);
                                            }
                                        }
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }
                                }
                            }
                        }

                        @Override
                        public void onPacketSending(PacketEvent e) {
                            try {
                                if (!e.isCancelled()) {
                                    for (FakePlayer npc : npcs) {
                                        Object addPlayerInfo = null, entitySpawn = null, remPlayerInfo = null,
                                                look = null, headRotation = null;

                                        if (npc.isNPC(e.getPacket())) {
                                            if (!npc.isSpawned()) {
                                                potentialEntityIDs.put(e.getPacket().getIntegers().read(0),
                                                        System.currentTimeMillis());
                                            }

                                            if (e.getPacketType() == PacketType.Play.Server.SPAWN_ENTITY_LIVING) {
                                                switch (BukkitServerUtils.getBukkitVersion()) {
                                                    case "v1_8_R3":
                                                        addPlayerInfo = v1_8_R3.createAddPlayerInfoPacket(npc.getProfile());
                                                        entitySpawn = v1_8_R3.createSpawnPacket(
                                                                e.getPacket().getIntegers().read(0),
                                                                npc.getProfile().getId(), npc.getLocation());
                                                        remPlayerInfo = v1_8_R3
                                                                .createRemovePlayerInfoPacket(npc.getProfile());

                                                        look = v1_8_R3.createEntityLookPacket(
                                                                e.getPacket().getIntegers().read(0),
                                                                npc.getLocation().getYaw(), npc.getLocation().getPitch());
                                                        headRotation = v1_8_R3.createEntityHeadRotationPacket(
                                                                e.getPacket().getIntegers().read(0),
                                                                npc.getLocation().getYaw());
                                                        break;
                                                    case "v1_12_R1":
//													addPlayerInfo = v1_12_R1.createAddPlayerInfoPacket(npc.getProfile());
//													entitySpawn = v1_12_R1.createSpawnPacket(
//															e.getPacket().getIntegers().read(0),
//															npc.getProfile().getId(), npc.getLocation());
//													remPlayerInfo = v1_12_R1
//															.createRemovePlayerInfoPacket(npc.getProfile());
//
//													look = v1_12_R1.createEntityLookPacket(
//															e.getPacket().getIntegers().read(0),
//															npc.getLocation().getYaw(), npc.getLocation().getPitch());
//													headRotation = v1_12_R1.createEntityHeadRotationPacket(
//															e.getPacket().getIntegers().read(0),
//															npc.getLocation().getYaw());
                                                        break;
                                                    default:
                                                        Bukkit.getConsoleSender().sendMessage(Main.prefix
                                                                + "§eFakePlayer§7-§eAPI §cis not supported for this version §7(§e"
                                                                + BukkitServerUtils.getBukkitVersion() + "§7)");
                                                        break;
                                                }

                                                if (addPlayerInfo != null) {
                                                    e.setCancelled(true);

                                                    final Object finalAddPlayerInfo = addPlayerInfo,
                                                            finalEntitySpawn = entitySpawn,
                                                            finalRemPlayerInfo = remPlayerInfo,

                                                            finalLook = look, finalHeadRotation = headRotation;
                                                    Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(),
                                                            () -> {
                                                                MCPacketUtils.sendPacket(e.getPlayer(),
                                                                        finalAddPlayerInfo);
                                                                MCPacketUtils.sendPacket(e.getPlayer(),
                                                                        finalEntitySpawn);

                                                                MCPacketUtils.sendPacket(e.getPlayer(), finalLook);
                                                                MCPacketUtils.sendPacket(e.getPlayer(),
                                                                        finalHeadRotation);

                                                                Bukkit.getScheduler().scheduleSyncDelayedTask(
                                                                        Main.getInstance(), () -> {
                                                                            if (e.getPlayer().isOnline()) {
                                                                                MCPacketUtils.sendPacket(
                                                                                        e.getPlayer(),
                                                                                        finalRemPlayerInfo);
                                                                            }
                                                                        }, 15);
                                                            });
                                                }
                                            } else if (e.getPacketType() == PacketType.Play.Server.ENTITY_METADATA) {
                                                e.setCancelled(true);
                                            }
                                        }
                                    }
                                }
                            } catch (Throwable th) {
                                th.printStackTrace();
                            }
                        }
                    });

            Bukkit.getPluginManager().registerEvents(new Listener() {
                /* cancel as early as possible */
                @EventHandler(priority = EventPriority.LOWEST)
                private void onDamage(EntityDamageEvent e) {
                    for (FakePlayer npc : npcs) {
                        if (e.getEntity().getEntityId() == npc.getEntityID()) {
                            e.setCancelled(true);
                            e.setDamage(0);
                            break;
                        }
                    }
                }

                /* Force cancel */

                @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
                private void onDamage_FORCE(EntityDamageEvent e) {
                    for (FakePlayer npc : npcs) {
                        if (e.getEntity().getEntityId() == npc.getEntityID()) {
                            e.setCancelled(true);
                            e.setDamage(0);
                            break;
                        }
                    }
                }
            }, Main.getInstance());

            Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), () -> {
                for (Entry<Integer, Long> entry : potentialEntityIDs.entrySet()) {
                    if ((System.currentTimeMillis() - entry.getValue()) <= 10_000) {
                        potentialEntityIDs.remove(entry.getKey());
                    }
                }
            }, 72000, 72000); // 1h

            Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), () -> {
                for (FakePlayer npc : npcs) {
                    Object look = v1_8_R3.createEntityLookPacket(
                            npc.getEntityID(), npc.getLocation().getYaw(), npc.getLocation().getPitch()),
                            headRotation = v1_8_R3.createEntityHeadRotationPacket(npc.getEntityID(), npc.getLocation().getYaw());

                    Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(),
                            () -> {
                                for (Player inWorld : npc.getLocation().getWorld().getPlayers()) {
                                    MCPacketUtils.sendPacket(inWorld, look);
                                    MCPacketUtils.sendPacket(inWorld, headRotation);
                                }
                            });
                }
            }, 20 * 2, 20 * 5);

            registered = true;
        }
    }
}