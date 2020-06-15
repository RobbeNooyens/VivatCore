package net.vivatcreative.core.connections;

import net.vivatcreative.core.VivatCore;
import net.vivatcreative.core.utils.Logger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.lang.reflect.Field;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConnectionManager {

    private final Set<VivatConnection> list = new HashSet<>();
    private static final ConnectionManager INSTANCE = new ConnectionManager();

    private ConnectionManager() {
    }

    public static void register(VivatConnection connection) {
        INSTANCE.list.add(connection);
        Logger.info("VivatCore >> Plugin connected: " + connection.getName());
    }

    public static void disconnect(String plugin) {
        VivatConnection vivatConnection = null;
        for (VivatConnection conn : INSTANCE.list)
            if (conn.getName().equalsIgnoreCase(plugin)) vivatConnection = conn;
        if (vivatConnection != null) INSTANCE.list.remove(vivatConnection);
        Logger.info("VivatCore >> Plugin disconnected: " + plugin);
    }

    public static void disconnect(VivatConnection vivatConnection) {
        if (vivatConnection != null) {
            INSTANCE.list.remove(vivatConnection);
            Logger.info("VivatCore >> Plugin disconnected: " + vivatConnection.getName());
        }
    }

    public static VivatConnection request(String plugin) {
        for (VivatConnection conn : INSTANCE.list)
            if (conn.getName().equalsIgnoreCase(plugin)) return conn;
        return null;
    }

    public static Set<VivatConnection> getConnections() {
        return INSTANCE.list;
    }

    public static void reloadAll() {
        for (VivatConnection connection : getConnections())
            connection.onReload();
    }
}
