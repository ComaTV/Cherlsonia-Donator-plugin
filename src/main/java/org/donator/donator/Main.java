package org.donator.donator;

import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private DonatorManager donatorManager;

    @Override
    public void onEnable() {
        donatorManager = new DonatorManager(this);
        this.getCommand("donator").setExecutor(new DonatorCommand(this, donatorManager));
        this.getCommand("investor").setExecutor(new InvestorCommand(donatorManager));
        this.getCommand("car").setExecutor(new CarCommand(this, donatorManager));
        getServer().getPluginManager().registerEvents(new DonatorListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
