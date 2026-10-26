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

public class UnselectSubCmd implements SubCommand {

    @Override
    public String label() {
        return "unselect";
    }

    @Override
    public String syntax() {
        return ChatColor.GOLD + "/randomchest unselect";
    }

    @Override
    public String description() {
        return ChatColor.GRAY + "Clear current selection.";
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

        if (!(sender instanceof Player player)) {
            MessageUtil.send(sender, "only-game");
            return true;
        }

        RandomChest.getInstance().getChestUtil().removeSelectedType(player);
        MessageUtil.send(sender, "unselect");
        return true;
    }
}
