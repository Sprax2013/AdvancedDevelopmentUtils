package de.sprax2013.advanced_dev_utils.spigot.nick;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers.PlayerInfoAction;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.mojang.authlib.GameProfile;
import de.sprax2013.advanced_dev_utils.misc.StringUtils;
import de.sprax2013.advanced_dev_utils.mojang.MojangAPI;
import de.sprax2013.advanced_dev_utils.spigot.Main;
import de.sprax2013.advanced_dev_utils.spigot.utils.BukkitServerUtils;
import de.sprax2013.advanced_dev_utils.spigot.utils.GameProfileUtils;
import de.sprax2013.advanced_dev_utils.spigot.utils.MCPacketUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// TODO Anti-TabComplete
public class PlayerNick implements Listener {
    static HashMap<UUID, GameProfile> playersNick = new HashMap<>();

    private static final ExecutorService pool = Executors.newCachedThreadPool();

    private final static PacketAdapter packetAdapter = new PacketAdapter(Main.getInstance(), ListenerPriority.HIGH,
            PacketType.Play.Server.PLAYER_INFO, PacketType.Play.Server.TAB_COMPLETE) {
        @Override
        public void onPacketSending(PacketEvent e) {
            if (!playersNick.isEmpty()) {
                if (e.getPacket().getType() == PacketType.Play.Server.PLAYER_INFO) {
                    if (e.getPacket().getPlayerInfoAction().read(0) == PlayerInfoAction.ADD_PLAYER) {
                        for (int i = 0; i < e.getPacket().getPlayerInfoDataLists().size(); i++) {
                            List<PlayerInfoData> pInfoDataList = e.getPacket().getPlayerInfoDataLists().read(i);
                            List<PlayerInfoData> newList = new ArrayList<>();

                            boolean atLeastOneNewEntry = false;
                            for (PlayerInfoData pInfoData : pInfoDataList) {
                                if (playersNick.containsKey(pInfoData.getProfile().getUUID())) {
                                    newList.add(new PlayerInfoData(
                                            WrappedGameProfile
                                                    .fromHandle(playersNick.get(pInfoData.getProfile().getUUID())),
                                            pInfoData.getLatency(), pInfoData.getGameMode(),
                                            pInfoData.getDisplayName()));

                                    atLeastOneNewEntry = true;
                                } else {
                                    newList.add(pInfoData);
                                }

                                if (atLeastOneNewEntry) {
                                    e.getPacket().getPlayerInfoDataLists().write(i, newList);
                                }
                            }
                        }
                    }
                } else if (e.getPacket().getType() == PacketType.Play.Server.TAB_COMPLETE) {
                    List<String> newMatches = new ArrayList<>();

                    String[] matches = e.getPacket().getStringArrays().read(0);
                    @SuppressWarnings("deprecation")
                    String base = StringUtils.getStartingWith(matches, true).toLowerCase();

                    if (matches.length == 1) {
                        base = Character.toString(base.charAt(0));
                    }

                    for (String match : matches) {
                        if (match.startsWith("/")) {
                            newMatches.add(match);
                        } else {
                            Player p = getPlayer(match);

                            if (p == null || !isNicked(p)) {
                                newMatches.add(match);
                            }
                        }
                    }

                    if (!base.trim().isEmpty()) {
                        for (String nick : getUsedNicks()) {
                            if (nick.toLowerCase().startsWith(base)) {
                                newMatches.add(nick);
                            }
                        }
                    }

                    e.getPacket().getStringArrays().write(0, newMatches.toArray(new String[0]));
                }
            }
        }
    };

    private static boolean protocolLibListener = false;

    private static boolean bukkitListener = false;

