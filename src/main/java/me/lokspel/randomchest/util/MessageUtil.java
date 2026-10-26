package me.lokspel.randomchest.util;

import me.lokspel.randomchest.RandomChest;
import me.lokspel.randomchest.config.MessagesConfig;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public final class MessageUtil {

    private MessageUtil() {}

    public static void send(CommandSender sender, String message) {
        sender.sendMessage(format(message));
    }

    public static void send(CommandSender sender, String message, String key, Object value) {
        sender.sendMessage(format(message, key, value));
    }

    public static void send(CommandSender sender, String message, String k1, Object v1, String k2, Object v2) {
        sender.sendMessage(format(message, k1, v1, k2, v2));
    }

    public static String format(String message) {
        String resolved = resolve(message);
        return color(replacePrefix(resolved));
    }

    public static String format(String message, String key, Object value) {
        return format(replace(resolve(message), key, String.valueOf(value)));
    }

    public static String format(String message, String k1, Object v1, String k2, Object v2) {
        return format(replace(replace(resolve(message), k1, String.valueOf(v1)), k2, String.valueOf(v2)));
    }

    public static String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    private static String resolve(String message) {
        MessagesConfig config = RandomChest.getInstance().getMessages();
        if (config != null && config.contains(message)) {
            return config.get(message);
        }
        return message;
    }

    private static String replacePrefix(String text) {
        MessagesConfig config = RandomChest.getInstance().getMessages();
        String prefix = config != null ? config.getPrefix() : "";
        return text.replace("%prefix%", prefix);
    }

    private static String replace(String message, String key, String value) {
        return message.replace("%" + key + "%", value);
    }
}
