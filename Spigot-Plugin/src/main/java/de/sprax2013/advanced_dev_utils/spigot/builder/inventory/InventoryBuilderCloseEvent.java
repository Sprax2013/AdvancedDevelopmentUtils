package de.sprax2013.advanced_dev_utils.spigot.builder.inventory;

import org.bukkit.entity.HumanEntity;

public class InventoryBuilderCloseEvent {
    private HumanEntity entity;

    private boolean cancelAble;
    private boolean canceled;

    public InventoryBuilderCloseEvent(HumanEntity entity, boolean cancelAble, boolean canceled) {
        this.entity = entity;

        this.cancelAble = cancelAble;

        this.canceled = canceled;
    }

    public HumanEntity getEntity() {
        return entity;
    }

    public boolean isCancelAble() {
        return cancelAble;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean isCanceled) {
        canceled = isCanceled;
    }
}