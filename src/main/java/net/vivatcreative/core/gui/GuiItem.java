package net.vivatcreative.core.gui;

import net.vivatcreative.core.utils.SkullUtil;
import net.vivatcreative.core.utils.TextUtil;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public final class GuiItem {

    private final ItemStack itemStack;
    private final Material material;
    private final int amount, data;
    private final List<String> lore;
    private String name, customSkull;
    private final Consumer<Player> leftClick, rightClick, middleClick;
    private boolean glowing, hideFlags;
    private final Skull skullType;

    private enum Skull {
        NONE, PLAYER, CUSTOM
    }

    private GuiItem(Builder builder) {
        this.itemStack = builder.item;
        this.material = builder.material;
        this.amount = builder.amount;
        this.name = builder.itemName;
        this.lore = builder.lore;
        this.customSkull = builder.customSkull;
        this.leftClick = builder.leftClick;
        this.rightClick = builder.rightClick;
        this.middleClick = builder.middleClick;
        this.glowing = builder.glowing;
        this.hideFlags = builder.hideFlags;
        this.skullType = builder.skullType;
        this.data = builder.data;
    }

    public static class Builder {
        private ItemStack item;
        private Material material = Material.STONE;
        private int amount = 1, data = 0;
        private List<String> lore;
        private String itemName, customSkull;
        private Consumer<Player> leftClick, rightClick, middleClick;
        private boolean glowing = false, hideFlags = false;
        private Skull skullType = Skull.NONE;
        public InventoryClickEvent e;

        public Builder material(Material mat) {
            this.material = mat;
            return this;
        }

        public Builder amount(int amount) {
            this.amount = amount;
            return this;
        }

        public Builder lore(List<String> lore) {
            this.lore = lore;
            return this;
        }

        public Builder data(int data) {
            this.data = data;
            return this;
        }

        public Builder lore(String... loreItems) {
            ArrayList<String> lore = new ArrayList<>();
            for (int i = 0; i < loreItems.length; i++) {
                lore.add(TextUtil.toColor(loreItems[i]));
            }
            this.lore = lore;
            return this;
        }

        public Builder name(String name) {
            this.itemName = TextUtil.toColor(name);
            return this;
        }

        public Builder playerSkull(String playerName) {
            this.customSkull = playerName;
            this.skullType = Skull.PLAYER;
            this.material = Material.SKULL_ITEM;
            this.data = 3;
            return this;
        }

        public Builder customSkull(String url) {
            this.customSkull = url;
            this.skullType = Skull.CUSTOM;
            this.material = Material.SKULL_ITEM;
            return this;
        }

        public Builder leftClick(Consumer<Player> action) {
            this.leftClick = action;
            return this;
        }

        public Builder rightClick(Consumer<Player> action) {
            this.rightClick = action;
            return this;
        }

        public Builder middleClick(Consumer<Player> action) {
            this.middleClick = action;
            return this;
        }

        public Builder click(Consumer<Player> action) {
            this.leftClick = action;
            this.rightClick = action;
            this.middleClick = action;
            return this;
        }

        public Builder itemStack(ItemStack item) {
            this.item = item;
            return this;
        }

        public Builder glowing() {
            this.glowing = true;
            return this;
        }

        public Builder glowing(boolean bool) {
            this.glowing = bool;
            return this;
        }

        public Builder hideFlags() {
            this.hideFlags = true;
            return this;
        }

        public GuiItem build() {
            return new GuiItem(this);
        }

    }

    public void setName(String name) {
        this.name = TextUtil.toColor(name);
    }

    public void setGlowing(boolean glowing) {
        this.glowing = glowing;
    }

    public Material getMaterial() {
        return material;
    }

    protected ItemStack getItem() {
        ItemStack item = new ItemStack(material, amount, (byte) data);
        if (itemStack != null)
            item = itemStack;
        if (skullType.equals(Skull.NONE))
            item.setItemMeta(defaultMeta(item));
        else if (skullType.equals(Skull.PLAYER))
            item.setItemMeta(playerHeadMeta(item));
        else if (skullType.equals(Skull.CUSTOM))
            item = customSkullMeta();
        return item;
    }

    private ItemMeta defaultMeta(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (name != null)
            meta.setDisplayName(name);
        if (lore != null)
            meta.setLore(lore);
        if (glowing)
            meta.addEnchant(Enchantment.MENDING, 0, true);
        if (hideFlags)
            meta.addItemFlags(ItemFlag.values());
        meta.addItemFlags(ItemFlag.values());
        return meta;
    }

    @SuppressWarnings("deprecation")
    private ItemMeta playerHeadMeta(ItemStack item) {
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        if (customSkull != null)
            meta.setOwner(customSkull);
        if (name != null)
            meta.setDisplayName(name);
        if (lore != null)
            meta.setLore(lore);
        if (glowing)
            meta.addEnchant(Enchantment.MENDING, 0, true);
        if (hideFlags)
            meta.addItemFlags(ItemFlag.values());
        meta.addItemFlags(ItemFlag.values());
        return meta;
    }

    private ItemStack customSkullMeta() {
        ItemStack item = SkullUtil.getCustomSkull(customSkull);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        if (name != null)
            meta.setDisplayName(name);
        if (lore != null)
            meta.setLore(lore);
        if (glowing)
            meta.addEnchant(Enchantment.MENDING, 0, true);
        if (hideFlags)
            meta.addItemFlags(ItemFlag.values());
        meta.addItemFlags(ItemFlag.values());
        item.setItemMeta(meta);
        return item;
    }

    public void rightClickAction(InventoryClickEvent e) {
        if (this.rightClick != null)
            this.rightClick.accept((Player) e.getWhoClicked());
    }

    public void leftClickAction(InventoryClickEvent e) {
        if (this.leftClick != null)
            this.leftClick.accept((Player) e.getWhoClicked());
    }

    public void scrollClickAction(InventoryClickEvent e) {
        if (this.middleClick != null)
            this.middleClick.accept((Player) e.getWhoClicked());
    }
}
