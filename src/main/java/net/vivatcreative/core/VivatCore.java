package net.vivatcreative.core;

import net.vivatcreative.core.commands.VivatCommand;
import net.vivatcreative.core.database.MySQLDatabase;
import net.vivatcreative.core.listeners.PlayerJoinLeaveListener;
import net.vivatcreative.core.files.CustomYml;
import net.vivatcreative.core.files.FileManager;
import net.vivatcreative.core.gui.GuiEvents;
import net.vivatcreative.core.messages.MessageHelper;
import net.vivatcreative.core.utils.Logger;
import net.vivatcreative.core.utils.ReloadUtil;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class VivatCore extends JavaPlugin {

    @Override
    public void onEnable() {
        Logger.info("VivatCore >> Enabling plugin...");

        // Register files
        CustomYml coreMessages = FileManager.getFile(this, "messages.yml", false);
        MessageHelper.register(coreMessages);
        saveDefaultConfig();

        // Database
        MySQLDatabase.get().reconnect();

        // Commands
        getCommand("vivat").setExecutor(new VivatCommand());

        // Events
        Bukkit.getPluginManager().registerEvents(new PlayerJoinLeaveListener(), this);
        Bukkit.getPluginManager().registerEvents(new GuiEvents(), this);

        Logger.info("VivatCore >> Enabled plugin");
    }

    public static VivatCore get() {
        return JavaPlugin.getPlugin(VivatCore.class);
    }

    @Override
    public void onDisable() {
        Logger.info("VivatCore >> Disabling plugin...");
        ReloadUtil.unRegisterBukkitCommand(this, "vivat");
        FileManager.removeFile(this, "messages.yml", null);
        Logger.info("VivatCore >> Disabled plugin");
    }

}
