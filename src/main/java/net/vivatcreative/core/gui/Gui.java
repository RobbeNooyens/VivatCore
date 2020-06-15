package net.vivatcreative.core.gui;

import net.vivatcreative.core.utils.Logger;
import net.vivatcreative.core.utils.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public abstract class Gui {

    private final int invSize; // Inventory size (amount of squares) + page number
    private String title = "Gui";
    private Inventory inv;
    private final Player player;
    protected final GuiItem[] guiContents;
    private Gui gui;

    /**
     * Creates a Gui.
     *
     * @param p     the Player the Gui will be opened for
     * @param size  the amount of squares (rows*columns)
     */
    public Gui(final Player p, final int size) {
        this.invSize = size;
        this.player = p;
        this.guiContents = new GuiItem[size];
    }

    /**
     * @return the amount of squares (rows*columns)
     */
    public int getSize() {
        return invSize;
    }

    /**
     * @return the title of the Gui
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the Gui.
     *
     * @param title the title of the Gui to be set
     */
    public void setTitle(String title) {
        this.title = TextUtil.toColor(title);
    }

    /**
     * @return Inventory object
     */
    public Inventory toInventory() {
        return inv;
    }

    /**
     * @return the Player stored in this Gui object
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Opens and registers the Gui.
     */
    public void open() {
        if(inv == null) this.inv = Bukkit.createInventory(null, this.invSize, this.title);
        prepareBuilding();
        for (int i = 0; i < invSize; i++) {
            try {
                inv.setItem(i, (guiContents[i] == null) ? null : guiContents[i].getItem());
            } catch (Exception e) {
                Logger.error("Error when setting item: " + e.getMessage());
                Logger.exception(e);
            }
        }
        player.openInventory(inv);
        GuiManager.addGuiLink(this);
    }

    /**
     * @return the linked Gui
     */
    public Gui getGuiLink() {
        return gui;
    }

    /**
     * Sets a link with the previous Gui.
     *
     * @param gui the Gui to be linked
     */
    public void setGuiLink(final Gui gui) {
        this.gui = gui;
    }

    /**
     * @param slot the Id of the slot to get the item from
     * @return the GuiItem on a specific slot
     */
    protected GuiItem getGuiItem(int slot) {
        return guiContents[slot];
    }

    /**
     * Sets a GuiItem on a specific slot.
     *
     * @param place is the slot
     * @param item  is the GuiItem to be put inside the slot
     */
    protected void setGuiItem(int place, GuiItem item) {
        if (!(place >= 0 && place < 54)) return;
        guiContents[place] = item;
    }

    /**
     * Fills specific slots with GuiItems.
     *
     * @param slots the slot Ids where items should be put in
     * @param items the Items that should be put inside the slots
     */
    protected void fillSlots(int[] slots, GuiItem... items) {
        if (items == null || slots == null || slots.length < 1) return;
        int count = 0;
        for (int i : slots) {
            if (i < 0 || i > 53) continue;
            if (count >= items.length) break;
            guiContents[i] = items[count++];
        }
    }

    /**
     * Called before creating the Inventory object
     */
    public abstract void prepareBuilding();
}
