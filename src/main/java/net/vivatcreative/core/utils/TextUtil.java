package net.vivatcreative.core.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * Class containing helper methods related to Strings.
 *
 * @author Robnoo02
 */
public class TextUtil {

    /**
     * Colors a String containg &-signs.
     *
     * @param input is the uncoded String
     * @return formatted String
     */
    public static String toColor(String input) {
        try {
            return (ChatColor.translateAlternateColorCodes('&', input));
        } catch (Exception e) {
            return input;
        }
    }

    /**
     * Removes color from a String.
     *
     * @param input is the formatted String
     * @return decoded String
     */
    public static String removeColor(String input) {
        try {
            return (ChatColor.stripColor(toColor(input)));
        } catch (Exception e) {
            return input;
        }
    }

    /**
     * Returns the input with the first letter in capital.
     * Ex. toFirstUppder("hello") -> "Hello"
     *
     * @param str is the input String
     * @return the input String with the first letter in uppercase
     */
    public static String toFirstUpper(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * Returns a String with replaced placeholders.
     *
     * @param input        is the input String containing %placeholders%
     * @param placeholders contains (%placeholder1%, value1, %placeholder2%, value2, ...)
     * @return replaced String
     */
    public static String replacePlaceholders(String input, Object... placeholders) {
        if (placeholders == null || (placeholders.length % 2) == 1) return input;
        String output = input;
        for (int i = 0; i < placeholders.length; i += 2) {
            if (placeholders[i] == null || placeholders[i + 1] == null) continue;
            output = output.replaceAll(String.valueOf(placeholders[i]), String.valueOf(placeholders[i + 1]));
        }
        return output;
    }

    /**
     * Sends a JSON message to a player.
     *
     * @param p    is the target to send the message to
     * @param json is the JSON formatted message
     */
    public static void sendJson(CommandSender p, String json) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + p.getName() + " " + json);
    }

}