package de.sprax2013.advanced_dev_utils.spigot.builder;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import de.sprax2013.advanced_dev_utils.math_and_numbers.RandomNumberUtils;
import de.sprax2013.advanced_dev_utils.spigot.utils.GameProfileUtils;
import de.sprax2013.advanced_dev_utils.spigot.utils.NMSClassUtils;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * With this class you can create an ItemStack with custom MetaData within one
 * line!<br>
 * <br>
 * Example: <i>new
 * ItemBuilder(Material.SKULL_ITEM).setSkullOwner("Sprax2013").build();</i><br>
 * <br>
 * Will create a SKULL_ITEM setting the SkullOwner to <i>Sprax2013</i> resulting
 * in automatically get it's subID set to <i>3</i>.
 */
public class ItemBuilder {
    private ItemStack item;
    private ItemMeta meta;

    /**
     * Instantiates a new ItemBuilder.
     *
     * @param type the material for the item
     */
    public ItemBuilder(Material type) {
        if (type == null) {
            throw new IllegalArgumentException("Can not create an ItemBuilder-Instance with 'type' being null");
        }

        item = new ItemStack(type);
    }

    /**
     * Instantiates a new ItemBuilder.
     *
     * @param type   the material for the item
     * @param amount the amount of the resulting ItemStack
     */
    public ItemBuilder(Material type, int amount) {
        if (type == null) {
            throw new IllegalArgumentException("Can not create an ItemBuilder-Instance with 'type' being null");
        }

        item = new ItemStack(type, amount);
    }

    /**
     * Instantiates a new ItemBuilder.
     *
     * @param type   the material for the item
     * @param amount the amount of the resulting ItemStack
     * @param subID  the subID for the resulting ItemStack
     */
    public ItemBuilder(Material type, int amount, Integer subID) {
        if (type == null) {
            throw new IllegalArgumentException("Can not create an ItemBuilder-Instance with 'type' being null");
        }

        item = new ItemStack(type, amount, subID.shortValue());
    }

    /**
     * Instantiates a new ItemBuilder.
     *
     * @param item an existing ItemStack that should be edited
     */
    public ItemBuilder(ItemStack item) {
        if (item == null) {
            throw new IllegalArgumentException("Can not create an ItemBuilder-Instance with 'item' being null");
        }

        this.item = item.clone();
    }

    /**
     * Sets the material of the item.<br>
     * <b>This could remove some changes that depend on the material (for example
     * the set color)</b>
     *
     * @param mat The Material
     *
     * @return the instantiated ItemBuilder
     */
    public ItemBuilder setMaterial(Material mat) {
        item.setType(mat);

        return this;
    }

    /**
     * Sets the display name of the item.
     *
     * @param displayname the display name
     *
     * @return the instantiated ItemBuilder
     */
    public ItemBuilder setDisplayName(String displayname) {
        getItemMeta().setDisplayName(displayname);

        return this;
    }

    /**
     * Sets the lore.
     *
     * @param lore the lore
     *
     * @return the instantiated ItemBuilder
     */
    public ItemBuilder setLore(String lore) {
        if (lore != null) {
            getItemMeta().setLore(Arrays.asList("§r" + lore));
        } else {
            getItemMeta().setLore(null);
        }

        return this;
    }

    /**
     * Sets the lore.
     *
     * @param lore the lore
     *
     * @return the instantiated ItemBuilder
     */
    public ItemBuilder setLore(String lore, String... lores) {
        if (lore != null) {
            List<String> list = new ArrayList<>();
            list.add("§r" + lore);

            for (String s : lores) {
                list.add("§r" + s);
            }

            getItemMeta().setLore(list);
        } else {
            getItemMeta().setLore(null);
        }

        return this;
    }

    /**
     * Sets the lore of an Item.
     *
     * @param lore the lore
     *
     * @return the instantiated ItemBuilder
     */
    public ItemBuilder setLore(List<String> lore) {
        getItemMeta().setLore(lore);

        return this;
    }

    /**
     * Will add a String to an existing lore or add a new lore.
     *
     * @param lore the lore of the item
     *
     * @return the instantiated ItemBuilder
     */
    public ItemBuilder addLore(String lore) {
        if (lore != null) {
            if (getItemMeta().hasLore()) {
                List<String> lores = getItemMeta().getLore();
                lores.add("§r" + lore);

                getItemMeta().setLore(lores);
            } else {
                setLore(lore);
            }
        }

        return this;
    }

    /**
     * Clears the lore of the item.
     *
     * @return the instantiated ItemBuilder
     */
    public ItemBuilder clearLore() {
        getItemMeta().setLore(null);

        return this;
    }

    /**
     * Sets the amount of the item.
     *
     * @param amount the desired amount
     *
     * @return the instantiated ItemBuilder
     */
    public ItemBuilder setAmount(int amount) {
        item.setAmount(amount);

        return this;
    }

