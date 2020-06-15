package net.vivatcreative.core.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.UUID;

public class SkullUtil {

    public static ItemStack getCustomSkull(String skinURL) {
        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        if (skinURL == null || skinURL.isEmpty())
            return head;
        ItemMeta headMeta = head.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", skinURL));
        Field profileField = null;
        try {
            profileField = headMeta.getClass().getDeclaredField("profile");
        } catch (NoSuchFieldException | SecurityException e) {
            Logger.exception(e);
        }
        profileField.setAccessible(true);
        try {
            profileField.set(headMeta, profile);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            Logger.exception(e);
        }
        head.setItemMeta(headMeta);
        return head;
    }

    public static ItemStack getPlayerSkull(OfflinePlayer owner) {
        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        if (owner != null && meta.hasOwner())
            meta.setOwningPlayer(owner);
        item.setItemMeta(meta);
        return item;
    }
}
