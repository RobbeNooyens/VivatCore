package net.vivatcreative.core.gui.general;

import net.vivatcreative.core.gui.BlueprintGui;
import net.vivatcreative.core.gui.GuiItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Objects;

public class MainGui extends BlueprintGui {

    private static final String globe = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjBhY2EwMTMxNzhhOWY0NzkxM2U4OTRkM2QwYmZkNGIwYjY2MTIwODI1YjlhYWI4YTRkN2Q5YmYwMjQ1YWJmIn19fQ==";

    public MainGui(Player p) {
        super(p);
        this.setTitle("         &7|  &3Vivat &8Creative &7|");
        this.setGuiLink(null);
        this.setItem(4, getHead());
        this.setItem(12, getRanks());
        this.setItem(22, getTitles());
        this.setItem(13, getPlotHomes());
        this.setItem(23, getHelp());
        this.setItem(21, getReviewHistory());
        this.setItem(14, getWorlds());


        // 23 25 15
        // 11 19 21
        this.hideHomeBtn(true);
    }

    public static boolean show(Player p) {
        Objects.requireNonNull(p);
        (new MainGui(p)).open();
        return true;
    }

    public GuiItem getHead() {
        return new GuiItem.Builder().name("&7Profile: &b" + getPlayer().getName())
                .playerSkull(getPlayer().getName()).hideFlags()
                .build();
    }

    public GuiItem getHelp() {
        return new GuiItem.Builder().name("&bHelp").lore("&7Click to open the help menu.")
                .material(Material.REDSTONE_TORCH_ON).hideFlags()
                .click((p) -> Bukkit.dispatchCommand(p, "help")).build();
    }

    public GuiItem getWorlds() {
        return new GuiItem.Builder().name("&bWorlds").lore("&7Click to open the worlds menu.")
                .customSkull(globe).hideFlags()
                .click((p) -> Bukkit.dispatchCommand(p, "vivat worlds")).build();
    }

    public GuiItem getReviewHistory() {
        return new GuiItem.Builder().name("&bReview History").lore("&7Click to open your review history.")
                .material(Material.BOOKSHELF).hideFlags()
                .click((p) -> Bukkit.dispatchCommand(p, "review history")).build();
    }

    public GuiItem getReviewRecent() {
        return new GuiItem.Builder().name("&bReview Recent").lore("&7Click to open recent reviews.")
                .material(Material.BOOK_AND_QUILL).hideFlags()
                .click((p) -> Bukkit.dispatchCommand(p, "review recent")).build();
    }

    public GuiItem getPlotHomes() {
        return new GuiItem.Builder().name("&bPlothomes").lore("&7Click to open a GUI", "&7with all your plots.")
                .material(Material.COMPASS).hideFlags()
                .click((p) -> Bukkit.dispatchCommand(p, "plothomes")).build();
    }

    public GuiItem getTitles() {
        return new GuiItem.Builder().name("&bTitles").lore("&7Click to choose a title!")
                .material(Material.NAME_TAG).hideFlags()
                .click((p) -> Bukkit.dispatchCommand(p, "title")).build();
    }

    public GuiItem getRanks() {
        return new GuiItem.Builder().name("&bRanks").lore("&7Click to get an overview", "&7of all buildranks and", "&7your rank progress.")
                .material(Material.WOOD_AXE).hideFlags()
                .click((p) -> Bukkit.dispatchCommand(p, "ranks")).build();
    }

    @Override
    public void updateGui() {
    }

}