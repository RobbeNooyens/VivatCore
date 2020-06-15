package net.vivatcreative.core.connections;

import net.vivatcreative.core.utils.ReloadUtil;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Set;

public interface VivatConnection {

    Object get(String key, Object... objects);

    void set(String key, Object... values);

    JavaPlugin getPlugin();

    void onReload();

    default String getName() {
        return getPlugin().getName();
    }

    default void registerCommand(String command, CommandExecutor executor) {
        getPlugin().getCommand(command).setExecutor(executor);
        addCommand(command);
    }

    default void unregisterCommands(){
        getRegisteredCommands().forEach(cmd -> ReloadUtil.unRegisterBukkitCommand(getPlugin(), cmd));
        getRegisteredCommands().clear();
    }

    void addCommand(String command);

    Set<String> getRegisteredCommands();

}
