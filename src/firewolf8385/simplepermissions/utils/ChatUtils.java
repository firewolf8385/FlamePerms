package firewolf8385.simplepermissions.utils;

import firewolf8385.simplepermissions.enums.DefaultFontInfo;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatUtils
{
    private final static int CENTER_PX = 154;

    /**
     * A quicker way to send colored messages to the player in chat.
     * @param p Player
     * @param message Message to be sent.
     */
    public static void chat(Player p, String message)
    {
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    /**
     * A quicker way to send colored messages to a command sender.
     * @param sender Command Sender
     * @param message Message to be sent.
     */
    public static void chat(CommandSender sender, String message)
    {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    /**
     * Translate plain text into colored text.
     * @param message Plain text
     * @return Colored Text
     */
    public static String translate(String message)
    {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    /**
     * Send a centered chat message to a player.
     * @param player Player
     * @param message nessage
     */
    public static void centeredChat(Player player, String message)
    {
        message = ChatUtils.translate(message);

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
        message = ChatUtils.translate(message);

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
