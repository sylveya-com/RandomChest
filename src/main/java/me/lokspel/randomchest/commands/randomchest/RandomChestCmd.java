package me.lokspel.randomchest.commands.randomchest;

import com.google.common.collect.ImmutableList;
import me.lokspel.randomchest.RandomChest;
import me.lokspel.randomchest.commands.SubCommand;
import me.lokspel.randomchest.commands.randomchest.subcommands.ReloadSubCmd;
import me.lokspel.randomchest.commands.randomchest.subcommands.RestoreSubCmd;
import me.lokspel.randomchest.commands.randomchest.subcommands.SelectSubCmd;
import me.lokspel.randomchest.commands.randomchest.subcommands.UnselectSubCmd;
import me.lokspel.randomchest.util.Enableable;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RandomChestCmd extends Command implements Enableable {

    private final List<SubCommand> subCommands;
    private final List<String> tabCompletes;

    public RandomChestCmd() {
        super(
                "randomchest",
                "Main RandomChest command",
                "/randomchest [ reload, select, unselect, restore ]",
                ImmutableList.of("rs")
        );
        subCommands = ImmutableList.of(
                new ReloadSubCmd(),
                new SelectSubCmd(),
                new UnselectSubCmd(),
                new RestoreSubCmd()
        );
        tabCompletes = subCommands.stream().map(SubCommand::label).collect(Collectors.collectingAndThen(Collectors.toList(), ImmutableList::copyOf));
    }

    @Override
    public void enable() {
        PluginCommand pluginCommand = RandomChest.getInstance().getCommand(getName());
        if (pluginCommand != null) {
            pluginCommand.setExecutor((sender, command, label, args) -> execute(sender, label, args));
            pluginCommand.setTabCompleter((sender, command, alias, args) -> tabComplete(sender, alias, args));
        }
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws CommandException, IllegalArgumentException {
        if (args.length == 1) {
            String current = args[0].toLowerCase();
            return tabCompletes.stream()
                    .filter(s -> s.toLowerCase().startsWith(current))
                    .collect(Collectors.toList());
        }

        if (args.length >= 2) {
            for (final SubCommand subCommand : subCommands) {
                if (args[0].equalsIgnoreCase(subCommand.label())) {
                    return subCommand.tabComplete(sender, alias, args);
                }
            }
        }

        return Collections.emptyList();
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (args.length >= 1) {
            for (final SubCommand subCommand : subCommands) {
                if (args[0].equalsIgnoreCase(subCommand.label())) {
                    return subCommand.execute(sender, commandLabel, args);
                }
            }
        }

        return commandOverview(sender);
    }

    private boolean commandOverview(CommandSender sender) {
        sender.sendMessage(ChatColor.DARK_AQUA + "-----------------------------------------------------");
        sender.sendMessage(ChatColor.GOLD + "RandomChest Commands");
        sender.sendMessage(ChatColor.DARK_AQUA + "-----------------------------------------------------");
        for (final SubCommand subCmd : subCommands) {
            sender.sendMessage(subCmd.syntax() + ChatColor.GRAY + " - " + subCmd.description());
        }
        sender.sendMessage(ChatColor.DARK_AQUA + "-----------------------------------------------------");
        return true;
    }
}
