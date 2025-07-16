package org.donator.donator;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.SkeletonHorse;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.NamespacedKey;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CarCommand implements CommandExecutor {
    private final DonatorManager donatorManager;
    private final JavaPlugin plugin;
    // Map pentru a ține evidența calului invocat de fiecare jucător
    private final Map<UUID, SkeletonHorse> playerHorses = new HashMap<>();
    private final NamespacedKey carKey;

    public CarCommand(JavaPlugin plugin, DonatorManager donatorManager) {
        this.plugin = plugin;
        this.donatorManager = donatorManager;
        this.carKey = new NamespacedKey(plugin, "donator_car");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }
        Player player = (Player) sender;
        String type = donatorManager.getDonatorType(player.getUniqueId());
        if (type == null || !(type.equalsIgnoreCase("car") || type.equalsIgnoreCase("ultra"))) {
            player.sendMessage(ChatColor.RED + "You do not have access to this command!");
            return true;
        }
        // Check if already has a horse
        if (playerHorses.containsKey(player.getUniqueId())) {
            SkeletonHorse horse = playerHorses.get(player.getUniqueId());
            if (!horse.isDead() && horse.isValid()) {
                player.sendMessage(ChatColor.YELLOW + "You already have a car spawned! Wait for the previous one to disappear.");
                return true;
            } else {
                playerHorses.remove(player.getUniqueId());
            }
        }
        // Spawn Skeleton Horse
        Location loc = player.getLocation();
        SkeletonHorse horse = player.getWorld().spawn(loc, SkeletonHorse.class, h -> {
            h.setTamed(true);
            h.setOwner(player);
            h.setCustomName(ChatColor.GRAY + "Car of " + player.getName());
            h.setCustomNameVisible(true);
            h.setAdult();
            h.getPersistentDataContainer().set(carKey, PersistentDataType.STRING, player.getUniqueId().toString());
        });
        horse.addPassenger(player);
        playerHorses.put(player.getUniqueId(), horse);
        player.sendMessage(ChatColor.GREEN + "You have summoned your car (skeleton horse)!");
        return true;
    }

    // Metodă pentru a elimina calul din map când moare
    public void onHorseDeath(SkeletonHorse horse) {
        playerHorses.values().removeIf(h -> h.getUniqueId().equals(horse.getUniqueId()));
    }
} 