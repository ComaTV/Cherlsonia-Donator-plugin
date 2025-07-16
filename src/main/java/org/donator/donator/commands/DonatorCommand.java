package org.donator.donator.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.donator.donator.DonatorManager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DonatorCommand implements CommandExecutor {
    private final DonatorManager donatorManager;

    public DonatorCommand(DonatorManager donatorManager) {
        this.donatorManager = donatorManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage(ChatColor.RED + "Only operators can use this command.");
            return true;
        }
        if (args.length != 3) {
            sender.sendMessage(ChatColor.RED + "/donator [name] [car|investor|amandoua] [days]");
            return true;
        }
        String playerName = args[0];
        String type = args[1].toLowerCase();
        String tag;
        switch (type) {
            case "car": tag = "donator_car"; break;
            case "investor": tag = "donator_investor"; break;
            case "amandoua": tag = "donator_ultra"; break;
            default:
                sender.sendMessage(ChatColor.RED + "Invalid type. Use car, investor, or amandoua.");
                return true;
        }
        int days;
        try {
            days = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.RED + "Invalid number of days.");
            return true;
        }
        LocalDate expiry = LocalDate.now().plusDays(days);
        donatorManager.setDonator(playerName, tag, expiry);
        Player target = Bukkit.getPlayerExact(playerName);
        if (target != null) {
            donatorManager.applyTag(target, tag);
            target.sendMessage(ChatColor.GREEN + "You have received the donator rank: " + tag + " until " + expiry.format(DateTimeFormatter.ISO_DATE));
        }
        sender.sendMessage(ChatColor.GREEN + "Donator rank set for " + playerName + ": " + tag + " until " + expiry.format(DateTimeFormatter.ISO_DATE));
        return true;
    }
} 