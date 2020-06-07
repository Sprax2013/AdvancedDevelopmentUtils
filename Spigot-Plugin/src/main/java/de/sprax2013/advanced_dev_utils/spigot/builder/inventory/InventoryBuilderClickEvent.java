package de.sprax2013.advanced_dev_utils.spigot.builder.inventory;

import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;

public class InventoryBuilderClickEvent {
    private final HumanEntity entity;

    private final int rawSlot, slot;

    private final ItemStack slotItem, cursorItem;

    private final boolean canceled;

    public InventoryBuilderClickEvent(HumanEntity entity, int rawSlot, int slot, ItemStack slotItem,
                                      ItemStack cursorItem, boolean canceled) {
        this.entity = entity;

        this.rawSlot = rawSlot;
        this.slot = slot;

        this.slotItem = slotItem;
        this.cursorItem = cursorItem;

        this.canceled = canceled;
    }

    public HumanEntity getEntity() {
        return entity;
    }

    public int getRawSlot() {
        return rawSlot;
    }

    public int getSlot() {
        return slot;
    }

    public ItemStack getSlotItem() {
        return slotItem;
    }

    public ItemStack getCursorItem() {
        return cursorItem;
    }

    public boolean isCanceled() {
        return canceled;
    }
}