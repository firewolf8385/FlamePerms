package firewolf8385.simplepermissions.commands;

import firewolf8385.simplepermissions.commands.subcommands.Info;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class SP implements CommandExecutor
{
    private Info info;


    public SP()
    {
        this.info = new Info();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        // Runs if No Arguments
        if(args.length == 0)
        {
            this.info.onCommand(sender, cmd, label, Arrays.copyOfRange(args, 0, args.length));
            return true;
        }

        switch(args[0].toLowerCase())
        {
            // sp info
            case "info":
                this.info.onCommand(sender, cmd, label, Arrays.copyOfRange(args, 1, args.length));
                break;


            default:
                this.info.onCommand(sender, cmd, label, Arrays.copyOfRange(args, 1, args.length));
                break;
        }

        return true;
    }
}
