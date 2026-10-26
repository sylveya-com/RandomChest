package me.lokspel.randomchest.config;

import me.lokspel.randomchest.RandomChest;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public final class MessagesConfig {

    private static final String PREFIX_KEY = "prefix";

    private final RandomChest plugin;
    private FileConfiguration config;

    public MessagesConfig(RandomChest plugin) {
        this.plugin = plugin;
        this.config = load();
    }

    private FileConfiguration load() {
        File file = new File(plugin.getDataFolder(), "messages.yml");
        if (!file.exists()) {
            plugin.saveResource("messages.yml", false);
        }
        return YamlConfiguration.loadConfiguration(file);
    }

    public void reload() {
        this.config = load();
    }

    public String get(String key) {
        return config != null ? config.getString(key, "") : "";
    }

    public boolean contains(String key) {
        return config != null && config.contains(key);
    }

    public String getPrefix() {
        return get(PREFIX_KEY);
    }
}
