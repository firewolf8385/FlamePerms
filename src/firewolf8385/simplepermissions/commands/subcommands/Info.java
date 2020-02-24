package firewolf8385.simplepermissions.commands.subcommands;

import firewolf8385.simplepermissions.SimplePermissions;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import firewolf8385.simplepermissions.utils.*;

public class Info implements CommandExecutor
{
    private static Plugin pl = SimplePermissions.getPlugin();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        ChatUtils.chat(sender, "&2&l]&8&m---------------------------------------------------&2&l[");
        ChatUtils.centeredChat((Player) sender, "&2&lSimplePermissions");
        ChatUtils.chat(sender, "  &8» &aVersion &8- &f" + pl.getDescription().getVersion());
        ChatUtils.chat(sender, "  &8» &aAuthor &8- &f" + pl.getDescription().getAuthors().get(0));
        ChatUtils.chat(sender, "&2&l]&8&m---------------------------------------------------&2&l[");
        return true;
    }

}
