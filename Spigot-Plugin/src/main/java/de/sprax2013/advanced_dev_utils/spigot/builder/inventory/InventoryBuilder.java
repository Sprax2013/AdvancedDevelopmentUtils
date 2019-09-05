package de.sprax2013.advanced_dev_utils.spigot.builder.inventory;

import de.sprax2013.advanced_dev_utils.spigot.Main;
import de.sprax2013.advanced_dev_utils.spigot.builder.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.HashMap;

//import org.bukkit.potion.PotionData;

public class InventoryBuilder {
    Inventory inv;
    InventoryBuilderEventInterface eventInterface;

    private boolean built = false;

    private boolean autoDestroyOnNoViewers = true;

    private boolean itemsLocked = true;

    private Listener listener;

    HashMap<ItemStack, InventoryBuilderItemClickInterface> itemHandlers = new HashMap<>();

    boolean beingDisabled = false;
    private boolean destroyed = false;

    /**
     * Instantiates a new InventoryBuilder with a fixed size.
     *
     * @param size The size (should be: 9, 18, 27, 36, ...)
     */
    public InventoryBuilder(int size) {
        this.inv = Bukkit.createInventory(null, size);
    }

    /**
     * Instantiates a new InventoryBuilder with a specific type.
     *
     * @param type The InventoryType
     */
    public InventoryBuilder(InventoryType type) {
        this.inv = Bukkit.createInventory(null, type);
    }

    /**
     * Instantiates a new InventoryBuilder with a fixed size.
     *
     * @param size  The size (should be: 9, 18, 27, 36, ...)
     * @param title The title of the inventory
     */
    public InventoryBuilder(int size, String title) {
        this.inv = Bukkit.createInventory(null, size, title);
    }

    /**
     * Instantiates a new InventoryBuilder with a specific type.
     *
     * @param type  The InventoryType
     * @param title The title of the inventory
     */
    public InventoryBuilder(InventoryType type, String title) {
        this.inv = Bukkit.createInventory(null, type, title);
    }

    /**
     * Puts an item into the inventory at <i>slot</i>
     *
     * @param slot The slot
     * @param item The item
     *
     * @return The InventoryBuilder
     *
     * @throws IllegalStateException If the Inventory has already been built
     * @see #setItems(int, int, ItemStack, InventoryBuilderItemClickInterface)
     */
    public InventoryBuilder setItem(int slot, ItemStack item) {
        if (built) {
            throw new IllegalStateException(
                    "Inventory has already been built. You'll need to create a new InventoryBuilder-Object");
        }

        inv.setItem(slot, item);

        return this;
    }

    /**
     * Puts an item into the inventory at <i>slot</i>
     *
     * @param slot           The slot
     * @param item           The item
     * @param eventInterface Used to easily listen to events involving the item
     *
     * @return The InventoryBuilder
     *
     * @throws IllegalStateException If the Inventory has already been built
     * @see #setItem(int, ItemStack)
     */
    public InventoryBuilder setItem(int slot, ItemStack item, InventoryBuilderItemClickInterface eventInterface) {
        if (built) {
            throw new IllegalStateException(
                    "Inventory has already been built. You'll need to create a new InventoryBuilder-Object");
        }

        ItemStack iS = item.clone();

        itemHandlers.put(iS, eventInterface);

        inv.setItem(slot, iS);

        return this;
    }

    /**
     * Fills a range of Slots with a specific item
     *
     * @param startSlot The start Slot
     * @param endSlot   The last Slot
     * @param item      The item
     *
     * @return The InventoryBuilder
     *
     * @throws IllegalStateException If the Inventory has already been built
     * @see #setItems(int, int, ItemStack)
     */
    public InventoryBuilder setItems(int startSlot, int endSlot, ItemStack item) {
        return setItems(startSlot, endSlot, item, false);
    }

