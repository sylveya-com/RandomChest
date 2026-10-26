package me.lokspel.randomchest;

import com.tcoded.folialib.FoliaLib;
import me.lokspel.randomchest.commands.randomchest.RandomChestCmd;
import me.lokspel.randomchest.config.MessagesConfig;
import me.lokspel.randomchest.listener.BlockBreakListener;
import me.lokspel.randomchest.listener.InventoryCloseListener;
import me.lokspel.randomchest.listener.PlayerInteractListener;
import me.lokspel.randomchest.util.ChestUtil;
import me.lokspel.randomchest.util.PluginPermission;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class RandomChest extends JavaPlugin {

    private static RandomChest instance;
    private MessagesConfig messages;
    private ChestUtil chestUtil;
    private FoliaLib foliaLib;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        foliaLib = new FoliaLib(this);
        messages = new MessagesConfig(this);
        chestUtil = new ChestUtil(this);

        PluginPermission.registerAll();
        new RandomChestCmd().enable();

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerInteractListener(this), this);
        pm.registerEvents(new BlockBreakListener(this), this);
        pm.registerEvents(new InventoryCloseListener(this), this);

        new Metrics(this, 33763);

        chestUtil.startChestsRespawn();
    }

    @Override
    public void onDisable() {
        PluginPermission.unregisterAll();
        if (foliaLib != null) {
            foliaLib.getScheduler().cancelAllTasks();
        }
        if (chestUtil != null) {
            chestUtil.forceChestsRespawn();
        }
    }

    public static RandomChest getInstance() {
        return instance;
    }

    public MessagesConfig getMessages() {
        return messages;
    }

    public ChestUtil getChestUtil() {
        return chestUtil;
    }

    public FoliaLib getFoliaLib() {
        return foliaLib;
    }
}
