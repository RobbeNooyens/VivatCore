package net.vivatcreative.core.files;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Objects;

/**
 * <h1>FileManager</h1>
 * <hr>
 * <p>Singleton that saves, creates and gets custom yml files.<br>
 * Files are stored in a HashMap with a unique path key.</p>
 *
 * @author Robnoo02
 */
public class FileManager {

    /**
     * Instance holding the HashMap with files
     */
    private static final FileManager INSTANCE = new FileManager();

    /**
     * Contains cached files
     */
    private final HashMap<String, CustomYml> files = new HashMap<>();

    /**
     * Private constructor for singleton
     */
    private FileManager() {
    }

    /**
     * Returns a CustomYml
     *
     * @param plugin    to get the right plugin directory
     * @param fileName  the name of the file, with or without .yml extension
     * @param createNew false if the file already exists in the resource folder of the plugin
     * @return CustomYml instance referring to a custom yml file in the root folder
     */
    public static CustomYml getFile(JavaPlugin plugin, String fileName, boolean createNew) {
        Objects.requireNonNull(fileName, "Please specify a file!");
        Objects.requireNonNull(plugin, "Please specify a plugin!");
        String key = toKey(plugin, fileName, "/");
        if (INSTANCE.files.containsKey(key)) // Checks if the file is already cached
            return INSTANCE.files.get(key);
        CustomYml yml = new CustomYml(plugin, fileName, createNew);
        INSTANCE.files.put(key, yml); // Caches the file
        return yml;
    }

    /**
     * Removes a file from the HashMap. Used when the plugin is being disabled.
     *
     * @param plugin   the plugin the file belongs to
     * @param fileName the name of the file
     * @param folder   the folder the file is located in
     */
    public static void removeFile(JavaPlugin plugin, String fileName, String folder) {
        String key = toKey(plugin, fileName, folder == null ? "/" : folder);
        INSTANCE.files.remove(key);
    }

    /**
     * Returns a CustomYml
     *
     * @param plugin    to get the right plugin directory
     * @param fileName  the name of the file, with or without .yml extension
     * @param createNew false if the file already exists in the resource folder of the plugin
     * @param folder    the folder the file is located in
     * @return CustomYml instance referring to a custom yml file in a custom folder
     */
    public static CustomYml getFileInFolder(JavaPlugin plugin, String fileName, boolean createNew, String folder) {
        Objects.requireNonNull(folder, "Please specify a folder!");
        Objects.requireNonNull(fileName, "Please specify a file!");
        Objects.requireNonNull(plugin, "Please specify a plugin!");
        String key = toKey(plugin, fileName, folder);
        if (INSTANCE.files.containsKey(key)) // Checks if the file is already cached
            return INSTANCE.files.get(key);
        CustomYml yml = new CustomYml(plugin, folder, fileName, createNew);
        INSTANCE.files.put(key, yml); // Caches the file
        return yml;
    }

    /**
     * @param plugin   is the plugin the file is located in
     * @param fileName is the name of the file
     * @param folder   is the folder the file is located in. '/' if it's in the root folder
     * @return a referencekey for the HashMap
     */
    private static String toKey(JavaPlugin plugin, String fileName, String folder) {
        return plugin.getName() + folder + fileName.replaceAll(".yml", "");
    }
}
