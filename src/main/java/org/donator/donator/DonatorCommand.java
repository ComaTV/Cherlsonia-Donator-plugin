package org.donator.donator;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.LocalDate;
import java.util.UUID;

public class DonatorCommand implements CommandExecutor {
    private final Main plugin;
    private final DonatorManager donatorManager;

    public DonatorCommand(Main plugin, DonatorManager donatorManager) {
        this.plugin = plugin;
        this.donatorManager = donatorManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("donator.admin")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission.");
            return true;
        }
        if (args.length != 3) {
            sender.sendMessage(ChatColor.YELLOW + "/donator <player> <type> <days>");
            return true;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(ChatColor.RED + "Player is not online!");
            return true;
        }
        String type = args[1].toLowerCase();
        if (!type.equals("car") && !type.equals("investor") && !type.equals("ultra")) {
            sender.sendMessage(ChatColor.RED + "Invalid type! Use: car, investor, ultra");
            return true;
        }
        int zile;
        try {
            zile = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.RED + "Invalid number of days!");
            return true;
        }
        LocalDate expires = LocalDate.now().plusDays(zile);
        UUID uuid = target.getUniqueId();
        donatorManager.addDonator(uuid, type, expires);
        // Set tag
        String tag = "donator_" + type;
        target.setDisplayName(ChatColor.GOLD + "[" + tag + "] " + ChatColor.RESET + target.getName());
        sender.sendMessage(ChatColor.GREEN + "Donator added: " + target.getName() + ", type: " + type + ", expires at: " + expires);
        target.sendMessage(ChatColor.GOLD + "You have received donator status (" + type + ") until: " + expires);
        return true;
    }
} 