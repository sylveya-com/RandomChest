package me.lokspel.randomchest;

import me.lokspel.randomchest.command.CommandDispatcher;
import me.lokspel.randomchest.command.RegisteredCommand;
import me.lokspel.randomchest.command.ReloadCommand;
import me.lokspel.randomchest.command.RestoreCommand;
import me.lokspel.randomchest.command.SelectCommand;
import me.lokspel.randomchest.command.UnselectCommand;
import me.lokspel.randomchest.listener.BlockBreakListener;
import me.lokspel.randomchest.listener.InventoryCloseListener;
import me.lokspel.randomchest.listener.PlayerInteractListener;
import me.lokspel.randomchest.util.ChestUtil;
import me.lokspel.randomchest.util.LangUtil;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bstats.bukkit.Metrics;
import com.tcoded.folialib.FoliaLib;

import java.util.Arrays;
import java.util.Objects;

public final class RandomChest extends JavaPlugin {

    private LangUtil messages;
    private ChestUtil chestUtil;
    private FoliaLib foliaLib;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        foliaLib = new FoliaLib(this);
        messages = new LangUtil(this);
        chestUtil = new ChestUtil(this);

        CommandDispatcher dispatcher = new CommandDispatcher(this, Arrays.asList(
                new RegisteredCommand("reload", new ReloadCommand(this)),
                new RegisteredCommand("select", new SelectCommand(this)),
                new RegisteredCommand("unselect", new UnselectCommand(this)),
                new RegisteredCommand("restore", new RestoreCommand(this))
        ));
        Objects.requireNonNull(getCommand("randomchest")).setExecutor(dispatcher);

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerInteractListener(this), this);
        pm.registerEvents(new BlockBreakListener(this), this);
        pm.registerEvents(new InventoryCloseListener(this), this);

        new Metrics(this, 33763);

        chestUtil.startChestsRespawn();
    }

    @Override
    public void onDisable() {
        if (foliaLib != null) {
            foliaLib.getScheduler().cancelAllTasks();
        }
        if (chestUtil != null) {
            chestUtil.forceChestsRespawn();
        }
    }

    public LangUtil getMessages() {
        return messages;
    }

    public ChestUtil getChestUtil() {
        return chestUtil;
    }

    public FoliaLib getFoliaLib() {
        return foliaLib;
    }
}
