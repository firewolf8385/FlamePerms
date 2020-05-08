package me.firewolf8385.flameperms;

import me.firewolf8385.flameperms.commands.SP;
import me.firewolf8385.flameperms.events.PlayerJoin;
import me.firewolf8385.flameperms.events.PlayerQuit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class FlamePerms extends JavaPlugin
{
    /***************************************************************************************
     *    Title: FlamePerms
     *    Author: firewolf8385
     *    Date: February 23rd, 2020
     *    Code version: 1.0
     ***************************************************************************************/
    private static Plugin pl;
    private SettingsManager settings = SettingsManager.getInstance();

    /**
     * Get instance of the plugin.
     * @return Plugin
     */
    public static Plugin getPlugin()
    {
        return pl;
    }

    /**
     * Run startup tasks.
     */
    public void onEnable()
    {
        pl = this;

        // Create Config File.
        settings.setup(this);

        // Enable bStats
        MetricsLite metrics = new MetricsLite(this, 6593);

        // Checks for any new updates.
        //UpdateChecker update = new UpdateChecker(this.getDescription().getVersion());

        // MySQL connect to database.
        MySQL.openConnection();

        // Create tables.
        MySQL.createTables();

        // Register Commands / Events
        registerCommands();
        registerEvents();
    }

    /**
     * Run shutdown tasks.
     */
    public void onDisable()
    {
        pl = null;
    }

    /**
     * Register commands used by the plugin.
     */
    private void registerCommands()
    {
        getCommand("flameperms").setExecutor(new SP());
    }

    /**
     * Register events used by the plugin.
     */
    private void registerEvents()
    {
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuit(), this);
    }

}
