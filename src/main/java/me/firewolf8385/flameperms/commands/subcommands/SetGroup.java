package me.firewolf8385.flameperms.commands.subcommands;

import me.firewolf8385.flameperms.SettingsManager;
import me.firewolf8385.flameperms.api.GroupAPI;
import me.firewolf8385.flameperms.api.PlayerAPI;
import me.firewolf8385.flameperms.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetGroup implements CommandExecutor
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
            ChatUtils.chat(sender, settings.getConfig().getString("Messages.setUsage"));
            return true;
        }

        Player t = Bukkit.getPlayer(args[0]);
        String group = args[1];

        // Check if group exists.
        if(!GroupAPI.groupExists(group))
        {
            ChatUtils.chat(sender, settings.getConfig().getString("Messages.groupDoesNotExist")
                    .replace("%group%", group));
            return true;
        }

        GroupAPI.setPlayerGroup(t.getUniqueId(), group);
        ChatUtils.chat(sender, settings.getConfig().getString("Messages.setGroup")
                .replace("%target%", t.getName())
                .replace("%group%", group));

        PlayerAPI.resetPermissions(t);

        return true;
    }

}
