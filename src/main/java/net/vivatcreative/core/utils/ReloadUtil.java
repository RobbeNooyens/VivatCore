package net.vivatcreative.core.utils;

import java.lang.reflect.Field;
import java.util.HashMap;

import org.bukkit.command.Command;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * https://bukkit.org/threads/how-to-unregister-commands-from-your-plugin.131808/
 * @author zeeveener
 *
 */
public class ReloadUtil {

	private static Object getPrivateField(Object object, String field)
			throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		Class<?> clazz = object.getClass();
		Field objectField = clazz.getDeclaredField(field);
		objectField.setAccessible(true);
		Object result = objectField.get(object);
		objectField.setAccessible(false);
		return result;
	}

	public static void unRegisterBukkitCommand(JavaPlugin plugin, String command) {
		PluginCommand cmd = plugin.getCommand(command);
		try {
			Object result = getPrivateField(plugin.getServer().getPluginManager(), "commandMap");
			SimpleCommandMap commandMap = (SimpleCommandMap) result;
			Object map = getPrivateField(commandMap, "knownCommands");
			@SuppressWarnings("unchecked")
			HashMap<String, Command> knownCommands = (HashMap<String, Command>) map;
			knownCommands.remove(cmd.getName());
			for (String alias : cmd.getAliases()) {
				if (knownCommands.containsKey(alias) && knownCommands.get(alias).toString().contains(plugin.getName())) {
					knownCommands.remove(alias);
				}
			}
		} catch (Exception e) {
			Logger.exception(e);
		}
	}

}
