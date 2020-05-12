package me.firewolf8385.flameperms.utils;

import me.firewolf8385.flameperms.SettingsManager;
import me.firewolf8385.flameperms.enums.DefaultFontInfo;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatUtils
{
    private final static int CENTER_PX = 154;
    private static final SettingsManager settings = SettingsManager.getInstance();

    /**
     * A quicker way to send colored messages to the player in chat.
     * @param p Player
     * @param message Message to be sent.
     */
    public static void chat(Player p, String message)
    {
        p.sendMessage(translate(message));
    }

    /**
     * A quicker way to send colored messages to a command sender.
     * @param sender Command Sender
     * @param message Message to be sent.
     */
    public static void chat(CommandSender sender, String message)
    {
        sender.sendMessage(translate(message));
    }

    /**
     * Translate plain text into colored text.
     * @param message Plain text
     * @return Colored Text
     */
    public static String translate(String message)
    {
        String str = message
                .replace("color1", settings.getConfig().getString("Theme.color1"))
                .replace("color2", settings.getConfig().getString("Theme.color2"))
                .replace("color3", settings.getConfig().getString("Theme.color3"))
                .replace("color4", settings.getConfig().getString("Theme.color4"))
                .replace("color5", settings.getConfig().getString("Theme.color5"));

        return ChatColor.translateAlternateColorCodes('&', str);
    }

    /**
     * Send a centered chat message to a player.
     * @param player Player
     * @param message nessage
     */
    public static void centeredChat(Player player, String message)
    {
        message = translate(message);

        if(message == null || message.equals("")) player.sendMessage("");
        message = ChatColor.translateAlternateColorCodes('&', message);

        int messagePxSize = 0;
        boolean previousCode = false;
        boolean isBold = false;

        for(char c : message.toCharArray())
        {
            if(c == '§')
            {
                previousCode = true;
                continue;
            }
            else if(previousCode == true)
            {
                previousCode = false;
                if(c == 'l' || c == 'L')
                {
                    isBold = true;
                    continue;
                }
                else isBold = false;
            }
            else
            {
                DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
                messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
                messagePxSize++;
            }
        }

        int halvedMessageSize = messagePxSize / 2;
        int toCompensate = CENTER_PX - halvedMessageSize;
        int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
        int compensated = 0;
        StringBuilder sb = new StringBuilder();
        while(compensated < toCompensate)
        {
            sb.append(" ");
            compensated += spaceLength;
        }
        player.sendMessage(sb.toString() + message);
    }

    /**
     * Sender a centered chat message to a CommandSender.
     * @param sender Command Sender
     * @param message Message
     */
    public static void centeredChat(CommandSender sender, String message)
    {
        message = translate(message);

        if(message == null || message.equals("")) sender.sendMessage("");
        message = ChatColor.translateAlternateColorCodes('&', message);

        int messagePxSize = 0;
        boolean previousCode = false;
        boolean isBold = false;

        for(char c : message.toCharArray())
        {
            if(c == '§')
            {
                previousCode = true;
                continue;
            }
            else if(previousCode == true)
            {
                previousCode = false;
                if(c == 'l' || c == 'L')
                {
                    isBold = true;
                    continue;
                }
                else isBold = false;
            }
            else
            {
                DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
                messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
                messagePxSize++;
            }
        }

        int halvedMessageSize = messagePxSize / 2;
        int toCompensate = CENTER_PX - halvedMessageSize;
        int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
        int compensated = 0;
        StringBuilder sb = new StringBuilder();
        while(compensated < toCompensate)
        {
            sb.append(" ");
            compensated += spaceLength;
        }
        sender.sendMessage(sb.toString() + message);
    }

}