    /**
     * Fills a range of Slots with a specific item
     *
     * @param startSlot      The start Slot
     * @param endSlot        The last Slot
     * @param item           The item
     * @param randomizeColor Will set a random color on each item if possible
     *
     * @return The InventoryBuilder
     *
     * @throws IllegalStateException If the Inventory has already been built
     * @see #setItems(int, int, ItemStack)
     */
    public InventoryBuilder setItems(int startSlot, int endSlot, ItemStack item, boolean randomizeColor) {
        if (built) {
            throw new IllegalStateException(
                    "Inventory has already been built. You'll need to create a new InventoryBuilder-Object");
        }

        if (endSlot > inv.getSize() - 1) {
            throw new IllegalArgumentException(
                    "Integer 'endSlot' ist greater than possible (" + (inv.getSize() - 1) + ")");
        }

        if (endSlot < startSlot) {
            throw new IllegalArgumentException("(Integer) 'startSlot' < (Integer) 'endSlot'");
        }

        int curr = startSlot;
        while (curr <= endSlot) {
            if (randomizeColor) {
                setItem(curr, new ItemBuilder(item.clone()).setRandomDyeColor().build());
            } else {
                setItem(curr, item.clone());
            }

            curr++;
        }

        return this;
    }

    /**
     * Fills a range of Slots with a specific item
     *
     * @param startSlot The start Slot
     * @param endSlot   The last Slot
     * @param item      The item
     * @param handler   Used to easily listen to events involving the item
     *
     * @return The InventoryBuilder
     *
     * @throws IllegalStateException If the Inventory has already been built
     * @see #setItems(int, int, ItemStack)
     */
    public InventoryBuilder setItems(int startSlot, int endSlot, ItemStack item,
                                     InventoryBuilderItemClickInterface handler) {
        return setItems(startSlot, endSlot, item, handler, false);
    }

    /**
     * Fills a range of Slots with a specific item
     *
     * @param startSlot      The start Slot
     * @param endSlot        The last Slot
     * @param item           The item
     * @param handler        Used to easily listen to events involving the item
     * @param randomizeColor Will set a random color on each item if possible
     *
     * @return The InventoryBuilder
     *
     * @throws IllegalStateException If the Inventory has already been built
     * @see #setItems(int, int, ItemStack)
     */
    public InventoryBuilder setItems(int startSlot, int endSlot, ItemStack item,
                                     InventoryBuilderItemClickInterface handler, boolean randomizeColor) {
        if (built) {
            throw new IllegalStateException(
                    "Inventory has already been built. You'll need to create a new InventoryBuilder-Object");
        }

        if (endSlot > inv.getSize() - 1) {
            throw new IllegalArgumentException(
                    "Integer 'endSlot' ist greater than possible (" + (inv.getSize() - 1) + ")");
        }

        if (endSlot < startSlot) {
            throw new IllegalArgumentException("(Integer) 'startSlot' < (Integer) 'endSlot'");
        }

        int curr = startSlot;
        while (curr <= endSlot) {
            ItemStack iS = randomizeColor ? new ItemBuilder(item.clone()).setRandomDyeColor().build() : item.clone();

            itemHandlers.put(iS, handler);
            inv.setItem(curr, iS);
            curr++;
        }

        return this;
    }

    //

    /**
     * Fills a range of Slots with a specific item vertically
     *
     * @param startSlot The start Slot
     * @param endSlot   The last Slot
     * @param item      The item
     *
     * @return The InventoryBuilder
     *
     * @throws IllegalStateException If the Inventory has already been built
     * @see #setItems(int, int, ItemStack)
     */
    public InventoryBuilder setItemsVertical(int startSlot, int endSlot, ItemStack item) {
        return setItemsVertical(startSlot, endSlot, item, false);
    }

