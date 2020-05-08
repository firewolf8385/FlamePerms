package me.firewolf8385.flameperms.api;

import me.firewolf8385.flameperms.MySQL;
import me.firewolf8385.flameperms.FlamePerms;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PlayerAPI
{
    public static HashMap<UUID, PermissionAttachment> perms = new HashMap<>();

    private static final int PLAYER = 1;
    private static final int GROUP = 2;

    /**
     * Assign permissions to a player.
     * @param p Player for permissions to be assigned.
     */
    public static void assignPermissions(Player p)
    {
        PermissionAttachment attachment = p.addAttachment(FlamePerms.getPlugin());
        perms.put(p.getUniqueId(), attachment);
        PermissionAttachment pperms = perms.get(p.getUniqueId());

        for(String str : getPermissions(p))
        {
            pperms.setPermission(str, true);
        }

    }

    /**
     * Get the group of a player.
     * @param p Player to get group of.
     * @return The group the player is in.
     */
    public static String getGroup(Player p)
    {
        String group = "default";

        try
        {
            PreparedStatement statement = MySQL.getConnection().prepareStatement("SELECT * from fp_users WHERE uuid = ?");
            statement.setString(1, p.getUniqueId().toString());
            ResultSet results = statement.executeQuery();
            results.next();
            group = results.getString(GROUP);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        return group;
    }

    /**
     * Get the permissions of a player.
     * @param p The player to get permissions of.
     * @return Permissions of the player.
     */
    public static List<String> getPermissions(Player p)
    {
        return GroupAPI.getPermissions(getGroup(p));
    }

    /**
     * Check if the player exists in the database.
     * @param p Player to check whether they exist.
     * @return Whether the player exists.
     */
    public static boolean playerExists(Player p)
    {
        try
        {
            PreparedStatement statement = MySQL.getConnection().prepareStatement("SELECT * from fp_users WHERE uuid = ?");
            statement.setString(1, p.getUniqueId().toString());
            ResultSet results = statement.executeQuery();

            return results.next();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Reset a player's permissions
     * @param p Player to have their permissions reset.
     */
    public static void resetPermissions(Player p)
    {
        p.removeAttachment(perms.get(p.getUniqueId()));

        for(String str : getPermissions(p))
        {
            perms.get(p.getUniqueId()).unsetPermission(str);
        }

        assignPermissions(p);
    }

}
