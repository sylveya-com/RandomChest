package me.lokspel.randomchest.commands.randomchest.subcommands;

import me.lokspel.randomchest.RandomChest;
import me.lokspel.randomchest.commands.SubCommand;
import me.lokspel.randomchest.util.MessageUtil;
import me.lokspel.randomchest.util.PluginPermission;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SelectSubCmd implements SubCommand {

    @Override
    public String label() {
        return "select";
    }

    @Override
    public String syntax() {
        return ChatColor.GOLD + "/randomchest select <type>";
    }

    @Override
    public String description() {
        return ChatColor.GRAY + "Select a chest type.";
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws CommandException, IllegalArgumentException {
        if (args.length == 2) {
            ConfigurationSection section = RandomChest.getInstance().getConfig().getConfigurationSection("chestset");
            if (section != null) {
                String current = args[1].toLowerCase();
                return section.getKeys(false).stream()
                        .filter(s -> s.toLowerCase().startsWith(current))
                        .collect(Collectors.toList());
            }
        }
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

        if (args.length < 2) {
            MessageUtil.send(sender, "usage");
            return true;
        }

        RandomChest plugin = RandomChest.getInstance();
        String typeName = args[1];

        if (!plugin.getConfig().contains("chestset." + typeName)) {
            MessageUtil.send(sender, "type-not-found", "type", typeName);
            return true;
        }

        plugin.getChestUtil().selectType(player, typeName);
        Material tool = plugin.getChestUtil().getToolMaterial("select-tool");
        String toolName = tool != null ? tool.name() : "UNKNOWN";
        MessageUtil.send(sender, "selected",
                "type", typeName,
                "item", toolName);
        return true;
    }
}
