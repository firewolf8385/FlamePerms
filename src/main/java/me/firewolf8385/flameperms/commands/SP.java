package me.firewolf8385.flameperms.commands;

import me.firewolf8385.flameperms.commands.subcommands.*;
import me.firewolf8385.flameperms.commands.subcommands.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class SP implements CommandExecutor
{
    private AddPerm addPerm;
    private CreateGroup createGroup;
    private Help help;
    private Info info;
    private RemovePerm removePerm;
    private SetGroup setGroup;
    private SetFamily setFamily;
    private SetOrder setOrder;


    public SP()
    {
        this.addPerm = new AddPerm();
        this.createGroup = new CreateGroup();
        this.help = new Help();
        this.info = new Info();
        this.removePerm = new RemovePerm();
        this.setGroup = new SetGroup();
        this.setFamily = new SetFamily();
        this.setOrder = new SetOrder();
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
            case "info":
                this.info.onCommand(sender, cmd, label, Arrays.copyOfRange(args, 1, args.length));
                break;
            case "addperm":
                this.addPerm.onCommand(sender, cmd, label, Arrays.copyOfRange(args, 1, args.length));
                break;
            case "removeperm":
                this.removePerm.onCommand(sender, cmd, label, Arrays.copyOfRange(args, 1, args.length));
                break;
            case "creategroup":
                this.createGroup.onCommand(sender, cmd, label, Arrays.copyOfRange(args, 1, args.length));
                break;
            case "help":
                this.help.onCommand(sender, cmd, label, Arrays.copyOfRange(args, 1, args.length));
                break;
            case "setgroup":
                this.setGroup.onCommand(sender, cmd, label, Arrays.copyOfRange(args, 1, args.length));
                break;
            case "setfamily":
                this.setFamily.onCommand(sender, cmd, label, Arrays.copyOfRange(args, 1, args.length));
                break;
            case "setorder":
                this.setOrder.onCommand(sender, cmd, label, Arrays.copyOfRange(args, 1, args.length));
                break;
            default:
                this.help.onCommand(sender, cmd, label, Arrays.copyOfRange(args, 1, args.length));
                break;
        }

        return true;
    }
}
