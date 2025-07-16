package org.donator.donator;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InvestorCommand implements CommandExecutor {
    private final DonatorManager donatorManager;

    public InvestorCommand(DonatorManager donatorManager) {
        this.donatorManager = donatorManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }
        Player player = (Player) sender;
        String type = donatorManager.getDonatorType(player.getUniqueId());
        if (type == null || !(type.equalsIgnoreCase("investor") || type.equalsIgnoreCase("ultra"))) {
            player.sendMessage(ChatColor.RED + "You do not have access to this command!");
            return true;
        }
        // Check if already wearing armor
        for (ItemStack armor : player.getInventory().getArmorContents()) {
            if (armor != null && armor.getType() != Material.AIR) {
                player.sendMessage(ChatColor.YELLOW + "All armor slots must be empty!");
                return true;
            }
        }
        // Create special armor
        ItemStack helmet = createInvestorArmor(Material.DIAMOND_HELMET);
        ItemStack chest = createInvestorArmor(Material.DIAMOND_CHESTPLATE);
        ItemStack legs = createInvestorArmor(Material.DIAMOND_LEGGINGS);
        ItemStack boots = createInvestorArmor(Material.DIAMOND_BOOTS);
        player.getInventory().setHelmet(helmet);
        player.getInventory().setChestplate(chest);
        player.getInventory().setLeggings(legs);
        player.getInventory().setBoots(boots);
        player.sendMessage(ChatColor.AQUA + "You have received the special investor armor!");
        return true;
    }

    private ItemStack createInvestorArmor(Material material) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ChatColor.AQUA + "Investor Armor");
            item.setItemMeta(meta);
        }
        return item;
    }
} 