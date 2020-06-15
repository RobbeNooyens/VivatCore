package net.vivatcreative.core.ranks;

import net.vivatcreative.core.utils.TextUtil;

public enum BuildRank {

    DEFAULT("Unranked", "\u00A77", 7, 10),
    BRONZE("Bronze", "\u00A73", 9, 20),
    SILVER("Silver", "\u00A7f", 0, 30),
    GOLD("Gold", "\u00A76", 1, 40),
    DIAMOND("Diamond", "\u00A7b", 3, 50),
    EMERALD("Emerald", "\u00A7a", 5, 60),
    MASTER("Master", "\u00A75", 10, 70);

    private final String name, colouredName, chatColor;
    private final int blockData, weight;

    BuildRank(String name, String chatColor, int blockData, int weight) {
        this.name = name;
        this.chatColor = chatColor;
        this.colouredName = chatColor + name;
        this.blockData = blockData;
        this.weight = weight;
    }

    public String getChatColor() {
        return chatColor;
    }

    public String getName(boolean coloured) {
        return coloured ? colouredName : name;
    }

    public int getBlockData() {
        return blockData;
    }

    public int getWeight() {
        return weight;
    }

    public static BuildRank fromString(String s) {
        String name = TextUtil.removeColor(s);
        for (BuildRank rank : BuildRank.values())
            if (rank.toString().equalsIgnoreCase(name)) return rank;
        return BuildRank.DEFAULT;
    }

    public static BuildRank fromWeight(int weight) {
        for (BuildRank rank : BuildRank.values()) {
            if (rank.weight == weight)
                return rank;
        }
        return BuildRank.DEFAULT;
    }

    public BuildRank nextRank() {
        return fromWeight(weight + 10);
    }

    public BuildRank previousRank() {
        return fromWeight(weight - 10);
    }

}