    /**
     * Sets the subID of the item. (351:<b>15</b> | itemID:<b>subID</b>)
     *
     * @param subID the subID
     *
     * @return the instantiated ItemBuilder
     */
    public ItemBuilder setSubID(Integer subID) {
        return setDamage(subID);
    }

    /**
     * Sets the subID of the item. (351:<b>15</b> | itemID:<b>subID</b>)
     *
     * @param subID the subID
     *
     * @return the instantiated ItemBuilder
     */
    public ItemBuilder setSubID(Short subID) {
        return setDamage(subID);
    }

    /**
     * Sets the remaining durability of the item. (type.getMaxDurability() -
     * durability)<br>
     * <b>Not to be confused with {@link #setDamage(Integer) setDamage} !</b>
     *
     * @param durability the durability
     *
     * @return the instantiated ItemBuilder
     */
    public ItemBuilder setDurabilityLeft(Integer durability) {
        item.setDurability((short) (item.getType().getMaxDurability() - durability.shortValue()));

        return this;
    }

    /**
     * Sets the damage the item has<br>
     * <b>Not to be confused with {@link #setDurabilityLeft(Integer)
     * setDurabilityLeft} !</b>
     *
     * @param damage the damage of the item
     *
     * @return the instantiated ItemBuilder
     */
    public ItemBuilder setDamage(Integer damage) {
        setDamage(damage.shortValue());

        return this;
    }

    /**
     * Sets the damage the item has<br>
     * <b>Not to be confused with {@link #setDurabilityLeft(Integer)
     * setDurabilityLeft} !</b>
     *
     * @param damage the damage of the item
     *
     * @return the instantiated ItemBuilder
     */
    public ItemBuilder setDamage(Short damage) {
        item.setDurability(damage);

        return this;
    }

    /**
     * Adds an enchantment.
     *
     * @param type  Enchantment type
     * @param level level of the enchantment
     *
     * @return the instantiated ItemBuilder
     */
    public ItemBuilder addEnchantment(Enchantment type, int level) {
        getItemMeta().addEnchant(type, level, true);

        return this;
    }

    /**
     * Adds an enchantment. Will remove it (or won't set one) if
     * <i>removeEnchantmentWhenLevelSmaler1</i> = true
     *
     * @param type                               Enchantment type
     * @param level                              level of the enchantment
     * @param removeEnchantmentWhenLevelSmaller1 if true, level < 1 will result in
     *                                           removing or not setting an
     *                                           enchantment
     *
     * @return the instantiated ItemBuilder
     */
    public ItemBuilder addEnchantment(Enchantment type, int level, boolean removeEnchantmentWhenLevelSmaller1) {
        if (removeEnchantmentWhenLevelSmaller1 && level < 1) {
            getItemMeta().removeEnchant(type);
        } else {
            getItemMeta().addEnchant(type, level, true);
        }

        return this;
    }

    /**
     * Adds multiple enchantments.
     *
     * @param enchantments Enchantment type and level
     *
     * @return the instantiated ItemBuilder
     */
    public ItemBuilder addEnchantments(Map<Enchantment, Integer> enchantments) {
        for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
            getItemMeta().addEnchant(entry.getKey(), entry.getValue(), true);
        }

