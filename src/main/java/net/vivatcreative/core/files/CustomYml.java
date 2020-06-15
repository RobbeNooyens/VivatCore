package net.vivatcreative.core.files;

import net.vivatcreative.core.utils.Logger;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

/**
 * <h1>CustomYml</h1>
 * <hr>
 * <p>This class represents a Yml file.<br> This class
 * is designed for instantiation.
 *
 * @author Robnoo02
 */
public class CustomYml {

    private final File file;
    private YamlConfiguration config;
    private final String fileName;
    private final boolean createNew;
    private int hashCode;
    private final JavaPlugin plugin;

    /**
     * Constructor Protected for static factory instance creation.
     * Returns a CustomYml for a file in a custom folder.
     *
     * @param plugin    in which plugin directory the file is located
     * @param folder    Name subfolder
     * @param fileName  Name file with or without .yml part
     * @param createNew set to false if the file already exists as a resource in the
     *                  plugin itself
     */
    protected CustomYml(final JavaPlugin plugin, final String folder, final String fileName, final boolean createNew) {
        this.fileName = fileName.replaceAll(".yml", ""); // Makes sure that file name doesn't contain '.yml'.
        this.file = new File(plugin.getDataFolder() + File.separator + folder, this.fileName + ".yml");
        this.createNew = createNew;
        this.plugin = plugin;
        setup();
    }

    /**
     * Constructor Protected for static factory instance creation.
     * Returns a CustomYml for a file in the root folder.
     *
     * @param plugin    in which plugin directory the file is located
     * @param fileName  Name file without .yml part.
     * @param createNew true to create a whole new file (if not exists).
     */
    protected CustomYml(final JavaPlugin plugin, final String fileName, final boolean createNew) {
        this.fileName = fileName.replaceAll(".yml", "");
        this.file = new File(plugin.getDataFolder(), this.fileName + ".yml");
        this.createNew = createNew;
        this.plugin = plugin;
        setup();
    }

    /**
     * Sets a field in the yml file.
     * Use this method to save values, otherwise the file won't be saved!
     *
     * @param path the field where the Object should be saved.
     * @param obj  the Object that should be saved.
     */
    public void set(String path, Object obj) {
        config.set(path, obj);
        saveFile();
    }

    /**
     * @param path location of configurationsection.
     * @param key  the key that should be sought.
     * @return true if the configurationsection for the given path contains the key.
     */
    public boolean containsKey(String path, String key) {
        try {
            return config.getConfigurationSection(path).getKeys(false).contains(key);
        } catch (NullPointerException exception) {
            return false;
        }
    }

    /**
     * @param key The key that should be checked.
     * @return true if the config file contains a specific key
     */
    public boolean containsKey(String key) {
        return config.getKeys(false).contains(key);
    }

    /**
     * @return the name of the file without the '.yml' part
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * VERY IMPORTANT!!! This method should be called in order to use the getters
     * and setters.
     */
    private void setup() {
        if (!plugin.getDataFolder().exists() && !plugin.getDataFolder().mkdirs()) // If there's no datafolder, try to create it.
            throw new RuntimeException("Couldn't make a directory for plugin " + plugin.getName()); // Throw an exception if the creation fails.
        if (!file.exists()) if (createNew)
            try {
                if (file.createNewFile())
                    Logger.info("VivatCore >> Created new file: " + fileName);
                else
                    Logger.warn("VivatCore >> File already exists: " + fileName);
            } catch (IOException e) {
                Logger.exception(e);
            }
        else
            plugin.saveResource(fileName + ".yml", false);
        this.config = YamlConfiguration.loadConfiguration(file);
        loadFile();
        saveFile();
    }

    /**
     * Ignore: supports setup()
     */
    private void loadFile() {
        try {
            config.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            Logger.exception(e);
        }
    }

    /**
     * Ignore: supports setup()
     */
    private void saveFile() {
        try {
            config.save(file);
        } catch (IOException e) {
            Logger.exception(e);
        }
    }

    /**
     * Gets a reference to this config file. Only use it to read inforation! Writing
     * content directly to it without saving it correctly can clear the whole yml
     * file.
     *
     * @return the config file of the referenced file.
     */
    public YamlConfiguration getFile() {
        return config;
    }

    /**
     * Two files are equal if their path is equal.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CustomYml)) {
            return false;
        }
        final CustomYml otherYml = (CustomYml) other;
        return this.file.getAbsolutePath().equals(otherYml.file.getAbsolutePath());
    }

    /**
     * HashCode is calculated based on the file's HashCode. Two CustomYml objects
     * refering to the same yml return the same int.
     */
    @Override
    public int hashCode() {
        int result = hashCode;
        if (result == 0) {
            result = this.file.hashCode();
            result = 31 * result + fileName.hashCode();
            hashCode = result;
        }
        return result;

    }

}
