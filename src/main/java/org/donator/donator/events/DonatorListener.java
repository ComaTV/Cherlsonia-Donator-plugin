package org.donator.donator.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.donator.donator.DonatorManager;

import java.util.Arrays;

public class DonatorListener implements Listener {
    private final DonatorManager donatorManager;

    public DonatorListener(DonatorManager donatorManager) {
        this.donatorManager = donatorManager;
    }

    // 1. Șterge armura specială dacă jucătorul scoate o piesă
    @EventHandler
    public void onArmorRemove(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();
        // Dacă slotul e de armură și piesa scoasă e diamond
        int slot = event.getSlot();
        if (slot >= 36 && slot <= 39) { // armor slots
            ItemStack item = event.getCurrentItem();
            if (item != null && item.getType().name().contains("DIAMOND_")) {
                // Scoate toată armura dacă are tag de donator
                if (player.getScoreboardTags().contains("donator_investor") || player.getScoreboardTags().contains("donator_ultra")) {
                    Arrays.asList(player.getInventory().getArmorContents()).forEach(i -> {
                        if (i != null) i.setAmount(0);
                    });
                    player.getInventory().setArmorContents(new ItemStack[4]);
                    player.sendMessage(ChatColor.RED + "Your special armor has been removed. Use /investor to get it again.");
                }
            }
        }
    }

    // 2. Șterge tagul has_special_horse când calul moare
    @EventHandler
    public void onHorseDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Horse) {
            Horse horse = (Horse) entity;
            if (horse.getOwner() instanceof Player) {
                Player owner = (Player) horse.getOwner();
                if (owner.getScoreboardTags().contains("has_special_horse")) {
                    owner.removeScoreboardTag("has_special_horse");
                    owner.sendMessage(ChatColor.RED + "Your special horse has died. You can summon another with /car.");
                }
            }
        }
    }

    // 3. Verifică expirarea rangurilor la login
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        donatorManager.checkExpiries();
        DonatorManager.DonatorInfo info = donatorManager.getDonator(player.getName());
        if (info != null) {
            donatorManager.applyTag(player, info.type);
        } else {
            donatorManager.removeAllTags(player);
        }
    }
} 