    @SuppressWarnings("unused")
    public static void nickPlayer(JavaPlugin plugin, Player p, UUID uuid, Runnable onFinish) {
        registerListener();

        pool.execute(() -> {
            boolean success = true;

            //noinspection SwitchStatementWithTooFewBranches
            switch (BukkitServerUtils.getBukkitVersion()) {
                case "v1_8_R3":
                    v1_8_R3.nickPlayer(p, createDestProfile(p.getUniqueId(), uuid), createDestProfileForSelf(p, uuid));
                    break;
//				case "v1_12_R1":
//					v1_12_R1.nickPlayer(p, createDestProfile(p.getUniqueId(), uuid), createDestProfileForSelf(p, uuid));
//					break;
                default:
                    success = false;
                    break;
            }

            if (success && plugin != null && onFinish != null) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
                    Bukkit.getPluginManager().callEvent(new PlayerNickEvent(p));

                    onFinish.run();
                });
            } else if (plugin != null && onFinish != null) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, onFinish);
            }
        });
    }

    @SuppressWarnings("unused")
    public static void unNickPlayer(JavaPlugin plugin, Player p, Runnable onFinish) {
        registerListener();

        pool.execute(() -> {
            boolean success = true;

            //noinspection SwitchStatementWithTooFewBranches
            switch (BukkitServerUtils.getBukkitVersion()) {
                case "v1_8_R3":
                    v1_8_R3.unNickPlayer(p);
                    break;
//				case "v1_12_R1":
//					v1_12_R1.unNickPlayer(p);
//					break;
                default:
                    success = false;
                    break;
            }

            if (success && plugin != null && onFinish != null) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
                    Bukkit.getPluginManager().callEvent(new PlayerUnNickEvent(p));

                    onFinish.run();
                });
            } else if (plugin != null && onFinish != null) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, onFinish);
            }
        });
    }

    /**
     * This method is used by the plugin 'SuperNick' by Sprax2013.<br>
     * It won't do nothing if it is called by another plugin!
     *
     * @param plugin The plugin
     * @param p      The player
     * @param uuid   The nick-UUID
     */
    @SuppressWarnings("unused")
    public static void nickPlayerWhileLogin(JavaPlugin plugin, Player p, UUID uuid, Runnable onFinish) {
        registerListener();

        if (plugin != null && plugin.getName().equals("SuperNick")) {
            playersNick.put(p.getUniqueId(), createDestProfile(p.getUniqueId(), uuid));

            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
                Bukkit.getPluginManager().callEvent(new PlayerNickEvent(p));

                if (onFinish != null) {
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, onFinish);
                }
            });
        }
    }

    public static List<Player> getNickedPlayers() {
        List<Player> list = new ArrayList<>();

        for (UUID uuid : playersNick.keySet()) {
            list.add(Bukkit.getPlayer(uuid));
        }

        return list;
    }

    public static List<String> getUsedNicks() {
        List<String> list = new ArrayList<>();

        for (GameProfile gp : playersNick.values()) {
            list.add(gp.getName());
        }

        return list;
    }

    public static boolean isNicked(Player p) {
        return playersNick.containsKey(p.getUniqueId());
    }

    public static String getNick(Player p) {
        GameProfile gp = playersNick.get(p.getUniqueId());

        if (gp != null) {
            return gp.getName();
        }

        return null;
    }

    @SuppressWarnings("unused")
    public static GameProfile getNicksGameProfile(Player p) {
        if (isNicked(p)) {
            return playersNick.get(p.getUniqueId());
        }

        return null;
    }

    @SuppressWarnings("unused")
    public static UUID getNickUUID(Player p) {
        GameProfile gp = playersNick.get(p.getUniqueId());

        if (gp != null) {
            return MojangAPI.getUUID(ChatColor.stripColor(gp.getName())).getUUID();
        }

        return null;
    }

    public static Player getPlayer(String name) {
        Player p = Bukkit.getPlayerExact(name);

        if (p == null) {
            for (Player nicked : getNickedPlayers()) {
                if (name.equalsIgnoreCase(getNick(nicked))) {
                    p = nicked;
                    break;
                }
            }
        }

        return p;
    }

    // TODO don't modify the GameProfile!! The cached Profile will change!
    static GameProfile createDestProfile(UUID playerUUID, UUID uuid) {
        GameProfile gp = GameProfileUtils.toGameProfile(uuid);

        if (gp != null) {
            MCPacketUtils.setValue(gp, "id", playerUUID);
        }

        return gp;
    }

    // TODO use "9a7d9d82-218c-4aed-ac47-89ff34f84b3e" for a non-Skin Player

    /**
     * Creates a GameProfile containing Steve or Alex Skin in case the nick does not
     * have a skin.<br>
     * This prevents the own-client to display the original Skin.<br>
     * The original Skin would only be visible to himself. Other players would still
     * see Alex and Steve.<br>
     * <br>
     * Self will use a Skin. Others will use their default skin provided by their
     * texturepack instead.
     *
     * @return The GameProfile or null
     */
    @SuppressWarnings("unused")
    static GameProfile createDestProfileForSelf(Player p, UUID uuid) {
//		MojangProfile profile = MojangAPI.getProfile(uuid);

//		if (!profile.hasTextureProp()) {
//			GameProfile gp = GameProfileUtils.toGameProfile(uuid);
//
//			if (gp != null) {
//				MCPacketUtils.setValue(gp, "id", p.getUniqueId());
//
//				gp.getProperties().clear();
//				if (profile.getTextureProp().isDefaultSteve()) {
//					gp.getProperties().put("textures", new Property("textures",
//							"eyJ0aW1lc3RhbXAiOjE1MjY4MDczNDc1MjgsInByb2ZpbGVJZCI6Ijg2NjdiYTcxYjg1YTQwMDRhZjU0NDU3YTk3MzRlZWQ3IiwicHJvZmlsZU5hbWUiOiJTdGV2ZSIsInNpZ25hdHVyZVJlcXVpcmVkIjp0cnVlLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGMxYzc3Y2U4ZTU0OTI1YWI1ODEyNTQ0NmVjNTNiMGNkZDNkMGNhM2RiMjczZWI5MDhkNTQ4Mjc4N2VmNDAxNiJ9LCJDQVBFIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjc2N2Q0ODMyNWVhNTMyNDU2MTQwNmI4YzgyYWJiZDRlMjc1NWYxMTE1M2NkODVhYjA1NDVjYzIifX19",
//							"fzBEi4s/C8nj18tVHIWBlWQEObm65IfATc4P83iB3PEHE44M0wGQjpyT+ZQGXcbgNeVFQ0FPyEwA8/SC3iGrZ5eKy8s5LCfI5LvOPWYQLHPlwEmRgix9dhpf+vKVrirUEkzDuY/oM3cRfLBbNk1afl+df36oixeG4cqsbLEnSJRu/kOMtA5Fcic2NQf7g402pNeqD2D8cq4Hbe47g2UcfIRVsGt0fLif2qsojbha5m6dYYUEfJOmNcGqPiubznxgGS3vpQ8GHRZrntMJbmywrDAOZjgxNmi+Bdq476nJ84NZycBe3BqgtmKFp+WF6z6jxPeQ1ZcUnlEzmsRJwhfS7zHb4Ujyvzn5BxzMegTmsP33cplCydcd/2oXhKnMj4xtmQtrHS10aUs4oa2M7Ak60SVm11qAOR1KwGvMcDY37shvzjK/4cwuspfsgSBIlVC6MJGBgqmc571LWixSJYBRl2HvW/ao43XbN8k9/oegh7SBJMusdO3ADtbOmt84GmzoEbLfWTi4uEkJpYkPfK4UiqvTnB0Uw+KyRJCdoRwpDNRVMZFTb/eJO4Cr2tAIVTM1JR1E5hWaQ7IQBH+Bwj39JjBpK7MLpx0jjZV+y09+u3BrUIVgrLYFQP0WZxypw45+SAuk/P35hG10ERGjwYRZ6PMWnevq13fYUHlc0Crbn3I="));
//				} else {
//					gp.getProperties().put("textures", new Property("textures",
//							"eyJ0aW1lc3RhbXAiOjE1NDE4NjM0MjM0ODYsInByb2ZpbGVJZCI6IjZhYjQzMTc4ODlmZDQ5MDU5N2Y2MGY2N2Q5ZDc2ZmQ5IiwicHJvZmlsZU5hbWUiOiJNSEZfQWxleCIsInNpZ25hdHVyZVJlcXVpcmVkIjp0cnVlLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODNjZWU1Y2E2YWZjZGIxNzEyODVhYTAwZTgwNDljMjk3YjJkYmViYTBlZmI4ZmY5NzBhNTY3N2ExYjY0NDAzMiIsIm1ldGFkYXRhIjp7Im1vZGVsIjoic2xpbSJ9fX19",
//							"VpXY4bvRGvivKNdWFXyVR8n2gijDHa3ysmAK12e8FFwrCXSAUegA1DgkjLn/WLs5ygv8VZ6EWSQaVQ0Xt+xpwvX5oj86dlQnqxCNYfF9/FYxQm8K5kGlIoCwuL1rKhYCIB83UWmFEmKYCEGva8iUgSvmhSc+wpHi3gtE8mJuGBifnLO/pZVok50U92odhG5QkRaTE+xjByDaFOIzv/K/Oi579blJdELh9JwA5UFTBxLO6kmzQwuzt60WccOYal+FX70mbyWOHJb5sQVWZVNHR+5l1b04iBizTor484Dv2hAq4GVpfRzB0u9xAMeaKgCfvBVlGZfZjeuT+yq1WNSE1a2Fi2DeSWx7ZeAKd1UiWclizsXnUkgKsHMK93MyBIeqvJP5tNIXOuOfYoRvuDlftflXCVp/CNFye76LzdkiRc9x+AwMAUWUQdIxihtfeT/loSY+E/ZvvVB5QVOVCukZsnMwVZAjSiXW9armllgvtVEFNmP6iMzm0FbUBzbg5gLcKSeMdOMrETRzPxhAnje1PRs+FJ4kXfAZdMNwdT2rHzz4bFCISRxps9ZeY/rcFiaFt25gxuORekijBrRO86VRkMyo8Pje4KfX/E29CSVRnXv6d+PkQTSbCfIYMRWUuHQE7SVSbw8hmnGC0WO9cLsg7hYHkO0LKHuB+9Sw3y8WPKo="));
//				}
//			}
//		}

        return null;
    }

    static void sleep(@SuppressWarnings("SameParameterValue") long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    private void onQuit(PlayerQuitEvent e) {
        playersNick.remove(e.getPlayer().getUniqueId());
    }

    // Listener-Methods
    private static void registerListener() {
        if (!bukkitListener) {
            Bukkit.getPluginManager().registerEvents(new PlayerNick(), Main.getInstance());

            bukkitListener = true;
        }

        if (!protocolLibListener) {
            ProtocolLibrary.getProtocolManager().addPacketListener(packetAdapter);

            protocolLibListener = true;
        }
    }
}