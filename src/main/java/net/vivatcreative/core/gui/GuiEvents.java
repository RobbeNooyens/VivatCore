package net.vivatcreative.core.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Handles the events for Gui actions.
 *
 * @author Robnoo02
 */
public class GuiEvents implements Listener {

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (GuiManager.hasOpenGui(p)) GuiManager.removeGuiLink(p);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClose(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();
        if (GuiManager.hasOpenGui(p)) GuiManager.removeGuiLink(p);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) return;
        Player p = (Player) e.getWhoClicked();
        if (e.getRawSlot() >= e.getView().getTopInventory().getSize()) {
            if (GuiManager.hasOpenGui(p)) e.setCancelled(true);
            return;
        }
        int slot = e.getRawSlot();
        ClickType click = e.getClick();
        if (click == ClickType.WINDOW_BORDER_LEFT || click == ClickType.WINDOW_BORDER_RIGHT) return;
        if (slot < 0) return;
        if (!GuiManager.hasOpenGui(p)) return;

        e.setCancelled(true);
        ItemStack mat = e.getCurrentItem();
        if (mat == null || mat.getType() == Material.AIR) return;
        if (e.getInventory().getHolder() != null) return;

        Gui gui = GuiManager.getGuiFor(p);
        GuiItem item = gui.getGuiItem(slot);
        if (item == null) return;

        if (click == ClickType.LEFT || click == ClickType.SHIFT_LEFT) {
            item.leftClickAction(e);
        } else if (click == ClickType.RIGHT || click == ClickType.SHIFT_RIGHT) {
            item.rightClickAction(e);
        } else if (click == ClickType.MIDDLE) {
            item.scrollClickAction(e);
        }
    }

}
