package net.vivatcreative.core.gui.general;

import net.vivatcreative.core.gui.BlueprintGui;
import net.vivatcreative.core.gui.GuiItem;
import net.vivatcreative.core.players.Users;
import net.vivatcreative.core.ranks.BuildRank;
import net.vivatcreative.core.utils.VivatWorld;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Objects;

public class WorldsGui extends BlueprintGui {

    private static final int[] DIVIDE_PANES = {0, 8, 9, 13, 14, 16, 17, 18, 22, 23, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35};
    private final int weight;

    public WorldsGui(Player p) {
        super(p);
        this.setTitle("&8VivatWorlds");
        this.weight = Users.get(p).getBuildRank().getWeight();
        this.setItem(2, getCategorySign("&f&lRanked VivatWorlds", "&7> &3Bronze", "&7> &6&fSilver", "&7> &6Gold", "&7> &bDiamond", "&7> &aEmerald", "&7> &5Master"));
        this.setItem(6, getCategorySign("&f&lUnranked VivatWorlds", "&7> &eBuild Battle", "&7> &cFreebuild"));
        this.setItem(4, getVivatWorld(VivatWorld.HUB, 11, " ", "&e> Click to teleport"));
        this.setItem(10, getVivatWorld(VivatWorld.BRONZE, 9, bronzeLore()));
        this.setItem(11, getVivatWorld(VivatWorld.SILVER, 0, silverLore()));
        this.setItem(12, getVivatWorld(VivatWorld.GOLD, 1, goldLore()));
        this.setItem(19, getVivatWorld(VivatWorld.DIAMOND, 3, diamondLore()));
        this.setItem(20, getVivatWorld(VivatWorld.EMERALD, 5, emeraldLore()));
        this.setItem(21, getVivatWorld(VivatWorld.MASTER, 10, masterLore()));
        this.setItem(15, getVivatWorld(VivatWorld.BUILDBATTLE, 4, buildbattleLore()));
        this.setItem(24, getVivatWorld(VivatWorld.FREEBUILD, 6, freebuildLore()));
        for (int i : DIVIDE_PANES)
            this.setItem(i, getPane());
    }

    public static boolean show(Player p) {
        Objects.requireNonNull(p);
        (new WorldsGui(p)).open();
        return true;
    }

    private GuiItem getVivatWorld(VivatWorld VivatWorld, int data, String... lore) {
        return new GuiItem.Builder().name(VivatWorld.coloured).material(Material.CONCRETE).data(data).click((p) -> teleport(VivatWorld)).lore(lore).hideFlags().build();
    }

    private GuiItem getCategorySign(String category, String... lore) {
        return new GuiItem.Builder().material(Material.SIGN).name(category).hideFlags().lore(lore).build();
    }

    private void teleport(VivatWorld VivatWorld) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mvtp " + getPlayer().getName() + " " + VivatWorld.officialName);
    }

    private String[] bronzeLore() {
        return new String[]{
                "&7Plot size: &f31x31",
                " ",
                weight >= BuildRank.DEFAULT.getWeight() ? "&aUnlocked" : "",
                "&e> Click to teleport"
        };
    }

    private String[] silverLore() {
        return new String[]{
                "&7Plot size: &f75x75",
                " ",
                weight >= BuildRank.BRONZE.getWeight() ? "&aUnlocked" : "",
                "&e> Click to teleport"
        };
    }

    private String[] goldLore() {
        return new String[]{
                "&7Plot size: &f125x125",
                " ",
                weight >= BuildRank.SILVER.getWeight() ? "&aUnlocked" : "",
                "&e> Click to teleport"
        };
    }

    private String[] diamondLore() {
        return new String[]{
                "&7Plot size: &f225x225",
                " ",
                weight >= BuildRank.GOLD.getWeight() ? "&aUnlocked" : "",
                "&e> Click to teleport"
        };
    }

    private String[] emeraldLore() {
        return new String[]{
                "&7Plot size: &f300x300",
                " ",
                weight >= BuildRank.DIAMOND.getWeight() ? "&aUnlocked" : "",
                "&e> Click to teleport"
        };
    }

    private String[] masterLore() {
        return new String[]{
                "&7Plot size: &f501x501",
                " ",
                weight >= BuildRank.EMERALD.getWeight() ? "&aUnlocked" : "",
                "&e> Click to teleport"
        };
    }

    private String[] freebuildLore() {
        return new String[]{
                "&7Plot size: &f501x501",
                " ",
                " ",
                "&e> Click to teleport"
        };
    }

    private String[] buildbattleLore() {
        return new String[]{
                "&7Choose a theme and build against your friends!",
                "&7Who's the best under pressure?",
                " ",
                "&7VivatWorldedit: &aEnabled",
                "&7Minimum players needed: &f2",
                "&7Time: &f15 minutes",
                "&e> Click to teleport"
        };
    }

    @Override
    public void updateGui() {
    }

}
