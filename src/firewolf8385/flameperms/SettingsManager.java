package firewolf8385.flameperms;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;


public class SettingsManager
{
    private SettingsManager() { }
    static SettingsManager instance = new SettingsManager();

    Plugin p;

    /**
     * Get the config file.
     * @return config
     */
    public FileConfiguration getConfig()
    {
        return p.getConfig();
    }

    /**
     * Retrieves the Instance of SettingsManager
     * @return Instance
     */
    public static SettingsManager getInstance()
    {
        return instance;
    }

    /**
     * Creates the files if they do not exist.
     * @param p plugin
     */
    public void setup(Plugin p)
    {
        this.p = p;

        // Create the Plugin Folder if it does not exist.
        if (!p.getDataFolder().exists())
        {
            p.getDataFolder().mkdir();
        }

        p.getConfig().options().copyDefaults(true);
        p.saveDefaultConfig();
    }
}