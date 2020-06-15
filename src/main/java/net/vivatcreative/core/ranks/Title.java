package net.vivatcreative.core.ranks;

import net.vivatcreative.core.permissions.CorePermission;
import net.vivatcreative.core.utils.TextUtil;
import org.bukkit.entity.Player;

public class Title {

    private final String key, name;
    private final String[] description, requirements;
    private final int blockData;

    public Title(String key, String name, int blockData, String[] description, String[] requirements){
        this.key = key;
        this.name = name;
        this.description = description;
        this.blockData = blockData;
        this.requirements = requirements;
    }

    public String getKey() {
        return key;
    }

    public String getName(boolean colored) {
        return colored ? TextUtil.toColor(name) : TextUtil.removeColor(name);
    }

    public int getBlockData() {
        return blockData;
    }

    public boolean canEquip(Player player) {
        return CorePermission.EQUIP_TITLE.has(player, key.toLowerCase().replaceAll("_", "-"));
    }

    public String[] getDescription() {
        return description;
    }

    public String[] getRequirements() {
        return requirements;
    }

    public String lwrCase() {
        return TextUtil.removeColor(name).toLowerCase();
    }

    @Override
    public String toString(){
        return key;
    }
}
