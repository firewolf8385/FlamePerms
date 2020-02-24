package firewolf8385.simplepermissions.events;

import firewolf8385.simplepermissions.MySQL;
import firewolf8385.simplepermissions.utils.Permissions;
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
        if(!Permissions.exist(e.getPlayer()))
        {
            try
            {
                PreparedStatement statement = MySQL.getConnection().prepareStatement("INSERT INTO sp_users (uuid) VALUES (?)");
                statement.setString(1, e.getPlayer().getUniqueId().toString());

                statement.executeUpdate();
            }
            catch(SQLException ex)
            {
                ex.printStackTrace();
            }
        }

        Permissions.assignPermissions(e.getPlayer());
    }

}
