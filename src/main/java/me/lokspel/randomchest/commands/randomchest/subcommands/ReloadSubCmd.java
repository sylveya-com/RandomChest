package me.lokspel.randomchest.commands.randomchest.subcommands;

import me.lokspel.randomchest.RandomChest;
import me.lokspel.randomchest.commands.SubCommand;
import me.lokspel.randomchest.util.MessageUtil;
import me.lokspel.randomchest.util.PluginPermission;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

public class ReloadSubCmd implements SubCommand {

    @Override
    public String label() {
        return "reload";
    }

    @Override
    public String syntax() {
        return ChatColor.GOLD + "/randomchest reload";
    }

    @Override
    public String description() {
        return ChatColor.GRAY + "Reload configuration and database.";
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws CommandException, IllegalArgumentException {
        return Collections.emptyList();
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!sender.hasPermission(PluginPermission.ADMIN.get())) {
            MessageUtil.send(sender, "no-permission");
            return true;
        }

        RandomChest plugin = RandomChest.getInstance();
        plugin.reloadConfig();
        plugin.getChestUtil().reloadChestDB();
        plugin.getMessages().reload();
        MessageUtil.send(sender, "reload");
        return true;
    }
}
