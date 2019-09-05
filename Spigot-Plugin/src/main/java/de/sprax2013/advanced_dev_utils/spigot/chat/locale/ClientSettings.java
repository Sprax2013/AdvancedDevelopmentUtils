package de.sprax2013.advanced_dev_utils.spigot.chat.locale;

import java.util.Locale;

import org.apache.commons.lang3.LocaleUtils;

import de.sprax2013.advanced_dev_utils.spigot.Main;
import net.md_5.bungee.api.ChatColor;

public class ClientSettings {
	private String localeString;
	private Locale locale;

	private int viewDistance;

	private ClientChatMode chatMode;
	private boolean chatColors;

	private int displayedSkinParts;

	private ClientMainHand mainHand = ClientMainHand.getDefault();

	public ClientSettings(String localeString, int viewDistance, ClientChatMode chatMode, boolean chatColors,
			int displayedSkinParts) {
		this.localeString = localeString;

		this.viewDistance = viewDistance;

		this.chatMode = chatMode;
		this.chatColors = chatColors;

		this.displayedSkinParts = displayedSkinParts;

		updateLocale();
	}

	public ClientSettings(String localeString, int viewDistance, ClientChatMode chatMode, boolean chatColors,
			int displayedSkinParts, ClientMainHand mainHand) {
		this.localeString = localeString;

		this.viewDistance = viewDistance;

		this.chatMode = chatMode;
		this.chatColors = chatColors;

		this.displayedSkinParts = displayedSkinParts;

		this.mainHand = mainHand;

		updateLocale();
	}

	public final String getLocaleString() {
		return this.localeString;
	}

	public final Locale getLocale() {
		return locale;
	}

	public final int getViewDistance() {
		return this.viewDistance;
	}

	public final ClientChatMode getChatMode() {
		return this.chatMode;
	}

	public final boolean getChatColors() {
		return this.chatColors;
	}

	public final int getDisplayedSkinParts() {
		return this.displayedSkinParts;
	}

	public final ClientMainHand getMainHand() {
		return this.mainHand;
	}

	public void update(String localeString, int viewDistance, ClientChatMode chatMode, boolean chatColors,
			int displayedSkinParts) {
		this.localeString = localeString;

		this.viewDistance = viewDistance;

		this.chatMode = chatMode;
		this.chatColors = chatColors;

		this.displayedSkinParts = displayedSkinParts;

		this.mainHand = ClientMainHand.getDefault();

		updateLocale();
	}

	public void update(String localeString, int viewDistance, ClientChatMode chatMode, boolean chatColors,
			int displayedSkinParts, ClientMainHand mainHand) {
		this.localeString = localeString;

		this.viewDistance = viewDistance;

		this.chatMode = chatMode;
		this.chatColors = chatColors;

		this.displayedSkinParts = displayedSkinParts;

		this.mainHand = mainHand;

		updateLocale();
	}

	private void updateLocale() {
		locale = getLocale(getLocaleString());
	}

	public static Locale getLocale(String s) {
		Locale lang = null;

		String result = "";
		int i = 0;
		for (String arg : s.split("_")) {
			if (i == 0) {
				result += arg.toLowerCase();
			} else if (i == 1) {
				result += "_" + arg.toUpperCase();
			} else {
				result += "_" + arg;
			}

			i++;
		}

		try {
			lang = LocaleUtils.toLocale(result);
		} catch (@SuppressWarnings("unused") IllegalArgumentException invalidLocaleString) {
			System.err.println(ChatColor.stripColor(Main.prefix) + "ClientSettings found invalid Locale-String: " + s);
		}

		return lang;
	}
}