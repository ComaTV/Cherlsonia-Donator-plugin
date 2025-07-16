package org.donator.donator;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DonatorManager {
    private final JavaPlugin plugin;
    private final File file;
    private FileConfiguration config;
    private final Map<UUID, DonatorInfo> donators = new HashMap<>();

    public DonatorManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), "donators.yml");
        load();
    }

    public void load() {
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            plugin.saveResource("donators.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(file);
        donators.clear();
        if (config.isConfigurationSection("donators")) {
            for (String uuidStr : config.getConfigurationSection("donators").getKeys(false)) {
                UUID uuid = UUID.fromString(uuidStr);
                String type = config.getString("donators." + uuidStr + ".type");
                String expires = config.getString("donators." + uuidStr + ".expires");
                donators.put(uuid, new DonatorInfo(type, expires));
            }
        }
    }

    public void save() {
        config.set("donators", null);
        for (Map.Entry<UUID, DonatorInfo> entry : donators.entrySet()) {
            String path = "donators." + entry.getKey();
            config.set(path + ".type", entry.getValue().type);
            config.set(path + ".expires", entry.getValue().expires);
        }
        try {
            config.save(file);
        } catch (IOException e) {
            Bukkit.getLogger().severe("Could not save donators.yml: " + e.getMessage());
        }
    }

    public void addDonator(UUID uuid, String type, LocalDate expires) {
        donators.put(uuid, new DonatorInfo(type, expires.toString()));
        save();
    }

    public boolean isDonator(UUID uuid, String type) {
        DonatorInfo info = donators.get(uuid);
        if (info == null) return false;
        if (!info.type.equalsIgnoreCase(type) && !info.type.equalsIgnoreCase("ultra")) return false;
        LocalDate now = LocalDate.now();
        LocalDate exp = LocalDate.parse(info.expires);
        return now.isBefore(exp) || now.isEqual(exp);
    }

    public String getDonatorType(UUID uuid) {
        DonatorInfo info = donators.get(uuid);
        return info != null ? info.type : null;
    }

    private static class DonatorInfo {
        String type;
        String expires;
        DonatorInfo(String type, String expires) {
            this.type = type;
            this.expires = expires;
        }
    }
} 