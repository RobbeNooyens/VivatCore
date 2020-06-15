package net.vivatcreative.core.ranks;

import net.vivatcreative.core.utils.TextUtil;

public enum StaffRank {
    MANAGER("Manager", "\u00A7c", 14),
    ADMIN("Admin", "\u00A7c", 14),
    MOD("Mod", "\u00A72", 13),
    HELPER("Helper", "\u00A79", 11),
    BUILDER("Builder", "\u00A75", 10),
    NONE("Player", "\u00A78", 0);

    private final String name, chatColour, colouredName;
    private final int blockData;

    StaffRank(String name, String chatColour, int blockData) {
        this.name = name;
        this.chatColour = chatColour;
        this.colouredName = chatColour + name;
        this.blockData = blockData;
    }

    public String getChatColour() {
        return chatColour;
    }

    public String getName(boolean coloured) {
        return coloured ? colouredName : name;
    }

    public int getBlockData() {
        return blockData;
    }

    public static StaffRank fromString(String s) {
        String name = TextUtil.removeColor(s);
        for (StaffRank rank : StaffRank.values())
            if (rank.toString().equalsIgnoreCase(name)) return rank;
        return StaffRank.NONE;
    }
}
