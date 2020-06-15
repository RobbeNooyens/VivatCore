package net.vivatcreative.core.utils;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Class containing helper methods to easily get players based
 * on a String that's either a name or a UUID.
 *
 * @author Robnoo02
 */
public class PlayerUtil {

    /**
     * Returns an OfflinePlayer by name or UUID
     *
     * @param nameOrUUID name or UUID of the requested player
     * @return the player matching the name or UUID
     */
    @SuppressWarnings("deprecation")
    public static OfflinePlayer getOfflinePlayer(String nameOrUUID) {
        try {
            return Bukkit.getOfflinePlayer(UUID.fromString(nameOrUUID));
        } catch(Exception e){
            return Bukkit.getOfflinePlayer(nameOrUUID);
        }
    }

    /**
     * Returns a Player by name or UUID
     *
     * @param nameOrUUID name or UUID of the requested player
     * @return the player matching the name or UUID
     */
    public static Player getOnlinePlayer(String nameOrUUID) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getName().equalsIgnoreCase(nameOrUUID) || player.getUniqueId().toString().equalsIgnoreCase(nameOrUUID))
                return player;
        }
        return null;
    }

}

