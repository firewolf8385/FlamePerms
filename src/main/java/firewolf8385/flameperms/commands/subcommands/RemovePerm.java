package firewolf8385.flameperms.commands.subcommands;

import firewolf8385.flameperms.SettingsManager;
import firewolf8385.flameperms.api.GroupAPI;
import firewolf8385.flameperms.api.PlayerAPI;
import firewolf8385.flameperms.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class RemovePerm implements CommandExecutor
{
    private SettingsManager settings = SettingsManager.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        // Exit if no permission.
        if(!sender.hasPermission("fp.admin"))
        {
            ChatUtils.chat(sender, settings.getConfig().getString("Messages.noPermission"));
            return true;
        }

        // Check if not used correctly.
        if(args.length < 2)
        {
            ChatUtils.chat(sender, settings.getConfig().getString("Messages.removePermUsage"));
            return true;
        }

        String group = args[0];
        String permission = args[1];

        // Check if group exists.
        if(!GroupAPI.groupExists(group))
        {
            ChatUtils.chat(sender, settings.getConfig().getString("Messages.groupDoesNotExist")
                    .replace("%group%", group));
            return true;
        }

        GroupAPI.removePermission(group, permission);
        ChatUtils.chat(sender, settings.getConfig().getString("Messages.permissionRemoved")
                .replace("%group%", group)
                .replace("%permission%", permission));

        // Reset everyone's permissions.
        for(Player pl : Bukkit.getOnlinePlayers())
        {
            PlayerAPI.resetPermissions(pl);
        }

        return true;
    }
}