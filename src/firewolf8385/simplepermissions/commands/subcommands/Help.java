package firewolf8385.simplepermissions.commands.subcommands;

import firewolf8385.simplepermissions.SettingsManager;
import firewolf8385.simplepermissions.utils.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Help implements CommandExecutor
{
    private SettingsManager settings = SettingsManager.getInstance();
    private String gray = settings.getConfig().getString("Theme.color1");
    private String dgreen = settings.getConfig().getString("Theme.color2");
    private String lgreen = settings.getConfig().getString("Theme.color3");
    private String white = settings.getConfig().getString("Theme.color4");

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        // Check if sender has permission.
        if(!(sender.hasPermission("sp.admin")))
        {
            ChatUtils.chat(sender, settings.getConfig().getString("Messages.noPermission"));
            return true;
        }

        ChatUtils.chat(sender, dgreen + "&l]" + gray + "&m---------------------------------------------------" + dgreen + "&l[");
        ChatUtils.centeredChat(sender, dgreen + "&lPermissions Help " + lgreen + "[1/1]");
        ChatUtils.chat(sender, "  " + gray + "» " + lgreen + "/simple addperm [group] [perm]" + gray + " - " + white + "Add a permission.");
        ChatUtils.chat(sender, "  " + gray + "» " + lgreen + "/simple creategroup [group]" + gray + " - " + white + "Create a group.");
        ChatUtils.chat(sender, "  " + gray + "» " + lgreen + "/simple deletegroup [group]" + gray + " - " + white + "Delete a group.");
        ChatUtils.chat(sender, "  " + gray + "» " + lgreen + "/simple setfamily [group] [family]" + gray + " - " + white + "set a group's family.");
        ChatUtils.chat(sender, "  " + gray + "» " + lgreen + "/simple setgroup [player] [group]" + gray + " - " + white + "set a player's group.");
        ChatUtils.chat(sender, "  " + gray + "» " + lgreen + "/simple setorder [group] [order]" + gray + " - " + white + "set a group's order.");
        ChatUtils.chat(sender, dgreen + "&l]" + gray + "&m---------------------------------------------------" + dgreen + "&l[");

        return true;
    }

}
