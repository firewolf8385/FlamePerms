package firewolf8385.simplepermissions.commands.subcommands;

import firewolf8385.simplepermissions.MySQL;
import firewolf8385.simplepermissions.SettingsManager;
import firewolf8385.simplepermissions.utils.ChatUtils;
import firewolf8385.simplepermissions.utils.Rank;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateGroup implements CommandExecutor
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
        if(args.length == 0)
        {
            ChatUtils.chat(sender, settings.getConfig().getString("Messages.createGroupUsage"));
            return true;
        }

        // Check if group already exists.
        if(Rank.exists(args[0]))
        {
            ChatUtils.chat(sender, settings.getConfig().getString("Messages.groupAlreadyExists").replace("%group%", args[0]));
            return true;
        }

        try
        {
            PreparedStatement statement = MySQL.getConnection().prepareStatement("INSERT INTO sp_groups (name) VALUES (?)");
            statement.setString(1, args[0].toLowerCase());

            statement.executeUpdate();
            ChatUtils.chat(sender, settings.getConfig().getString("Messages.groupCreated").replace("%group%", args[0]));
        }
        catch(SQLException e)
        {
            ChatUtils.chat(sender, settings.getConfig().getString("Messages.errorCreatingGroup").replace("%group%", args[0]));
            e.printStackTrace();
        }

        return true;
    }

}
