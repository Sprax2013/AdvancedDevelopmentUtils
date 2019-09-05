package de.sprax2013.advanced_dev_utils.spigot.utils.plugin_messaging;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CustomPluginMessageEvent extends Event {
	// TODO JavaDocs

	public static HandlerList handlers = new HandlerList();

	private final String subChannel, msg;

	public CustomPluginMessageEvent(String subChannel, String msg) {
		this.subChannel = subChannel;
		this.msg = msg;
	}

	public String getSubChannel() {
		return subChannel;
	}

	public String getMessage() {
		return msg;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
}