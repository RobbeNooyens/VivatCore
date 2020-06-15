package net.vivatcreative.core.utils;

import net.vivatcreative.core.exceptions.WorldNotFoundException;
import org.bukkit.ChatColor;

public enum VivatWorld {
    FREEBUILD("world_Freebuild", "Freebuild", 'c', 6, true, false), //
    BRONZE("world_Bronze", "Bronze", '3', 9, true, true), //
    SILVER("world_Silver", "Silver", 'f', 0, true, true), //
    GOLD("world_Gold", "Gold", '6', 1, true, true),//
    DIAMOND("world_Diamond", "Diamond", 'b', 3, true, true), //
    EMERALD("world_Emerald", "Emerald", 'a', 5, true, true), //
    MASTER("world_Master", "Master", '5', 10, true, true), //
    BUILDBATTLE("world_BB", "Build Battle", 'e', 4, false, false), //
    HUB("world_Hub", "Hub", '1', 11, false, false);

    public final String officialName, name, coloured, lwrCase;
    public final int blockData;
    public final boolean isPlotWorld, isRanked;

    VivatWorld(String officialName, String name, char colour, int blockData, boolean isPlotWorld, boolean isRanked) {
        this.officialName = officialName;
        this.name = name;
        this.coloured = "§" + colour + name;
        this.lwrCase = name.toLowerCase();
        this.blockData = blockData;
        this.isPlotWorld = isPlotWorld;
        this.isRanked = isRanked;
    }

    public static VivatWorld fromString(String world) throws WorldNotFoundException {
        String wS = ChatColor.stripColor(world).toLowerCase();
        for (VivatWorld w : VivatWorld.values())
            if (w.name.equalsIgnoreCase(wS) || w.officialName.equalsIgnoreCase(wS))
                return w;
        throw new WorldNotFoundException();
    }
}