    /**
     * Fills a range of Slots vertically with a specific item
     *
     * @param startSlot      The start Slot
     * @param endSlot        The last Slot
     * @param item           The item
     * @param randomizeColor Will set a random color on each item if possible
     *
     * @return The InventoryBuilder
     *
     * @throws IllegalStateException If the Inventory has already been built
     * @see #setItems(int, int, ItemStack)
     */
    public InventoryBuilder setItemsVertical(int startSlot, int endSlot, ItemStack item, boolean randomizeColor) {
        if (built) {
            throw new IllegalStateException(
                    "Inventory has already been built. You'll need to create a new InventoryBuilder-Object");
        }

        if (endSlot > inv.getSize() - 1) {
            throw new IllegalArgumentException(
                    "Integer 'endSlot' ist greater than possible (" + (inv.getSize() - 1) + ")");
        }

        if (endSlot < startSlot) {
            throw new IllegalArgumentException("(Integer) 'startSlot' < (Integer) 'endSlot'");
        }

        int curr = startSlot;
        while (curr <= endSlot) {
            if (randomizeColor) {
                setItem(curr, new ItemBuilder(item.clone()).setRandomDyeColor().build());
            } else {
                setItem(curr, item.clone());
            }

            curr += 9;
        }

        return this;
    }

    /**
     * Fills a range of Slots vertically with a specific item
     *
     * @param startSlot The start Slot
     * @param endSlot   The last Slot
     * @param item      The item
     * @param handler   Used to easily listen to events involving the item
     *
     * @return The InventoryBuilder
     *
     * @throws IllegalStateException If the Inventory has already been built
     * @see #setItems(int, int, ItemStack)
     */
    public InventoryBuilder setItemsVertical(int startSlot, int endSlot, ItemStack item,
                                             InventoryBuilderItemClickInterface handler) {
        return setItemsVertical(startSlot, endSlot, item, handler, false);
    }

    /**
     * Fills a range of Slots vertically with a specific item
     *
     * @param startSlot      The start Slot
     * @param endSlot        The last Slot
     * @param item           The item
     * @param handler        Used to easily listen to events involving the item
     * @param randomizeColor Will set a random color on each item if possible
     *
     * @return The InventoryBuilder
     *
     * @throws IllegalStateException If the Inventory has already been built
     * @see #setItems(int, int, ItemStack)
     */
    public InventoryBuilder setItemsVertical(int startSlot, int endSlot, ItemStack item,
                                             InventoryBuilderItemClickInterface handler, boolean randomizeColor) {
        if (built) {
            throw new IllegalStateException(
                    "Inventory has already been built. You'll need to create a new InventoryBuilder-Object");
        }

        if (endSlot > inv.getSize() - 1) {
            throw new IllegalArgumentException(
                    "Integer 'endSlot' ist greater than possible (" + (inv.getSize() - 1) + ")");
        }

        if (endSlot < startSlot) {
            throw new IllegalArgumentException("(Integer) 'startSlot' < (Integer) 'endSlot'");
        }

        int curr = startSlot;
        while (curr <= endSlot) {
            ItemStack iS = randomizeColor ? new ItemBuilder(item.clone()).setRandomDyeColor().build() : item.clone();

            itemHandlers.put(iS, handler);
            inv.setItem(curr, iS);

            curr += 9;
        }

        return this;
    }

    //

    /**
     * Sets the EventInterface. Use this to easily listen to InventoryEvents
     *
     * @param eventInterface The EventInterface
     *
     * @return The InventoryBuilder
     *
     * @throws IllegalStateException If the Inventory has already been built
     */
    public InventoryBuilder setEventHandler(InventoryBuilderEventInterface eventInterface) {
        if (built) {
            throw new IllegalStateException(
                    "Inventory has already been built. You'll need to create a new InventoryBuilder-Object");
        }

        this.eventInterface = eventInterface;

        return this;
    }

    /**
     * True: The Items can't be picked up. They are locked to it's slot<br>
     * Default: True
     *
     * @param itemsLocked should the items be locked to it's slot
     */
    public InventoryBuilder setItemsLocked(boolean itemsLocked) {
        this.itemsLocked = itemsLocked;

        return this;
    }

