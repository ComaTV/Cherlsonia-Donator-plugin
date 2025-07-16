package org.donator.donator;

import org.bukkit.plugin.java.JavaPlugin;
import org.donator.donator.DonatorManager;
import org.donator.donator.commands.CarCommand;
import org.donator.donator.commands.CommandManager;
import org.donator.donator.commands.DonatorCommand;
import org.donator.donator.commands.InvestorCommand;
import org.donator.donator.events.DonatorListener;
import org.donator.donator.commands.DonatorTabCompleter;

public class Main extends JavaPlugin {
    private static Main instance;
    private CommandManager commandManager;
    private DonatorManager donatorManager;

    @Override
    public void onEnable() {
        instance = this;
        
        donatorManager = new DonatorManager(getDataFolder());
        getServer().getPluginManager().registerEvents(new DonatorListener(donatorManager), this);

        commandManager = new CommandManager(donatorManager);
        getCommand("donator").setExecutor(new DonatorCommand(donatorManager));
        getCommand("investor").setExecutor(new InvestorCommand(donatorManager));
        getCommand("car").setExecutor(new CarCommand(donatorManager));
        getCommand("donator").setTabCompleter(new DonatorTabCompleter());

        getLogger().info("Donator plugin enabled successfully!");
    }

    public static Main getInstance() {
        return instance;
    }

    public DonatorManager getDonatorManager() {
        return donatorManager;
    }
}