        return this;
    }

    /**
     * Adds the given ItemFlags.
     *
     * @param flags the flags
     *
     * @return the instantiated ItemBuilder
     */
    public ItemBuilder addItemFlag(ItemFlag... flags) {
        getItemMeta().addItemFlags(flags);

        return this;
    }

    /**
     * Removes the given ItemFlags.
     *
     * @param flags the flags
     *
     * @return the instantiated ItemBuilder
     */
    public ItemBuilder removeItemFlag(ItemFlag... flags) {
        getItemMeta().removeItemFlags(flags);

        return this;
    }

    /**
     * Sets the SkullOwner.<br>
     * Will automatically set the items subID to <i>3</i>
     *
     * @param owner the name of the player
     *
     * @return the instantiated ItemBuilder
     */
    @SuppressWarnings("deprecation")
    public ItemBuilder setSkullOwner(String owner) {
        if (getItemMeta() instanceof SkullMeta) {
            ((SkullMeta) getItemMeta()).setOwner(owner == null ? "" : owner);

            setSubID(3);
        }

        return this;
    }

    /**
     * Sets the SkullOwner.<br>
     * Will automatically set the items subID to <i>3</i>
     *
     * @param owner the player
     *
     * @return the instantiated ItemBuilder
     */
    @SuppressWarnings("deprecation")
    public ItemBuilder setSkullOwner(OfflinePlayer owner) {
        if (getItemMeta() instanceof SkullMeta) {
            try {
                meta.getClass().getDeclaredMethod("setOwningPlayer", OfflinePlayer.class).invoke(meta, owner);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ignore) {
                try {
                    meta.getClass().getDeclaredMethod("setOwner", String.class).invoke(meta, owner.getName());
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
                    ex.printStackTrace();
                }
            }

            setSubID(3);
        }

        return this;
    }

    public ItemBuilder setSkullSkin(String skinURL) {
        if (getItemMeta() instanceof SkullMeta) {
            try {
                Field profileField = NMSClassUtils.getNMSClass("org.bukkit.craftbukkit.?.inventory.CraftMetaSkull")
                        .getDeclaredField("profile");
                profileField.setAccessible(true);

                GameProfile profile = null;

                if (skinURL != null) {
                    profile = new GameProfile(UUID.randomUUID(), null);

                    profile.getProperties().put("textures", new Property("textures", Base64.getEncoder()
                            .encodeToString(new String("{textures:{SKIN:{url:\"" + skinURL + "\"}}}").getBytes())));
                }

                profileField.set(meta, profile);

                if (skinURL != null) {
                    setSubID(3);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return this;
    }

    public static String getSkullSkin(ItemStack item) {
        String skinURL = null;

        if (item != null && item.hasItemMeta()) {
            if (item.getItemMeta() instanceof SkullMeta) {
                try {
                    Field profileField = NMSClassUtils.getNMSClass("org.bukkit.craftbukkit.?.inventory.CraftMetaSkull")
                            .getDeclaredField("profile");
                    profileField.setAccessible(true);

                    Object profileObj = profileField.get(item.getItemMeta());

                    if (profileObj != null) {
                        skinURL = GameProfileUtils.getSkinURL((GameProfile) profileObj);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

        return skinURL;
    }

    /**
     * Sets the color using org.bukkit.Color<br>
     * Supported: LeatherArmor, WOOL, STAINED_GLASS(_PANE), STAINED_CLAY
     *
     * @param color the desired color
     *
     * @return the instantiated ItemBuilder
     */
    public ItemBuilder setColor(Color color) {
        try {
            if (getItemMeta() instanceof LeatherArmorMeta) {
                setLeatherArmorColor(color);
            } else if (item.getType().equals(Material.valueOf("INK_SACK"))) {
                setDyeItemColor(DyeColor.getByColor(color));
            } else if (item.getType().equals(Material.valueOf("WOOL"))
                    || item.getType().equals(Material.valueOf("STAINED_GLASS"))
                    || item.getType().equals(Material.valueOf("STAINED_GLASS_PANE"))
                    || item.getType().equals(Material.valueOf("STAINED_CLAY"))
                    || item.getType().equals(Material.valueOf("BED"))) {
                setBlockItemColor(DyeColor.getByColor(color));
            }
        } catch (Throwable th) {
            System.err.println("Your Spigot-Version is not supported: " + th.getMessage());
        }

        return this;
    }

    /**
     * Sets the color using org.bukkit.DyeColor<br>
     * Supported: LeatherArmor, WOOL, STAINED_GLASS(_PANE), STAINED_CLAY<br>
     * <br>
     * Something missing? Contact me! (Sprax2013)
     *
     * @param color the desired color
     *
     * @return the instantiated ItemBuilder
     */
    public ItemBuilder setColor(DyeColor color) {
        setColor(color.getColor());

        return this;
    }

    /**
     * Chooses a random DyeColor and tries to set it
     *
     * @return the instantiated ItemBuilder
     */
    public ItemBuilder setRandomDyeColor() {
        setColor(DyeColor.values()[RandomNumberUtils.randomInteger(0, DyeColor.values().length - 1)]);

        return this;
    }

    // TODO JavaDocs
    public ItemBuilder setPotion(PotionType type, int level, boolean splash, boolean extended) {
        if (getItemMeta() instanceof PotionMeta) {
            item = new Potion(type, level, splash, extended).toItemStack(1);
        }

        return this;
    }

    // TODO JavaDocs
//    public ItemBuilder setPotionData(PotionData data) {
//        if (getItemMeta() instanceof PotionMeta) {
//            ((PotionMeta) getItemMeta()).setBasePotionData(data);
//        }
//
//        return this;
//    }

    // TODO JavaDocs
    public ItemBuilder setMainPotionEffect(PotionEffectType effectType) {
        if (getItemMeta() instanceof PotionMeta) {
            ((PotionMeta) getItemMeta()).setMainEffect(effectType);
        }

        return this;
    }

    // TODO JavaDocs
    public ItemBuilder addCustomPotionEffect(PotionEffect effect, boolean overwrite) {
        if (getItemMeta() instanceof PotionMeta) {
            ((PotionMeta) getItemMeta()).addCustomEffect(effect, overwrite);
        }

        return this;
    }

    // TODO JavaDocs
    public ItemBuilder removeCustomPotionEffect(PotionEffectType effectType) {
        if (getItemMeta() instanceof PotionMeta) {
            ((PotionMeta) getItemMeta()).removeCustomEffect(effectType);
        }

        return this;
    }

    // TODO JavaDocs
    public ItemBuilder clearCustomPotionEffect() {
        if (getItemMeta() instanceof PotionMeta) {
            ((PotionMeta) getItemMeta()).clearCustomEffects();
        }

        return this;
    }

//    public ItemBuilder setPotionType(PotionType type) {
//        if (getItemMeta() instanceof PotionMeta) {
//            PotionData data = ((PotionMeta) getItemMeta()).getBasePotionData();
//            ((PotionMeta) getItemMeta()).setBasePotionData(new PotionData(type, data.isExtended(), data.isUpgraded
//            ()));
//        }
//
//        return this;
//    }

    public ItemBuilder setBookAuthor(String author) {
        if (getItemMeta() instanceof BookMeta) {
            ((BookMeta) getItemMeta()).setAuthor(author);
        }

        return this;
    }

    public ItemBuilder setBookTitle(String title) {
        if (getItemMeta() instanceof BookMeta) {
            ((BookMeta) getItemMeta()).setTitle(title);
        }

        return this;
    }

//    public ItemBuilder addBookPage(BaseComponent[]... pages) {
//        if (getItemMeta() instanceof BookMeta) {
//            try {
//                ((BookMeta) getItemMeta()).spigot().addPage(pages);
//            } catch (@SuppressWarnings("unused") NoSuchMethodError notSupportedSpigotVersion) {
//                for (BaseComponent[] bCArray : pages) {
//                    String page = null;
//
//                    for (BaseComponent bC : bCArray) {
//                        if (page == null) {
//                            page = bC.toLegacyText();
//                        } else {
//                            page += bC.toLegacyText();
//                        }
//                    }
//
//                    ((BookMeta) getItemMeta()).addPage(page == null ? page : page.replace("§f", "§0"));
//                }
//            }
//        }
//
//        return this;
//    }

//    public ItemBuilder setBookPage(int page, BaseComponent[] text) {
//        if (getItemMeta() instanceof BookMeta) {
//            try {
//                ((BookMeta) getItemMeta()).spigot().setPage(page, text);
//            } catch (@SuppressWarnings("unused") NoSuchMethodError notSupportedSpigotVersion) {
//                String pageContent = null;
//
//                for (BaseComponent bC : text) {
//                    if (pageContent == null) {
//                        pageContent = bC.toLegacyText();
//                    } else {
//                        pageContent += bC.toLegacyText();
//                    }
//                }
//
//                ((BookMeta) getItemMeta()).setPage(page,
//                        pageContent == null ? pageContent : pageContent.replace("§f", "§0"));
//            }
//        }
//
//        return this;
//    }

//    public ItemBuilder setBookPages(List<BaseComponent[]> pages) {
//        if (getItemMeta() instanceof BookMeta) {
//            try {
//                ((BookMeta) getItemMeta()).spigot().setPages(pages);
//            } catch (@SuppressWarnings("unused") NoSuchMethodError notSupportedSpigotVersion) {
//                List<String> pagesLegacy = new ArrayList<>();
//
//                for (BaseComponent[] bCArray : pages) {
//                    String page = null;
//
//                    for (BaseComponent bC : bCArray) {
//                        if (page == null) {
//                            page = bC.toLegacyText();
//                        } else {
//                            page += bC.toLegacyText();
//                        }
//                    }
//
//                    pagesLegacy.add(page == null ? page : page.replace("§f", "§0"));
//                }
//
//                ((BookMeta) getItemMeta()).setPages(pagesLegacy);
//            }
//        }
//
//        return this;
//    }

    /**
     * Builds and returns a copy of the ItemStack.<br>
     * Returning a copy of it makes it possible to use this ItemBuilder instance
     * again if wanted.
     *
     * @return the resulting ItemStack
     */
    public ItemStack build() {
        item.setItemMeta(getItemMeta());

        return new ItemStack(item);
    }

    private void setLeatherArmorColor(Color color) {
        ((LeatherArmorMeta) getItemMeta()).setColor(color);
    }

    @SuppressWarnings("deprecation")
    private void setDyeItemColor(DyeColor color) {
        item.setDurability(color.getDyeData());
    }

    @SuppressWarnings("deprecation")
    private void setBlockItemColor(DyeColor color) {
        item.setDurability(color.getWoolData());
    }

    private ItemMeta getItemMeta() {
        if (meta == null && item != null) {
            meta = item.getItemMeta();
        }

        return meta;
    }
}