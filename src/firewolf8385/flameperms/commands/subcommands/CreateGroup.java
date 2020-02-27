package firewolf8385.flameperms.commands.subcommands;

import firewolf8385.flameperms.SettingsManager;
import firewolf8385.flameperms.api.GroupAPI;
import firewolf8385.flameperms.utils.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


public class CreateGroup implements CommandExecutor
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
        if(args.length == 0)
        {
            ChatUtils.chat(sender, settings.getConfig().getString("Messages.createGroupUsage"));
            return true;
        }

        String group = args[0].toLowerCase();

        // Check if group already exists.
        if(GroupAPI.groupExists(group))
        {
            ChatUtils.chat(sender, settings.getConfig().getString("Messages.groupAlreadyExists")
                    .replace("%group%", group));
            return true;
        }

        GroupAPI.createGroup(group);
        ChatUtils.chat(sender, settings.getConfig().getString("Messages.groupCreated")
                .replace("%group%", group));

        return true;
    }

}
