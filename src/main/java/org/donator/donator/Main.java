package org.donator.donator;

import org.bukkit.plugin.java.JavaPlugin;
import io.papermc.paper.command.CommandManager;

public final class Main extends JavaPlugin {

    private DonatorManager donatorManager;

    @Override
    public void onEnable() {
        donatorManager = new DonatorManager(this);
        CommandManager cm = this.getServer().getCommandManager();
        cm.register("donator", new DonatorCommand(this, donatorManager));
        cm.register("investor", new InvestorCommand(donatorManager));
        cm.register("car", new CarCommand(this, donatorManager));
        getServer().getPluginManager().registerEvents(new DonatorListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
