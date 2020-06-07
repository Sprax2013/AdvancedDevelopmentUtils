package de.sprax2013.advanced_dev_utils.spigot.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerTimeoutEvent extends Event {
    public static HandlerList handlers = new HandlerList();

    private Player p;

    private int lastPing;
    private String quitMsg;

    public PlayerTimeoutEvent(Player p, int lastPing, String quitMsg) {
        this.p = p;

        this.lastPing = lastPing;
        this.quitMsg = quitMsg;
    }

    public Player getPlayer() {
        return this.p;
    }

    public int getLastPing() {
        return this.lastPing;
    }

    public String getQuitMessage() {
        return this.quitMsg;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}