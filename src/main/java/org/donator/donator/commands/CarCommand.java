package org.donator.donator.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.donator.donator.DonatorManager;

public class CarCommand implements CommandExecutor {
    private final DonatorManager donatorManager;

    public CarCommand(DonatorManager donatorManager) {
        this.donatorManager = donatorManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return true;
        }
        Player player = (Player) sender;
        if (!player.getScoreboardTags().contains("donator_car") && !player.getScoreboardTags().contains("donator_ultra")) {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return true;
        }
        if (player.getScoreboardTags().contains("has_special_horse")) {
            player.sendMessage(ChatColor.RED + "You already have a special horse. Wait until it dies to summon another.");
            return true;
        }
        Location loc = player.getLocation();
        Horse horse = (Horse) player.getWorld().spawnEntity(loc, EntityType.HORSE);
        horse.setOwner(player);
        horse.setTamed(true);
        horse.setCustomName(ChatColor.GOLD + "Donator Horse");
        horse.setCustomNameVisible(true);
        horse.setAdult();
        horse.getInventory().setSaddle(new ItemStack(Material.SADDLE));
        player.addScoreboardTag("has_special_horse");
        horse.addPassenger(player);
        player.sendMessage(ChatColor.GOLD + "You have received your special horse!");
        return true;
    }
} 