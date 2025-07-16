package org.donator.donator;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class DonatorManager {
    private final File file;
    private final FileConfiguration config;
    private final Map<String, DonatorInfo> donators = new HashMap<>();

    public DonatorManager(File dataFolder) {
        this.file = new File(dataFolder, "donators.yml");
        this.config = YamlConfiguration.loadConfiguration(file);
        load();
    }

    public void setDonator(String playerName, String type, LocalDate expiry) {
        DonatorInfo info = new DonatorInfo(type, expiry);
        donators.put(playerName.toLowerCase(), info);
        save();
    }

    public DonatorInfo getDonator(String playerName) {
        return donators.get(playerName.toLowerCase());
    }

    public void removeDonator(String playerName) {
        donators.remove(playerName.toLowerCase());
        save();
    }

    public void applyTag(Player player, String tag) {
        player.addScoreboardTag(tag);
    }

    public void removeAllTags(Player player) {
        player.getScoreboardTags().removeIf(tag -> tag.startsWith("donator_"));
    }

    public void checkExpiries() {
        LocalDate today = LocalDate.now();
        for (Map.Entry<String, DonatorInfo> entry : new HashMap<>(donators).entrySet()) {
            if (entry.getValue().expiry.isBefore(today) || entry.getValue().expiry.isEqual(today)) {
                Player player = Bukkit.getPlayerExact(entry.getKey());
                if (player != null) removeAllTags(player);
                donators.remove(entry.getKey());
            }
        }
        save();
    }

    private void load() {
        donators.clear();
        if (config.contains("players")) {
            for (String key : config.getConfigurationSection("players").getKeys(false)) {
                String type = config.getString("players." + key + ".type");
                String expiryStr = config.getString("players." + key + ".expires");
                LocalDate expiry = LocalDate.parse(expiryStr);
                donators.put(key.toLowerCase(), new DonatorInfo(type, expiry));
            }
        }
    }

    private void save() {
        config.set("players", null);
        for (Map.Entry<String, DonatorInfo> entry : donators.entrySet()) {
            config.set("players." + entry.getKey() + ".type", entry.getValue().type);
            config.set("players." + entry.getKey() + ".expires", entry.getValue().expiry.toString());
        }
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class DonatorInfo {
        public final String type;
        public final LocalDate expiry;
        public DonatorInfo(String type, LocalDate expiry) {
            this.type = type;
            this.expiry = expiry;
        }
    }
} 