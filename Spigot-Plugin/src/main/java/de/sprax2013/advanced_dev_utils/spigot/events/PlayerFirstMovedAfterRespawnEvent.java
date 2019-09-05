package de.sprax2013.advanced_dev_utils.spigot.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * This event is called after a player moves for the first time after he
 * respawned
 */
public class PlayerFirstMovedAfterRespawnEvent extends Event {
	public static HandlerList handlers = new HandlerList();

	private Player p;

	public PlayerFirstMovedAfterRespawnEvent(Player p) {
		this.p = p;
	}

	/**
	 * @return The player who moved
	 */
	public Player getPlayer() {
		return p;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
}