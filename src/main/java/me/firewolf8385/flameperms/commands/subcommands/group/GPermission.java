package me.firewolf8385.flameperms.commands.subcommands.group;

import me.firewolf8385.flameperms.api.GroupAPI;
import me.firewolf8385.flameperms.utils.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class GPermission implements CommandExecutor
{

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        // Exit if there are not enough arguments.
        if(args.length == 2)
        {

            return true;
        }

        String group = args[1].toLowerCase();

        switch (args[2].toLowerCase())
        {
            case "add":
                add(sender, group, Arrays.copyOfRange(args,4, args.length));
                break;

            case "list":
                // TODO: Create
                break;

            case "remove":
                break;
        }

        return true;
    }

    /**
     * Add a permission to a group via command.
     * @param sender Command sender.
     * @param group Group permission is added to.
     * @param args Command arguments
     */
    private void add(CommandSender sender, String group, String[] args)
    {
        // Exit if not enough arguments.
        if(args.length == 0)
        {
            ChatUtils.chat(sender, "color2&lUsage color1- color5/fp group [group] permission add [permission] <server>");
        }

        String permission = args[0];
        String server = "global";

        if(args.length == 2)
        {
            server = args[1];
        }

        GroupAPI.addPermission(group, permission, server);
        ChatUtils.chat(sender, "color2&lFlarePerms color1- color4" + permission + " color3has been added to  color4" + group + " color3on color4" + server + "color3.");
    }

    /**
     * Remove a permission from a group via command.
     * @param sender Command sender
     * @param group Group permission is removed from.
     * @param args Command arguments.
     */
    private void remove(CommandSender sender, String group, String[] args)
    {
        // Exit if not enough arguments.
        if(args.length == 0)
        {
            ChatUtils.chat(sender, "color2&lUsage color1- color5/fp group [group] permission remove [permission]");
        }
        String permission = args[0];
        GroupAPI.removePermission(group, permission);

        ChatUtils.chat(sender, "color2&lFlarePerms color1- color4" + permission + " color3has been removed from  color4" + group + "color3.");
    }

}
