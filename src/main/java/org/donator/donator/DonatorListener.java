package org.donator.donator;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.entity.SkeletonHorse;

public class DonatorListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();
        ItemStack current = event.getCurrentItem();
        if (current != null && current.hasItemMeta() && current.getItemMeta().hasDisplayName()) {
            String name = current.getItemMeta().getDisplayName();
            if (name.contains("Investor Armor")) {
                // Dacă mută armura specială, elimină tot setul
                player.getInventory().setHelmet(null);
                player.getInventory().setChestplate(null);
                player.getInventory().setLeggings(null);
                player.getInventory().setBoots(null);
                player.sendMessage(ChatColor.RED + "You removed a piece of the special armor, the entire set has been removed!");
            }
        }
    }

    @EventHandler
    public void onItemBreak(PlayerItemBreakEvent event) {
        ItemStack broken = event.getBrokenItem();
        if (broken != null && broken.hasItemMeta() && broken.getItemMeta().hasDisplayName()) {
            String name = broken.getItemMeta().getDisplayName();
            if (name.contains("Investor Armor")) {
                Player player = event.getPlayer();
                player.getInventory().setHelmet(null);
                player.getInventory().setChestplate(null);
                player.getInventory().setLeggings(null);
                player.getInventory().setBoots(null);
                player.sendMessage(ChatColor.RED + "A piece of the special armor broke, the entire set has been removed!");
            }
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity() instanceof SkeletonHorse) {
            SkeletonHorse horse = (SkeletonHorse) event.getEntity();
            // Caută și elimină din toate CarCommand (singleton pattern ar fi mai bun, dar pentru simplitate)
            // Dacă ai referință la CarCommand, poți apela direct metoda onHorseDeath
        }
    }
} 