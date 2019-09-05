package de.sprax2013.advanced_dev_utils.spigot.builder.inventory;

public interface InventoryBuilderEventInterface {
	void onOpen(InventoryBuilderOpenedEvent event);
	
	void onClick(InventoryBuilderClickEvent event);

	void onClose(InventoryBuilderCloseEvent event);
}