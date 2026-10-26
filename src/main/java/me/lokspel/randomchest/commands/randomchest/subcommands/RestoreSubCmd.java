package me.lokspel.randomchest.commands.randomchest.subcommands;

import me.lokspel.randomchest.RandomChest;
import me.lokspel.randomchest.commands.SubCommand;
import me.lokspel.randomchest.util.MessageUtil;
import me.lokspel.randomchest.util.PluginPermission;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class RestoreSubCmd implements SubCommand {

    @Override
    public String label() {
        return "restore";
    }

    @Override
    public String syntax() {
        return ChatColor.GOLD + "/randomchest restore";
    }

    @Override
    public String description() {
        return ChatColor.GRAY + "Restore all chests.";
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

        if (!(sender instanceof Player)) {
            MessageUtil.send(sender, "only-game");
            return true;
        }

        RandomChest.getInstance().getChestUtil().restoreAllChests();
        MessageUtil.send(sender, "restore");
        return true;
    }
}
