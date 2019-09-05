package de.sprax2013.advanced_dev_utils.spigot.fake_player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers.EntityUseAction;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import de.sprax2013.advanced_dev_utils.spigot.Main;
import de.sprax2013.advanced_dev_utils.spigot.utils.GameProfileUtils;
import de.sprax2013.advanced_dev_utils.spigot.utils.JavaPluginUtils;
import de.sprax2013.advanced_dev_utils.spigot.utils.MCPacketUtils;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

import java.util.Objects;
import java.util.UUID;

public class FakePlayer {
    private GameProfile gp;
    Location loc;

    Villager vill;
    boolean spawning;

    FakePlayerListener eventListener;

    public FakePlayer(Location loc, String displayName) {
        if (!JavaPluginUtils.isPluginEnabled("ProtocolLib")) {
            throw new IllegalStateException("ProtocolLib is required to use the FakePlayer-API");
        }

        this.loc = loc;

        // TODO Use UUID v2 instead
        this.gp = new GameProfile(
                Bukkit.getOnlineMode() ? UUID.nameUUIDFromBytes(UUID.randomUUID().toString().getBytes())
                        : UUID.randomUUID(),
                Objects.requireNonNull(displayName));
    }

    /**
     * @throws IllegalStateException When no location is set
     */
    public FakePlayer spawn() {
        if (loc == null)
            throw new IllegalStateException("NPC-Location cannot be null");

        if (vill == null) {
            FakePlayerManager.add(this);

            Runnable run = () -> {
                spawning = true;
                vill = loc.getWorld().spawn(
                        new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()),
                        Villager.class);
                spawning = false;

//					try {
//						vill.setMetadata("NPC", new FixedMetadataValue(Main.getInstance(), true));
//
//						vill.setProfession(Profession.NITWIT);
//
//						vill.setAI(false);
//						vill.setSilent(true);
//						vill.setInvulnerable(true);
//					} catch (@SuppressWarnings("unused") Throwable ignore) {
                NBTTagCompound compound = new NBTTagCompound();
                ((CraftEntity) vill).getHandle().e(compound);
                compound.setInt("NoAI", 1);
                compound.setInt("Silent", 1);
                compound.setInt("Invulnerable", 1);

                ((CraftEntity) vill).getHandle().f(compound);
//					}

                vill.teleport(loc);
            };

            if (Bukkit.isPrimaryThread()) {
                run.run();
            } else {
                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), run);
            }
        }

        return this;
    }

    public FakePlayer deSpawn() {
        if (vill != null) {
            Runnable run = () -> {
                vill.remove();
                vill = null;
            };

            if (Bukkit.isPrimaryThread()) {
                run.run();
            } else {
                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), run);
            }

            FakePlayerManager.remove(this);
        }

        return this;
    }

    public FakePlayer reSpawn() {
        deSpawn();
        spawn();

        return this;
    }

    public FakePlayer setDisplayname(String name) {
        MCPacketUtils.setValue(gp, "name", name);

        return this;
    }

    public FakePlayer setSkin(GameProfile gp) {
        this.gp.getProperties().put("textures", new Property("textures", GameProfileUtils.getTextureValue(gp),
                GameProfileUtils.getTextureSignature(gp)));

        return this;
    }

    public FakePlayer setSkin(String textureValue, String textureSignature) {
        gp.getProperties().put("textures", new Property("textures", textureValue, textureSignature));

        return this;
    }

    public FakePlayer setListener(FakePlayerListener eventListener) {
        this.eventListener = eventListener;

        return this;
    }

    public boolean isSpawned() {
        return vill != null;
    }

    public Location getLocation() {
        return loc;
    }

    public FakePlayer setLocation(Location loc) {
        this.loc = loc;

        if (isSpawned()) {
            vill.teleport(loc);
//			reSpawn(); // Maybe teleporting the villager would be enough
        }

        return this;
    }

    int getEntityID() {
        return vill.getEntityId();
    }

    GameProfile getProfile() {
        return gp;
    }

    @SuppressWarnings("deprecation")
    boolean isNPC(PacketContainer pCon) {
        if (pCon.getIntegers().size() > 0 && isSpawned() && pCon.getIntegers().read(0) == getEntityID()) {
            return true;
        } else if (pCon.getType() == PacketType.Play.Server.SPAWN_ENTITY_LIVING) {
            return spawning && EntityType.VILLAGER.getTypeId() == pCon.getIntegers().read(1)
                    && MCPacketUtils.getFixPoint(loc.getBlockX()) == pCon.getIntegers().read(2)
                    && MCPacketUtils.getFixPoint(loc.getBlockY()) == pCon.getIntegers().read(3)
                    && MCPacketUtils.getFixPoint(loc.getBlockZ()) == pCon.getIntegers().read(4);
        } else if (pCon.getType() == PacketType.Play.Server.ENTITY_METADATA) {
            return spawning && FakePlayerManager.potentialEntityIDs.containsKey(pCon.getIntegers().read(0))
                    && (System.currentTimeMillis()
                    - FakePlayerManager.potentialEntityIDs.get(pCon.getIntegers().read(0)) <= 10_000);
        }

        return false;
    }

    void callInteractEvent(Player p, EntityUseAction action) {
        if (eventListener != null) {
            FakePlayer npc = this;

            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
                @Override
                public void run() {
                    try {
                        eventListener.onInteract(new FakePlayerInteractEvent(npc, p, action));
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                }
            });
        }
    }
}