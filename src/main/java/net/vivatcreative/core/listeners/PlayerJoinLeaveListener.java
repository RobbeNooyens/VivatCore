package net.vivatcreative.core.listeners;

import net.vivatcreative.core.VivatCore;
import net.vivatcreative.core.messages.CoreMessage;
import net.vivatcreative.core.messages.Message;
import net.vivatcreative.core.players.Users;
import net.vivatcreative.core.players.VivatOnlinePlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerJoinLeaveListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (!p.hasPlayedBefore()) {
            Bukkit.getOnlinePlayers().forEach((receiver) -> Message.send(receiver, CoreMessage.WELCOME, "%player%", p.getName()));
            Bukkit.getScheduler().runTaskLater(VivatCore.get(), () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mvtp " + p.getName() + " world_Hub"), 20);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLeave(PlayerQuitEvent e) {
        ((VivatOnlinePlayer) Users.get(e.getPlayer())).saveToDatabase();
        Users.remove(e.getPlayer());
    }
}
