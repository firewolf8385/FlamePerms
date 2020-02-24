package firewolf8385.simplepermissions.events;

import firewolf8385.simplepermissions.api.PlayerAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener
{

    @EventHandler
    public void onQuit(PlayerQuitEvent e)
    {
        PlayerAPI.perms.remove(e.getPlayer().getUniqueId());
    }

}