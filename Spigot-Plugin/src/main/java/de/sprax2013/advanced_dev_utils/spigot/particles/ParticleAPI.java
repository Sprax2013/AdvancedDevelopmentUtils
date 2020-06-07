package de.sprax2013.advanced_dev_utils.spigot.particles;

import de.sprax2013.advanced_dev_utils.spigot.utils.BukkitServerUtils;
import de.sprax2013.advanced_dev_utils.spigot.utils.MCPacketUtils;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collection;

public class ParticleAPI {
    public static void send(Player p, ParticleBuilder particle) {
        send(Arrays.asList(p), particle);
    }

    public static void send(Collection<? extends Player> players, ParticleBuilder particle) {
        try {
            MCPacketUtils.sendPacket(players, particle.createPacket());
        } catch (@SuppressWarnings("unused") Exception ex) {
            throw new IllegalStateException(
                    "This Spigot-Version (" + BukkitServerUtils.getBukkitVersion() + ") is not supported!");
        }
    }
}