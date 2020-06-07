package de.sprax2013.advanced_dev_utils.spigot.fake_player;

import com.comphenix.protocol.wrappers.EnumWrappers.EntityUseAction;
import org.bukkit.entity.Player;

public class FakePlayerInteractEvent {
    private FakePlayer npc;

    private Player p;

    private EntityUseAction action;

    public FakePlayerInteractEvent(FakePlayer npc, Player p, EntityUseAction action) {
        this.npc = npc;
        this.p = p;
        this.action = action;
    }

    /**
     * @return The PlayerNPC
     */
    public FakePlayer getNPC() {
        return npc;
    }

    /**
     * @return The player
     */
    public Player getPlayer() {
        return p;
    }

    /**
     * @return The action
     */
    public InteractAction getAction() {
        return InteractAction.getByProtocolLib(action);
    }
}