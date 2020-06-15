package net.vivatcreative.core.messages;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Message {

    private Message() {
    }

    /**
     * Sends a message to a CommandSender with placeholders
     *
     * @param player       is the target to send the message to
     * @param key          is the key that refers to a yml key
     * @param placeholders are optional placeholders (alternating: {%placeholder1%, value1, %placeholder2%, value2})
     */
    public static boolean send(CommandSender player, String key, String... placeholders) {
        MessageHelper.send(player, key, placeholders);
        return true;
    }

    /**
     * Broadcasts a message to all players
     *
     * @param key          is the key that refers to a yml key
     * @param placeholders are optional placeholders (alternating)
     */
    public static void broadcast(String key, String... placeholders) {
        for (Player p : Bukkit.getOnlinePlayers())
            MessageHelper.send(p, key, placeholders);
    }

    public static boolean send(CommandSender player, VivatMessage message, String... placeholders){
        return send(player, message.key(), placeholders);
    }

}
