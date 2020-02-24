package firewolf8385.simplepermissions.commands.subcommands;

import firewolf8385.simplepermissions.MySQL;
import firewolf8385.simplepermissions.SettingsManager;
import firewolf8385.simplepermissions.utils.ChatUtils;
import firewolf8385.simplepermissions.utils.Permissions;
import firewolf8385.simplepermissions.utils.Rank;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SetFamily implements CommandExecutor
{
    private SettingsManager settings = SettingsManager.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        // Exit if no permission.
        if(!sender.hasPermission("sp.admin"))
        {
            ChatUtils.chat(sender, settings.getConfig().getString("Messages.noPermission"));
            return true;
        }

        // Check if not used correctly.
        if(args.length < 2)
        {
            ChatUtils.chat(sender, settings.getConfig().getString("Messages.setFamilyUsage"));
            return true;
        }

        // Check if group exists.
        if(!Rank.exists(args[0]))
        {
            ChatUtils.chat(sender, settings.getConfig().getString("Messages.groupDoesNotExist").replace("%group%", args[0]));
            return true;
        }

        try
        {
            PreparedStatement statement = MySQL.getConnection().prepareStatement("UPDATE sp_groups SET family=? WHERE name = ?");
            statement.setString(1, args[1].toLowerCase());
            statement.setString(2, args[0].toLowerCase());

            statement.executeUpdate();
            ChatUtils.chat(sender, settings.getConfig().getString("Messages.setFamily").replace("%group%", args[0]).replace("%family%", args[1]));

            for(Player pl : Bukkit.getOnlinePlayers())
            {
                Permissions.resetPermissions(pl);
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        return true;
    }
}
