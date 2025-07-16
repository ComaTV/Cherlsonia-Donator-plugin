package org.donator.donator.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.donator.donator.DonatorManager;

import java.util.Arrays;

public class InvestorCommand implements CommandExecutor {
    private final DonatorManager donatorManager;

    public InvestorCommand(DonatorManager donatorManager) {
        this.donatorManager = donatorManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return true;
        }
        Player player = (Player) sender;
        if (!player.getScoreboardTags().contains("donator_investor") && !player.getScoreboardTags().contains("donator_ultra")) {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return true;
        }
        PlayerInventory inv = player.getInventory();
        // Verific corect dacă sloturile de armură sunt goale (null sau AIR)
        boolean hasArmor = false;
        ItemStack[] armor = {inv.getHelmet(), inv.getChestplate(), inv.getLeggings(), inv.getBoots()};
        for (ItemStack item : armor) {
            if (item != null && item.getType() != Material.AIR) {
                hasArmor = true;
                break;
            }
        }
        if (hasArmor) {
            player.sendMessage(ChatColor.RED + "Your armor slots must be empty to receive the special armor.");
            return true;
        }
        ItemStack helmet = new ItemStack(Material.DIAMOND_HELMET);
        ItemMeta helmetMeta = helmet.getItemMeta();
        helmetMeta.setDisplayName(ChatColor.GOLD + "Investor Helmet");
        helmetMeta.setLore(Arrays.asList(ChatColor.AQUA + "Special helmet for investors!"));
        helmet.setItemMeta(helmetMeta);

        ItemStack chest = new ItemStack(Material.DIAMOND_CHESTPLATE);
        ItemMeta chestMeta = chest.getItemMeta();
        chestMeta.setDisplayName(ChatColor.GOLD + "Investor Chestplate");
        chestMeta.setLore(Arrays.asList(ChatColor.AQUA + "Special chestplate for investors!"));
        chest.setItemMeta(chestMeta);

        ItemStack legs = new ItemStack(Material.DIAMOND_LEGGINGS);
        ItemMeta legsMeta = legs.getItemMeta();
        legsMeta.setDisplayName(ChatColor.GOLD + "Investor Leggings");
        legsMeta.setLore(Arrays.asList(ChatColor.AQUA + "Special leggings for investors!"));
        legs.setItemMeta(legsMeta);

        ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS);
        ItemMeta bootsMeta = boots.getItemMeta();
        bootsMeta.setDisplayName(ChatColor.GOLD + "Investor Boots");
        bootsMeta.setLore(Arrays.asList(ChatColor.AQUA + "Special boots for investors!"));
        boots.setItemMeta(bootsMeta);

        inv.setHelmet(helmet);
        inv.setChestplate(chest);
        inv.setLeggings(legs);
        inv.setBoots(boots);
        player.sendMessage(ChatColor.GOLD + "You have received the special investor armor!");
        return true;
    }
}