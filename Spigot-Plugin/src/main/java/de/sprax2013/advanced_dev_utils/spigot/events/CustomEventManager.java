package de.sprax2013.advanced_dev_utils.spigot.events;

import de.sprax2013.advanced_dev_utils.spigot.Main;
import de.sprax2013.advanced_dev_utils.spigot.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * This class calls the custom events.
 */
public class CustomEventManager implements Listener {
    private Set<Player> didNotMove = new HashSet<>();

    // PlayerFirstMovedAfterRespawnEvent
    private Set<Player> diedRecently = new HashSet<>();

    private Set<Player> gotTeleportedRecently = new HashSet<>();

    // PlayerTimedOutEvent
    private static PlayerManager playerManager;

    public static void init() {
        playerManager = new PlayerManager(Main.getInstance()) {
            @Override
            public int schedule(JavaPlugin plugin, ObservedRunnable runnable) {
                return Bukkit.getScheduler().runTaskTimer(plugin, runnable, 20, 20).getTaskId();
            }

            @Override
            public void cancelTask(JavaPlugin plugin, int taskID) {
                try {
                    Bukkit.getScheduler().cancelTask(taskID);
                } catch (@SuppressWarnings("unused") Exception ignore) {
                }
            }
        };
    }

    @EventHandler(priority = EventPriority.MONITOR)
    private void onPlayerMove(PlayerMoveEvent e) {
        if (!e.isCancelled()) {
            // PlayerFirstMovedEvent
            if (didNotMove.contains(e.getPlayer())) {
                didNotMove.remove(e.getPlayer());

                Bukkit.getPluginManager().callEvent(new PlayerFirstMovedEvent(e.getPlayer()));
            }

            // PlayerFirstMovedAfterTeleportEvent
            if (gotTeleportedRecently.contains(e.getPlayer())) {
                gotTeleportedRecently.remove(e.getPlayer());

                Bukkit.getPluginManager().callEvent(new PlayerFirstMovedAfterTeleportEvent(e.getPlayer()));
            }

            // PlayerFirstMovedAfterRespawnEvent
            if (diedRecently.contains(e.getPlayer())) {
                diedRecently.remove(e.getPlayer());

                Bukkit.getPluginManager().callEvent(new PlayerFirstMovedAfterRespawnEvent(e.getPlayer()));
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    private void onTeleport(PlayerTeleportEvent e) {
        if (!e.isCancelled()) {
            gotTeleportedRecently.add(e.getPlayer());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    private void onPlayerRespawn(PlayerDeathEvent e) {
        // PlayerFirstMovedAfterRespawnEvent
        if (e.getEntity().isDead()) {
            diedRecently.add(e.getEntity());
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onJoin(PlayerJoinEvent e) {
        didNotMove.add(e.getPlayer());

        playerManager.track(e.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    private void onQuit(PlayerQuitEvent e) {
        didNotMove.remove(e.getPlayer());
        diedRecently.remove(e.getPlayer());
        gotTeleportedRecently.remove(e.getPlayer());
    }

    // PlayerTimedOutEvent
    @EventHandler(priority = EventPriority.MONITOR)
    private void onJoin1(PlayerJoinEvent e) {
        playerManager.track(e.getPlayer());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onQuit1(PlayerQuitEvent e) {
        ConnectionObserver observer = playerManager.unTrack(e.getPlayer());

        if (observer != null) {
            int threshold = 20;

            if (observer.equalPings > threshold) {
                PlayerTimeoutEvent timeoutEvent = new PlayerTimeoutEvent(e.getPlayer(), observer.ping,
                        "§e" + e.getPlayer().getName() + " timed out");
                Bukkit.getPluginManager().callEvent(timeoutEvent);

                e.setQuitMessage(timeoutEvent.getQuitMessage());
            }
        }
    }
}

abstract class PlayerManager {
    private JavaPlugin plugin;

    private final Map<UUID, ConnectionObserver> connectionObserverMap = new HashMap<>();

    protected PlayerManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    protected abstract int schedule(JavaPlugin plugin, ObservedRunnable runnable);

    protected abstract void cancelTask(JavaPlugin plugin, int taskId);

    public void track(Player p) {
        UUID uuid = p.getUniqueId();

        if (connectionObserverMap.containsKey(uuid)) {
            unTrack(p);
        }

        ConnectionObserver observer = new ConnectionObserver(p) {
        };

        observer.taskId = schedule(this.plugin, observer);

        connectionObserverMap.put(uuid, observer);
    }

    public ConnectionObserver unTrack(Player p) {
        ConnectionObserver observer = connectionObserverMap.remove(p.getUniqueId());

        if (observer != null) {
            observer.cancelled = true;
            cancelTask(this.plugin, observer.taskId);
        }

        return observer;
    }
}

abstract class ConnectionObserver extends ObservedRunnable {
    private Player player;
    public int ping;
    public int equalPings = 0;

    ConnectionObserver(Player player) {
        this.player = player;
    }

    @Override
    public void run() {
        if (cancelled) {
            return;
        }

        int lastPing = this.ping;
        this.ping = PlayerUtils.getPing(this.player);

        if (lastPing == this.ping) {
            equalPings++;
        } else {
            equalPings = 0;
        }
    }
}

class ObservedRunnable implements Runnable {
    boolean cancelled = false;
    int taskId = -1;

    @Override
    public void run() {
    }
}