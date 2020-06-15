package net.vivatcreative.core.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.util.Arrays;

public abstract class BlueprintGui extends Gui implements SlotIDs {

    private String lore;
    private int totalPages = 1, page = 1;
    private GuiItem[] items;
    private boolean hideHomeBtn = false;

    // Constructor

    /**
     * @param p            the Player the Gui will be opened for
     */
    public BlueprintGui(Player p) {
        super(p, 54);
        this.setGuiLink(GuiManager.getGuiFor(p));
        this.items = new GuiItem[4 * 9 * totalPages];
    }

    // Setters

    public void setItems(GuiItem... guiItems) {
        if(items.length < guiItems.length) setTotalPages((((guiItems.length)-1) / 36) + 1);
        this.items = guiItems;
    }

    public void setItems(int startPage, GuiItem... guiItems) {
        if(items.length-((startPage-1)*36) < guiItems.length) setTotalPages((startPage-1)*36+((guiItems.length-1) / 36) + 1);
        int j = 0;
        for(int i = getRelativeSlot(startPage, 0); i < items.length; i++, j++){
            if(j >= guiItems.length) break;
            items[i] = guiItems[j];
        }
    }

    public void setItem(int slot, GuiItem item) {
        if (items.length <= slot) throw new ArrayIndexOutOfBoundsException(
                "Trying to fill slot " + slot + " while there are only " + items.length + " slots available.");
        items[slot] = item;
    }

    public void setItem(int page, int slot, GuiItem item) {
        int relativeSlot = getRelativeSlot(page, slot);
        if (items.length <= relativeSlot) throw new ArrayIndexOutOfBoundsException(
                "Trying to fill slot " + relativeSlot + " while there are only " + items.length + " slots available.");
        items[relativeSlot] = item;
    }

    /**
     * Sets the current page number.
     *
     * @param page the pagenumber to be set
     */
    public void setPage(int page) {
        this.page = page;
    }

    /**
     * Set the total amount of pages.
     *
     * @param totalPages the total amount of pages to be set
     */
    public void setTotalPages(int totalPages) {
        if(totalPages < getPage()) return;
        this.totalPages = totalPages;
        GuiItem[] replace = new GuiItem[4 * 9 * totalPages];
        for(int i = 0; i < replace.length; i++){
            if(i >= items.length) break;
            replace[i] = items[i];
        }
        items = replace;
    }

    // Getters

    public int getRelativeSlot(int page, int slot){
        return (page-1)*36+slot;
    }

    public GuiItem getItem(int slot) {
        return items[slot];
    }

    public GuiItem getPane() {
        return Items.PANE;
    }

    /**
     * @return the current page number
     */
    public int getPage() {
        return page;
    }

    /**
     * Sets the total amount of pages based on the amount of items.
     *
     * @param perPage the amount of items per page
     * @param items   the total amount of pages which should be divided over all the
     *                pages
     */
    public static int getTotalPages(int perPage, int items) {
        try {
            int totalPages = items / perPage;
            if (items % perPage > 0) totalPages++;
            return totalPages;
        } catch (Exception e) {
            return 1;
        }
    }

    /**
     * @return the total amount of pages
     */
    public int getTotalPages() {
        return totalPages;
    }

    // Home Button

    public void hideHomeBtn(boolean b) {
        hideHomeBtn = b;
    }

    public abstract void updateGui();

    @Override
    public void prepareBuilding() {
        this.updateGui();
        this.createBorder();
        int startInItems = getRelativeSlot(getPage(), 0);
        for (int i = 9, j = startInItems; i < 45; i++, j++) {
            if (j < items.length)
            this.guiContents[i] = items[j];
            else
                this.guiContents[i] = null;
        }
    }

    private void createBorder() {
        this.lore = String.format("&7Page &e%s/%s", getPage(), getTotalPages());
        GuiItem[] panes = new GuiItem[LINES_SLOTS.length];
        Arrays.fill(panes, Items.PANE);
        this.fillSlots(LINES_SLOTS, panes);
        setButtons();
    }

    private static class Items {
        public static final GuiItem EXIT = new GuiItem.Builder().material(Material.BARRIER).name("&cExit").hideFlags().click(HumanEntity::closeInventory).build(),
        HOME = new GuiItem.Builder().material(Material.IRON_DOOR).name("&7Main Menu").hideFlags()
                .click((p) -> Bukkit.dispatchCommand(p, "vivat menu")).build(),
                PANE = new GuiItem.Builder().material(Material.STAINED_GLASS_PANE).data(7).name(" ").hideFlags().build(),
        PREVIOUS_MENU = new GuiItem.Builder().material(Material.REDSTONE_TORCH_ON).name("&7Back").lore("&8Return to the previous menu.").hideFlags()
                .click((p) -> GuiManager.getGuiFor(p).getGuiLink().open()).build();


    }

    private GuiItem getNextPage(){
        return new GuiItem.Builder().material(Material.PAPER).name("&aNext Page").lore(lore).hideFlags().click((p) ->
            goToPage(getPage() + 1)).build();
    }

    private GuiItem getPreviousPage(){
        return new GuiItem.Builder().material(Material.PAPER).name("&cPrevious Page").lore(lore).hideFlags().click((p) ->
                goToPage(getPage() - 1)).build();
    }

    private void setButtons() {
        this.setGuiItem(45, getGuiLink() == null ? Items.PANE : Items.PREVIOUS_MENU);
        this.setGuiItem(48, getPage() <= 1 ? Items.PANE : getPreviousPage());
        this.setGuiItem(49, hideHomeBtn ? Items.PANE : Items.HOME);
        this.setGuiItem(50, getPage() >= getTotalPages() ? Items.PANE : getNextPage());
        this.setGuiItem(53, Items.EXIT);
    }

    // GuiItem factory methods

    public void goToPage(int page) {
        this.setPage(page);
        this.open();
    }

    public void refreshPage() {
        goToPage(getPage());
    }
}
