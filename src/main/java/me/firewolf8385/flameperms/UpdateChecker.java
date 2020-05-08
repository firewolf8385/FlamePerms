package me.firewolf8385.flameperms;

import org.bukkit.Bukkit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class UpdateChecker
{
    private String currentVersion;
    public static String latestVersion;
    public static boolean update = false;
    private final int RESOURCEID = 0;


    /**
     * Create new Update Checker.
     * @param currentVersion Current Version of the plugin.
     */
    public UpdateChecker(String currentVersion)
    {
        this.currentVersion = currentVersion;
        check.start();
    }

    /**
     * Check if update is available.
     * @return Whether update is available.
     */
    public boolean updateAvailble()
    {
        if(!currentVersion.equals(latestVersion))
        {
            return true;
        }

        return false;
    }

    /**
     * Check if update is available.
     */
    private Thread check = new Thread()
    {
        @Override
        public void run()
        {
            URL url = null;
            try
            {
                url = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + RESOURCEID);
            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }

            URLConnection conn = null;
            try
            {
                conn = url.openConnection();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            try
            {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                latestVersion = reader.readLine();
                if (!latestVersion.equals(currentVersion)) {
                    update = true;
                    Bukkit.getLogger().info("[" + FlamePerms.getPlugin().getName() + "] There is an update available.\nYour Version: " + currentVersion + "\nLatest Version: " + latestVersion);
                }
                else
                {
                    update = false;
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    };

}