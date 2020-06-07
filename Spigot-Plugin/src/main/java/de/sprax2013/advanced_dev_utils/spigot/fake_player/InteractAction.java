package de.sprax2013.advanced_dev_utils.spigot.fake_player;

import com.comphenix.protocol.wrappers.EnumWrappers.EntityUseAction;

public enum InteractAction {
    LEFT_CLICK(EntityUseAction.ATTACK), RIGHT_CLICK(EntityUseAction.INTERACT_AT);

    private final EntityUseAction action;

    private InteractAction(EntityUseAction action) {
        this.action = action;
    }

    public static InteractAction getByProtocolLib(EntityUseAction pLAction) {
        for (InteractAction action : values()) {
            if (action.action.equals(pLAction)) {
                return action;
            }
        }

        return null;
    }
}