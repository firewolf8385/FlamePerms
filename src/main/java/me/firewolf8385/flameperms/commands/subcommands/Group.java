package me.firewolf8385.flameperms.commands.subcommands;

import me.firewolf8385.flameperms.SettingsManager;
import me.firewolf8385.flameperms.api.GroupAPI;
import me.firewolf8385.flameperms.commands.subcommands.group.GPermission;
import me.firewolf8385.flameperms.utils.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Group implements CommandExecutor
{
    private final SettingsManager settings = SettingsManager.getInstance();

    private GPermission gPermission;

    public Group()
    {
        this.gPermission = new GPermission();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        // Exit if not enough arguments.
        if(args.length < 1)
        {
            // TODO: Open Help
            return true;
        }

        // Exit if group doesn't exist.
        if(GroupAPI.groupExists(args[0]))
        {
            ChatUtils.chat(sender, "color2&lError color1- color5That group does not exist.");
            return true;
        }

        switch(args[0])
        {
            case "permission":
            case "perm":
                gPermission.onCommand(sender, cmd, label, args);

        }

        return true;
    }

}
