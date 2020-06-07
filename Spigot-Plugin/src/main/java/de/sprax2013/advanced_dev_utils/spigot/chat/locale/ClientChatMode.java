package de.sprax2013.advanced_dev_utils.spigot.chat.locale;

import com.comphenix.protocol.wrappers.EnumWrappers.ChatVisibility;

public enum ClientChatMode {
    FULL(ChatVisibility.FULL), SYSTEM(ChatVisibility.SYSTEM), NONE(ChatVisibility.HIDDEN);

    private final ChatVisibility plChatVis;

    private ClientChatMode(ChatVisibility plChatVis) {
        this.plChatVis = plChatVis;
    }

    public static ClientChatMode getByProtocolLibHand(ChatVisibility plChatVis) {
        for (ClientChatMode mode : values()) {
            if (mode.plChatVis == plChatVis) {
                return mode;
            }
        }

        return null;
    }
}