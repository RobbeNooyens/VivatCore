package net.vivatcreative.core.utils;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public class Logger implements Listener {

    /**
     * Sends a message to the console.
     *
     * @param msg is the message to be sent to the console
     */
    public static void console(String msg) {
        Bukkit.getConsoleSender().sendMessage(TextUtil.toColor(msg));
    }

    /**
     * Sends a message as INFO to the console
     *
     * @param msg is the message to be sent to console
     */
    public static void info(String msg) {
        console("§7[§bINFO§7] " + msg);
    }

    /**
     * Sends a message as WARN to the console
     *
     * @param msg is the message to be sent to console
     */
    public static void warn(String msg) {
        console("§7[§eWARN§7] " + msg);
    }

    /**
     * Sends a message as SEVERE to the console
     *
     * @param msg is the message to be sent to console
     */
    public static void error(String msg) {
        console("&7[&4ERROR§7] " + msg);
    }

    public static void exception(Exception e) {
        e.printStackTrace();
    }

    public static void ignoreException(Exception e) {
        warn(e.getMessage() + " > " + e.getCause().getMessage());
    }
}