    /**
     * Default-Value is <code>true</code>.
     *
     * @return true, if items are locked to it's slot.
     */
    public boolean areItemsLocked() {
        return itemsLocked;
    }

    /**
     * True: The Inventory will be destroyed with it's listeners. The Inventory can
     * not be viewed anymore<br>
     * Default: True
     *
     * @param autoDestroyOnNoViewers Should the Inventory be destroyed?
     */
    public InventoryBuilder setAutoDestroyOnNoViewers(boolean autoDestroyOnNoViewers) {
        this.autoDestroyOnNoViewers = autoDestroyOnNoViewers;

        return this;
    }

    /**
     * Default-Value is <code>true</code>.
     *
     * @return true, if the InventoryBuilder will be destroyed, when no one is
     * viewing the inventory anymore
     */
    public boolean autoDestroyOnNoViewers() {
        return autoDestroyOnNoViewers;
    }

    /**
     * Returns an Inventory with all modifications.<br>
     * Once built it can not be modified using the InventoryBuilder-Object.<br>
     * <b>Modifying it anyway, could cause bugs. You are on your own in that
     * case!</b>
     *
     * @return The inventory
     *
     * @throws IllegalStateException If the Inventory has already been built
     */
    public Inventory build() {
        if (built) {
            throw new IllegalStateException(
                    "Inventory has already been built. You'll need to create a new InventoryBuilder-Object");
        }

        built = true;

        if (eventInterface != null) {
            listener = new Listener() {
                @EventHandler(priority = EventPriority.HIGHEST)
                private void onItemClick(InventoryClickEvent e) {
                    if (e.getClickedInventory() != null && e.getClickedInventory().equals(inv)
                            && e.getWhoClicked().getOpenInventory().getTopInventory().equals(inv)) {
                        try {
                            eventInterface.onClick(new InventoryBuilderClickEvent(e.getWhoClicked(), e.getRawSlot(),
                                    e.getSlot(), e.getCurrentItem(), e.getCursor(), e.isCancelled()));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                        if (e.getSlotType() != SlotType.OUTSIDE && e.getCurrentItem() != null
                                && itemHandlers.containsKey(e.getCurrentItem())) {
                            try {
                                itemHandlers.get(e.getCurrentItem()).onClickItem(new InventoryBuilderItemClickEvent(
                                        e.getWhoClicked(), e.getSlot(), e.getCurrentItem(), inv, e.getClick()));
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }

                        if (areItemsLocked()) {
                            if (e.getClickedInventory().equals(inv)) {
                                e.setResult(Result.DENY);
                            } else if (e.getView().getTopInventory().equals(inv) && e.isShiftClick()) {
                                e.setResult(Result.DENY);
                            }
                        }
                    }
                }

                @EventHandler(priority = EventPriority.HIGHEST)
                private void onCloseInv(InventoryCloseEvent e) {
                    if (e.getInventory().equals(inv)) {
                        InventoryBuilderCloseEvent event = new InventoryBuilderCloseEvent(e.getPlayer(), true, false);

                        if (eventInterface != null) {
                            try {
                                eventInterface.onClose(event);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }

                        if (!event.isCanceled() && autoDestroyOnNoViewers() && !isDestroyed() && (inv.getViewers()
                                .isEmpty()
                                || (inv.getViewers().size() == 1 && inv.getViewers().contains(e.getPlayer())))) {
                            destroy();
                        } else if (!isDestroyed() && event.isCanceled()) {
                            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
                                @Override
                                public void run() {
                                    event.getEntity().openInventory(inv);
                                }
                            }, 2);
                        }
                    }
                }

                @EventHandler(priority = EventPriority.MONITOR)
                private void onOpenInv(InventoryOpenEvent e) {
                    if (!e.isCancelled() && e.getInventory().equals(inv) && eventInterface != null) {
                        try {
                            eventInterface.onOpen(new InventoryBuilderOpenedEvent(e.getPlayer()));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }

                @EventHandler
                private void onDisablePlugin(PluginDisableEvent e) {
                    if (e.getPlugin() == Main.getInstance()) {
                        beingDisabled = true;
                        destroy();
                    }
                }
            };
        } else if (itemsLocked) {
            listener = new Listener() {
                @EventHandler(priority = EventPriority.HIGHEST)
                private void onItemClick(InventoryClickEvent e) {
                    if (e.getClickedInventory() != null && e.getClickedInventory().equals(inv)
                            && e.getWhoClicked().getOpenInventory().getTopInventory().equals(inv)) {
                        e.setResult(Result.DENY);

                        if (e.getSlotType() != SlotType.OUTSIDE && e.getCurrentItem() != null
                                && itemHandlers.containsKey(e.getCurrentItem())) {
                            try {
                                itemHandlers.get(e.getCurrentItem()).onClickItem(new InventoryBuilderItemClickEvent(
                                        e.getWhoClicked(), e.getSlot(), e.getCurrentItem(), inv, e.getClick()));
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    } else if (e.getView().getTopInventory().equals(inv) && e.isShiftClick()) {
                        e.setResult(Result.DENY);
                    }
                }

                @EventHandler(priority = EventPriority.HIGHEST)
                private void onCloseInv(InventoryCloseEvent e) {
                    if (e.getInventory().equals(inv) && autoDestroyOnNoViewers() && !isDestroyed()
                            && (inv.getViewers().isEmpty()
                            || (inv.getViewers().size() == 1 && inv.getViewers().contains(e.getPlayer())))) {
                        destroy();
                    }
                }

                @EventHandler(priority = EventPriority.MONITOR)
                private void onOpenInv(InventoryOpenEvent e) {
                    if (!e.isCancelled() && e.getInventory().equals(inv) && eventInterface != null) {
                        try {
                            eventInterface.onOpen(new InventoryBuilderOpenedEvent(e.getPlayer()));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }

                @EventHandler
                private void onDisablePlugin(PluginDisableEvent e) {
                    if (e.getPlugin() == Main.getInstance()) {
                        beingDisabled = true;
                        destroy();
                    }
                }
            };
        } else if (!itemHandlers.isEmpty()) {
            listener = new Listener() {
                @EventHandler(priority = EventPriority.HIGHEST)
                private void onItemClick(InventoryClickEvent e) {
                    if (e.getClickedInventory() != null && e.getClickedInventory().equals(inv)
                            && e.getWhoClicked().getOpenInventory().getTopInventory().equals(inv)
                            && e.getCurrentItem() != null && itemHandlers.containsKey(e.getCurrentItem())) {
                        try {
                            itemHandlers.get(e.getCurrentItem()).onClickItem(new InventoryBuilderItemClickEvent(
                                    e.getWhoClicked(), e.getSlot(), e.getCurrentItem(), inv, e.getClick()));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }

                @EventHandler(priority = EventPriority.HIGHEST)
                private void onCloseInv(InventoryCloseEvent e) {
                    if (e.getInventory().equals(inv) && autoDestroyOnNoViewers() && !isDestroyed()
                            && (inv.getViewers().isEmpty()
                            || (inv.getViewers().size() == 1 && inv.getViewers().contains(e.getPlayer())))) {
                        destroy();
                    }
                }

                @EventHandler(priority = EventPriority.MONITOR)
                private void onOpenInv(InventoryOpenEvent e) {
                    if (!e.isCancelled() && e.getInventory().equals(inv) && eventInterface != null) {
                        try {
                            eventInterface.onOpen(new InventoryBuilderOpenedEvent(e.getPlayer()));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }

                @EventHandler
                private void onDisablePlugin(PluginDisableEvent e) {
                    if (e.getPlugin() == Main.getInstance()) {
                        beingDisabled = true;
                        destroy();
                    }
                }
            };
        } else if (autoDestroyOnNoViewers) {
            listener = new Listener() {
                @EventHandler(priority = EventPriority.HIGHEST)
                private void onCloseInv(InventoryCloseEvent e) {
                    if (e.getInventory().equals(inv) && !isDestroyed() && (inv.getViewers().isEmpty()
                            || (inv.getViewers().size() == 1 && inv.getViewers().contains(e.getPlayer())))) {
                        destroy();
                    }
                }

                @EventHandler(priority = EventPriority.MONITOR)
                private void onOpenInv(InventoryOpenEvent e) {
                    if (!e.isCancelled() && e.getInventory().equals(inv) && eventInterface != null) {
                        try {
                            eventInterface.onOpen(new InventoryBuilderOpenedEvent(e.getPlayer()));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }

                @EventHandler
                private void onDisablePlugin(PluginDisableEvent e) {
                    if (e.getPlugin() == Main.getInstance()) {
                        beingDisabled = true;
                        destroy();
                    }
                }
            };
        }

        if (listener != null) {
            Bukkit.getPluginManager().registerEvents(listener, Main.getInstance());
        }

        return inv;
    }

    /**
     * <ul>
     * <li>Closes the Inventory for all Viewers</li>
     * <li>Unregister the Listener. Events, Handlers, etc. won't work</li>
     * </ul>
     * <p>
     * The Inventory-Object etc. will only be <i>destroyed/deleted</i> by the
     * <a href=
     * "https://en.wikipedia.org/wiki/Garbage_collection_(computer_science)">GC</a>
     */
    public void destroy() {
        destroyed = true;

        Runnable run = new Runnable() {
            @Override
            public void run() {
                while (!inv.getViewers().isEmpty()) {
                    // try {
                    HumanEntity viewer = inv.getViewers().get(0);

                    viewer.closeInventory();

                    if (eventInterface != null) {
                        try {
                            eventInterface.onClose(new InventoryBuilderCloseEvent(viewer, false, false));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    // } catch (@SuppressWarnings("unused") IndexOutOfBoundsException ignore) {
                    // }
                }
            }
        };

        if (beingDisabled) {
            // Can't use schedulers when plugin is getting disabled :(
            run.run();
        } else {
            // Prevents resetting the mouse-courser to the center when inv gets destroyed
            // because the last viewer opened a new inventory
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), run);
        }

        if (listener != null) {
            HandlerList.unregisterAll(listener);
            listener = null;
        }
    }

    /**
     * Checks if the Inventory(-Builder)-Object has ben destroyed.<br>
     * A destroyed object can not be used anymore and should be cleaned by the
     * 'Garbage Collector'.
     *
     * @return true, if is destroyed
     */
    public boolean isDestroyed() {
        return destroyed;
    }

    @SuppressWarnings("deprecation")
    public String toJavaCode() {
        InventoryBuilder defValues = new InventoryBuilder(9);

        String javaCode = "new InventoryBuilder(" + inv.getSize() + ",\"" + inv.getTitle() + "\")";

        if (defValues.areItemsLocked() != areItemsLocked()) {
            javaCode += ".setItemsLocked(" + Boolean.toString(areItemsLocked()) + ")";
        }

        if (defValues.autoDestroyOnNoViewers() != autoDestroyOnNoViewers()) {
            javaCode += ".setAutoDestroyOnNoViewers(" + Boolean.toString(autoDestroyOnNoViewers()) + ")";
        }

        int slot = 0;
        while (slot < inv.getSize()) {
            ItemStack is = inv.getItem(slot);

            if (is != null) {
                String itemB = "new ItemBuilder(Material." + is.getType().name() + ")";

                if (is.getDurability() > 0) {
                    try {
                        if (is.getType() == Material.valueOf("INK_SACK")) {
                            itemB += ".setColor(DyeColor."
                                    + DyeColor.getByDyeData(Short.valueOf(is.getDurability()).byteValue()).name() + ")";
                        } else if (is.getType().equals(Material.valueOf("WOOL"))
                                || is.getType().equals(Material.valueOf("STAINED_GLASS"))
                                || is.getType().equals(Material.valueOf("STAINED_GLASS_PANE"))
                                || is.getType().equals(Material.valueOf("STAINED_CLAY"))
                                || is.getType().equals(Material.valueOf("BED"))) {
                            itemB += ".setColor(DyeColor."
                                    + DyeColor.getByWoolData(Short.valueOf(is.getDurability()).byteValue()).name()
                                    + ")";
                        } else {
                            itemB += ".setSubID(" + is.getDurability() + ")";
                        }
                    } catch (Throwable th) {
                        System.err.println("Your Spigot-Version is not supported: " + th.getMessage());
                    }
                }

                if (is.hasItemMeta()) {
                    if (is.getItemMeta().hasDisplayName()) {
                        itemB += ".setDisplayName(\"" + is.getItemMeta().getDisplayName() + "\")";
                    }

                    if (is.getItemMeta().hasLore()) {
                        String lores = null;

                        for (String lore : is.getItemMeta().getLore()) {
                            if (lores == null) {
                                lores = "\"" + lore + "\"";
                            } else {
                                lores += ",\"" + lore + "\"";
                            }
                        }

                        itemB += ".setLore(Arrays.asList(" + lores + "))";
                    }

                    if (!is.getItemMeta().getItemFlags().isEmpty()) {
                        for (ItemFlag flag : is.getItemMeta().getItemFlags()) {

                            itemB += ".addItemFlag(ItemFlag." + flag.name() + ")";
                        }
                    }

//                    if (is.getItemMeta() instanceof SkullMeta) {
//                        SkullMeta sMeta = ((SkullMeta) is.getItemMeta());
//
//                        String skinURL;
//
//                        if (sMeta.getOwningPlayer() != null) {
//                            itemB += ".setSkullOwner(\"" + sMeta.getOwningPlayer().getName()
//                                    + "\")	/* The name has been taken from an OfflinePlayer-Object (UUID: "
//                                    + sMeta.getOwningPlayer().getUniqueId().toString() + ") */";
//
//                            itemB.replace(".setSubID(3)", "");
//                        } else if (sMeta.getOwner() != null) {
//                            itemB += ".setSkullOwner(\"" + sMeta.getOwner() + "\")";
//
//                            itemB.replace(".setSubID(3)", "");
//                        } else if ((skinURL = ItemBuilder.getSkullSkin(is)) != null) {
//                            itemB += ".setSkullSkin(\"" + skinURL + "\")";
//
//                            itemB.replace(".setSubID(3)", "");
//                        }
//                    } else if (is.getItemMeta() instanceof PotionMeta) {
//                        PotionData data = ((PotionMeta) is.getItemMeta()).getBasePotionData();
//
//                        itemB += ".setBasePotionData(new PotionData(PotionType." + data.getType().name() + ","
//                                + Boolean.valueOf(data.isExtended()).toString() + ","
//                                + Boolean.valueOf(data.isUpgraded()).toString() + "))";
//                    } else
                    	if (is.getItemMeta() instanceof LeatherArmorMeta) {
                        Color color = ((LeatherArmorMeta) is.getItemMeta()).getColor();

                        itemB += ".setColor(Color.fromRGB(" + color.getRed() + "," + color.getGreen() + ","
                                + color.getBlue() + "))";
                    }
                }

                if (!is.getEnchantments().isEmpty()) {
                    for (Enchantment ench : is.getEnchantments().keySet()) {
                        itemB += ".addEnchantment(Enchantment." + ench.getName() + "," + is.getEnchantmentLevel(ench)
                                + ")";
                    }
                }

                javaCode += ".setItem(" + slot + "," + itemB + ".build())";
            }

            slot++;
        }

        defValues.destroy();

        return javaCode + ";";
    }
}