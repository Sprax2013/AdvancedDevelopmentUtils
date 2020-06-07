package de.sprax2013.advanced_dev_utils.spigot.builder.inventory;

import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryBuilderItemClickEvent {
    private HumanEntity entity;

    private int slot;
    private ItemStack item;

    private Inventory inv;

    private ClickType clickType;

    private boolean canceled;

    public InventoryBuilderItemClickEvent(HumanEntity entity, int slot, ItemStack item, Inventory inv,
                                          ClickType clickType) {
        this.entity = entity;

        this.slot = slot;
        this.item = item;

        this.inv = inv;

        this.clickType = clickType;
    }

    public HumanEntity getEntity() {
        return entity;
    }

    public int getSlot() {
        return slot;
    }

    public ItemStack getClickedItem() {
        return item;
    }

    public Inventory getInventory() {
        return inv;
    }

    public ClickType getClickType() {
        return clickType;
    }

    public boolean isCanceled() {
        return canceled;
    }
}