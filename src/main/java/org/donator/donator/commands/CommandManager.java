package org.donator.donator.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import java.util.ArrayList;
import java.util.List;

public class CommandManager implements TabCompleter {
    public CommandManager(org.donator.donator.DonatorManager donatorManager) {
        // Nu mai există comenzi de waypoints
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        // Nu există tab completare pentru donator/investor/car
        return new ArrayList<>();
    }
} 