package firewolf8385.simplepermissions.commands.subcommands;

import firewolf8385.simplepermissions.SettingsManager;
import firewolf8385.simplepermissions.api.GroupAPI;
import firewolf8385.simplepermissions.api.PlayerAPI;
import firewolf8385.simplepermissions.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DeleteGroup implements CommandExecutor
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
            ChatUtils.chat(sender, settings.getConfig().getString("Messages.deleteGroupUsage"));
            return true;
        }

        String group = args[0].toLowerCase();

        // Check if group already exists.
        if(!GroupAPI.groupExists(group))
        {
            ChatUtils.chat(sender, settings.getConfig().getString("Messages.groupDoesNotExist")
                    .replace("%group%", group));
            return true;
        }

        GroupAPI.deleteGroup(group);
        ChatUtils.chat(sender, settings.getConfig().getString("Messages.groupDeleted").replace("%group%", args[0]));

        // Reset everyone's permissions.
        for(Player pl : Bukkit.getOnlinePlayers())
        {
            PlayerAPI.resetPermissions(pl);
        }

        return true;
    }

}