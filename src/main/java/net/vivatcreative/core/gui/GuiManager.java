package net.vivatcreative.core.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

import java.util.HashMap;
import java.util.UUID;

public class GuiManager {

    private static final GuiManager INSTANCE = new GuiManager();
    private final HashMap<UUID, Gui> currentGuis = new HashMap<>();

    private GuiManager() {
    }

    /**
     * Saves a Gui with a Player's UUID as key.
     *
     * @param gui to be saved containg the Player
     */
    public static void addGuiLink(Gui gui) {
        INSTANCE.currentGuis.put(gui.getPlayer().getUniqueId(), gui);
    }

    /**
     * Checks if Player currently has a Gui opened.
     *
     * @param p the Player that's being checked
     * @return true if the Player has an opened Gui
     */
    public static boolean hasOpenGui(Player p) {
        if (!INSTANCE.currentGuis.containsKey(p.getUniqueId())) return false; // Player not registered in currentGuis
        if (p.getOpenInventory().getTopInventory().getType() == InventoryType.CHEST) return true; // Player has open Gui
        removeGuiLink(p); // Player registered in currentGuis but has no Gui opened
        return false;
    }

    /**
     * @param p is the Players whose Gui is requested
     * @return the opened Gui for the Player, returns null if the Player has no opened Gui
     */
    public static Gui getGuiFor(Player p) {
        return hasOpenGui(p) ? INSTANCE.currentGuis.get(p.getUniqueId()) : null;
    }

    /**
     * Removes the reference to a Gui if there is any.
     *
     * @param p the Player whose reference should be removed
     */
    public static void removeGuiLink(Player p) {
        INSTANCE.currentGuis.remove(p.getUniqueId());
    }
}
