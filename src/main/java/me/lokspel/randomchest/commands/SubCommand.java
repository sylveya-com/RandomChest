package me.lokspel.randomchest.commands;

import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;

import java.util.List;

public interface SubCommand {

    String label();
    String syntax();
    String description();

    List<String> tabComplete(CommandSender sender, String alias, String[] args)
            throws CommandException, IllegalArgumentException;

    boolean execute(CommandSender sender, String commandLabel, String[] args);

}
