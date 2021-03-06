package me.firewolf8385.flameperms.events;

import me.firewolf8385.flameperms.FlamePerms;
import me.firewolf8385.flameperms.MySQL;
import me.firewolf8385.flameperms.api.PlayerAPI;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PlayerJoin implements Listener
{

    @EventHandler
    public void onJoin(PlayerJoinEvent e)
    {

        // Run join tasks async to avoid blocking the main thread.
        Bukkit.getScheduler().runTaskAsynchronously(FlamePerms.getPlugin(), () -> {
            MySQL.ensureConnection();

            if(!PlayerAPI.playerExists(e.getPlayer()))
            {
                try
                {
                    PreparedStatement statement = MySQL.getConnection().prepareStatement("INSERT INTO fp_users (uuid) VALUES (?)");
                    statement.setString(1, e.getPlayer().getUniqueId().toString());

                    statement.executeUpdate();
                }
                catch(SQLException ex)
                {
                    ex.printStackTrace();
                }
            }

            PlayerAPI.assignPermissions(e.getPlayer());
        });
    }

}
