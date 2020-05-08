package me.firewolf8385.flameperms.commands.subcommands;

import me.firewolf8385.flameperms.SettingsManager;
import me.firewolf8385.flameperms.FlamePerms;
import me.firewolf8385.flameperms.utils.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import me.firewolf8385.flameperms.utils.*;

public class Info implements CommandExecutor
{
    private SettingsManager settings = SettingsManager.getInstance();
    private String gray = settings.getConfig().getString("Theme.color1");
    private String dgreen = settings.getConfig().getString("Theme.color2");
    private String lgreen = settings.getConfig().getString("Theme.color3");
    private String white = settings.getConfig().getString("Theme.color4");

    private static Plugin pl = FlamePerms.getPlugin();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        ChatUtils.chat(sender, dgreen + "&l]" + gray + "&m---------------------------------------------------" + dgreen + "&l[");
        ChatUtils.centeredChat(sender, dgreen + "&lFlamePerms");
        ChatUtils.chat(sender, "  " + gray + "» " + lgreen + "Version " + gray + "- " + white + pl.getDescription().getVersion());
        ChatUtils.chat(sender, "  " + gray + "» " + lgreen + "Author " + gray + "- " + white + pl.getDescription().getAuthors().get(0));
        ChatUtils.chat(sender, dgreen + "&l]" + gray + "&m---------------------------------------------------" + dgreen + "&l[");
        return true;
    }

}
