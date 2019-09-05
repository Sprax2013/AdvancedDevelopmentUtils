package de.sprax2013.advanced_dev_utils.spigot.chat.locale;

import com.comphenix.protocol.wrappers.EnumWrappers.Hand;

public enum ClientMainHand {
	LEFT(Hand.OFF_HAND), RIGHT(Hand.MAIN_HAND);

	private final Hand plHand;

	private ClientMainHand(Hand plHand) {
		this.plHand = plHand;
	}

	public static ClientMainHand getDefault() {
		return ClientMainHand.RIGHT;
	}

	// TODO Testen, ob das wirklich funktioniert auf 1.9+
	public static ClientMainHand getByProtocolLibHand(Hand plHand) {
		for (ClientMainHand hand : values()) {
			if (hand.plHand == plHand) {
				return hand;

			}
		}

		return null;
	}
}