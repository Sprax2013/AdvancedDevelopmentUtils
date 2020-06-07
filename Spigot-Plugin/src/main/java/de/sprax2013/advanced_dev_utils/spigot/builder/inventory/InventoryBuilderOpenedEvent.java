package de.sprax2013.advanced_dev_utils.spigot.builder.inventory;

import org.bukkit.entity.HumanEntity;

public class InventoryBuilderOpenedEvent {
    private HumanEntity entity;

    public InventoryBuilderOpenedEvent(HumanEntity entity) {
        this.entity = entity;
    }

    public HumanEntity getEntity() {
        return entity;
    }
}