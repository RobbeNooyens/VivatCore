package net.vivatcreative.core.permissions;

import me.lucko.luckperms.LuckPerms;
import me.lucko.luckperms.api.Node;
import me.lucko.luckperms.api.User;
import net.vivatcreative.core.messages.CoreMessage;
import net.vivatcreative.core.messages.Message;
import net.vivatcreative.core.utils.Logger;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public interface VivatPermission {
    String getNode();

    default boolean has(Player p) {
        return p.hasPermission(getNode());
    }

    default boolean has(Player p, Object... placeholders) {
        return p.hasPermission(String.format(getNode(), placeholders));
    }

    default boolean hasAndWarn(Player p) {
        if (p.hasPermission(getNode())) return true;
        Message.send(p, CoreMessage.NO_PERM);
        return false;
    }

    default boolean hasAndWarn(Player p, Object... placeholders) {
        if (p.hasPermission(String.format(getNode(), placeholders))) return true;
        Message.send(p, CoreMessage.NO_PERM);
        return false;
    }

    default void give(OfflinePlayer p) {
        LuckPerms.getApi().getUserManager().getUser(p.getUniqueId())
                .setPermission(LuckPerms.getApi().buildNode(getNode()).build());
    }

    default void give(OfflinePlayer p, Object... placeholders) {
        String node = String.format(getNode(), placeholders);
        User lpPlayer = LuckPerms.getApi().getUserManager().getUser(p.getUniqueId());
        Node permNode = LuckPerms.getApi().buildNode(node).build();
        if(lpPlayer == null) return;
        if (lpPlayer.hasPermission(permNode).asBoolean()) return;
        lpPlayer.setPermission(permNode);
        Logger.info("Added permission " + node + " for " + p.getName());
    }
}
