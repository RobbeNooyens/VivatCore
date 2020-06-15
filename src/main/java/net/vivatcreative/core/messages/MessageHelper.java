package net.vivatcreative.core.messages;

import net.vivatcreative.core.exceptions.MessageKeyNotFoundException;
import net.vivatcreative.core.files.CustomYml;
import net.vivatcreative.core.utils.Logger;
import net.vivatcreative.core.utils.TextUtil;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MessageHelper {

    private static final MessageHelper INSTANCE = new MessageHelper();
    private final Set<CustomYml> keys = new HashSet<>();

    /**
     * Private constructor to ensure singleton
     */
    private MessageHelper() {
    }

    // INTERFACE

    /**
     * Sends a message to a CommandSender with placeholders
     *
     * @param player       is the target to send the message to
     * @param key          is the key that refers to a yml key
     * @param placeholders are optional placeholders (alternating: {%placeholder1%, value1, %placeholder2%, value2})
     */
    protected static void send(CommandSender player, String key, String[] placeholders) {
        List<String> lines = getStringList(key);
        sendMessage(player, lines, placeholders);
    }

    /**
     * Use this method to register a CustomYml as a message file
     *
     * @param file the file containing messages
     */
    public static void register(CustomYml file) {
        INSTANCE.keys.add(file);
    }

    /**
     * Use this method to unregister a CustomYml when disabling the plugin
     *
     * @param file the file containing messages
     */
    public static void unregister(CustomYml file) {
        INSTANCE.keys.remove(file);
    }

    // PRIVATE HELPER METHODS

    private static List<String> getStringList(String key) {
        CustomYml file;
        try {
            file = getFileFromKey(key);
        } catch (MessageKeyNotFoundException e) {
            Logger.exception(e);
            return new ArrayList<>();
        }
        return file.getFile().getStringList(key);
    }

    private static CustomYml getFileFromKey(String key) throws MessageKeyNotFoundException {
        for (CustomYml f : INSTANCE.keys)
            if (f.containsKey(key)) return f;
        throw new MessageKeyNotFoundException(key);
    }

    public static void sendMessage(CommandSender p, List<String> list, Object... placeholders) {
        for (String s : list) {
            if (s == null || s.equalsIgnoreCase("none")) continue;
            sendLine(p, TextUtil.replacePlaceholders(TextUtil.toColor(s), placeholders));
        }
    }

    private static void sendLine(CommandSender p, String line) {
        if (!line.toLowerCase().startsWith("@json"))
            p.sendMessage(TextUtil.toColor(line));
        else
            TextUtil.sendJson(p, line.replaceFirst("@json", ""));
    }


}
