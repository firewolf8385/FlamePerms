package firewolf8385.flameperms.events;

import firewolf8385.flameperms.MySQL;
import firewolf8385.flameperms.api.PlayerAPI;
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
    }

}
