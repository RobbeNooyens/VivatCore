package net.vivatcreative.core.commands;

import net.vivatcreative.core.VivatCore;
import net.vivatcreative.core.connections.ConnectionManager;
import net.vivatcreative.core.connections.VivatConnection;
import net.vivatcreative.core.database.MySQLDatabase;
import net.vivatcreative.core.files.FileManager;
import net.vivatcreative.core.gui.general.MainGui;
import net.vivatcreative.core.gui.general.StaffMainGui;
import net.vivatcreative.core.gui.general.WorldsGui;
import net.vivatcreative.core.messages.CoreMessage;
import net.vivatcreative.core.messages.Message;
import net.vivatcreative.core.messages.MessageHelper;
import net.vivatcreative.core.permissions.CorePermission;
import net.vivatcreative.core.utils.Logger;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.*;

public class VivatCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return Message.send(sender, CoreMessage.SHOULD_BE_PLAYER);
        Player p = (Player) sender;

        if (args.length == 0)
            return MainGui.show(p);

        switch (args[0].toLowerCase()) {
            case "list":
                return (!CorePermission.VIVAT_STAFF.hasAndWarn(p)) || listSubplugins(p);
            case "menu":
                return CorePermission.VIVAT_STAFF.has(p) ? StaffMainGui.show(p) : MainGui.show(p);
            case "reload":
                if (!CorePermission.VIVAT_STAFF.hasAndWarn(p)) return true;
                return reload(p, (args.length > 1 ? args[1] : null));
            case "help":
                return Message.send(p, CoreMessage.VIVAT_HELP);
            case "message":
                if (args.length == 1) return Message.send(p, CoreMessage.COMMAND_USAGE, "%usage%", "/vivat message <key>");
                if(!CorePermission.MESSAGE.hasAndWarn(p, args[1])) return true;
                String[] placeholders = new String[args.length - 2];
                System.arraycopy(args, 2, placeholders, 0, args.length - 2);
                return Message.send(p, args[1], placeholders);
            case "worlds":
                return WorldsGui.show(p);
            case "database":
                if (!CorePermission.VIVAT_STAFF.hasAndWarn(p)) return true;
                MySQLDatabase.get().reconnect();
                return Message.send(p, CoreMessage.DATABASE_RECONNECTED);
            default:
                return Message.send(p, CoreMessage.COMMAND_USAGE, "%usage%", "/vivat <help/menu/reload/list/worlds>");

        }
    }

    private boolean reload(Player p, String plugin){
        boolean specification = (plugin != null);
        Set<VivatConnection> connections = ConnectionManager.getConnections();
        List<VivatConnection> iteratable = new ArrayList<>(specification ? 1 : connections.size());
        if(specification && plugin.equalsIgnoreCase("VivatCore")) {
            VivatCore.get().reloadConfig();
            FileManager.removeFile(VivatCore.get(), "messages.yml", null);
            return Message.send(p, CoreMessage.CONFIG_RELOADED);
        } else if (specification)
            connections.forEach((con) -> {
                if (con.getName().equalsIgnoreCase(plugin)) iteratable.add(con);
            });
        else
            iteratable.addAll(connections);
        connections.removeAll(iteratable);
        Iterator<VivatConnection> it = iteratable.iterator();
        while(it.hasNext()){
            VivatConnection c = it.next();
            disablePlugin(c);
            enablePlugin(c.getName());
            it.remove();
            return Message.send(p, CoreMessage.PLUGIN_RELOADED, "%plugin%", c.getPlugin().getName());
        }
        return true;
    }

    private boolean listSubplugins(Player p) {
        List<String> m = new ArrayList<>();
        m.add("&8&m-----------------------------------------------------");
        m.add(" ");
        m.add("&8[&6VivatCore&8] &7v" + VivatCore.get().getDescription().getVersion());
        for (VivatConnection connection : ConnectionManager.getConnections()) {
            JavaPlugin plugin = connection.getPlugin();
            String state = plugin.isEnabled() ? "&aENABLED" : "&cDISABLED";
            m.add(String.format("&8[&e%s&8] &7v%s &8- %s", plugin.getName(), plugin.getDescription().getVersion(), state));
        }
        m.add(" ");
        m.add("&8&m-----------------------------------------------------");
        MessageHelper.sendMessage(p, m);
        return true;
    }

    private boolean enablePlugin(String pl) {
        File pluginFile = new File(VivatCore.get().getDataFolder().getParent() + "/" + pl + ".jar");
        try {
            Plugin plugin = Bukkit.getPluginManager().loadPlugin(pluginFile);
            Bukkit.getPluginManager().enablePlugin(plugin);
            return true;
        } catch (UnknownDependencyException | InvalidPluginException | InvalidDescriptionException e) {
            Logger.exception(e);
            return false;
        }
    }

    private boolean disablePlugin(VivatConnection plugin) {
        ConnectionManager.disconnect(plugin);
        plugin.unregisterCommands();
        HandlerList.unregisterAll(plugin.getPlugin());
        Bukkit.getPluginManager().disablePlugin(plugin.getPlugin());
        return true;
    }
}
