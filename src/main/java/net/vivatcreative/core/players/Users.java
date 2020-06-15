package net.vivatcreative.core.players;

import net.vivatcreative.core.utils.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

public class Users {

    private static final Users INSTANCE = new Users();

    private final Set<VivatOnlinePlayer> players = new HashSet<>();

    private Users() {
    }

    private boolean contains(UUID uuid) {
        for (VivatPlayer p : players)
            if (p.getUUID().equals(uuid)) return true;
        return false;
    }

    private VivatPlayer get(UUID uuid) {
        for (VivatPlayer p : players)
            if (p.getUUID().equals(uuid)) return p;
        return null;
    }

    private VivatPlayer addPlayer(OfflinePlayer p) {
        VivatOnlinePlayer vP = new VivatOnlinePlayer(p.getUniqueId());
        players.add(vP);
        return vP;
    }

    private VivatPlayer getVivatPlayer(OfflinePlayer p) {
        if (contains(p.getUniqueId()))
            return get(p.getUniqueId());
        else if(p.isOnline())
            return addPlayer(p);
        else
            return new VivatOfflinePlayer(p.getUniqueId());
    }

    private void removeVivatPlayer(OfflinePlayer p) {
        VivatPlayer player = get(p.getUniqueId());
        if(player instanceof VivatOnlinePlayer)
            players.remove(player);
    }

    public static VivatPlayer get(OfflinePlayer player) {
        return INSTANCE.getVivatPlayer(player);
    }

    public static VivatPlayer get(String uuidOrName) {
        OfflinePlayer player = PlayerUtil.getOfflinePlayer(uuidOrName);
        if (player == null) return null;
        return INSTANCE.getVivatPlayer(player);
    }

    public static void remove(OfflinePlayer player) {
        INSTANCE.removeVivatPlayer(player);
    }

    public static int clearOfflinePlayerCache() {
        int clearedEntries = 0;
        Iterator<VivatOnlinePlayer> it = INSTANCE.players.iterator();
        while (it.hasNext()) {
            VivatPlayer p = it.next();
            OfflinePlayer pl = Bukkit.getOfflinePlayer(p.getUUID());
            if (pl == null || !pl.isOnline()) {
                it.remove();
                clearedEntries++;
            }
        }
        return clearedEntries;
    }
}
