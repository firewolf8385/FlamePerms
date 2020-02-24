package firewolf8385.simplepermissions.utils;

import firewolf8385.simplepermissions.MySQL;
import firewolf8385.simplepermissions.SimplePermissions;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Permissions
{
    public static HashMap<UUID, PermissionAttachment> perms = new HashMap<>();

    public static List<String> getPermissions(Player p)
    {
        String rank = "";
        String family = "";
        int order = 0;
        List<String> perms = new ArrayList<>();

        try
        {
            PreparedStatement statement = MySQL.getConnection().prepareStatement("SELECT * from sp_users WHERE uuid = ?");
            statement.setString(1, p.getUniqueId().toString());
            ResultSet results = statement.executeQuery();
            results.next();
            rank = results.getString(2);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        family = Rank.getFamily(rank);
        order = Rank.getOrder(rank);

        if(family == null || family.equals("none"))
        {
            perms.addAll(getPermissions(rank));
        }
        else
        {
            try
            {
                PreparedStatement statement = MySQL.getConnection().prepareStatement("SELECT * from sp_groups WHERE family = ?");
                statement.setString(1, family);
                ResultSet results = statement.executeQuery();

                while(results.next())
                {
                    if(Rank.getOrder(results.getString(1)) <= order)
                    {
                        perms.addAll(getPermissions(results.getString(1)));
                    }
                }

            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        }

        if(!rank.equals("default"))
        {
            perms.addAll(getPermissions("default"));
        }

        return perms;

    }

    public static List<String> getPermissions(String rank)
    {
        List<String> perms = new ArrayList<>();

        try
        {
            PreparedStatement statement = MySQL.getConnection().prepareStatement("SELECT * from sp_group_permissions WHERE name = ?");
            statement.setString(1, rank);
            ResultSet results = statement.executeQuery();

            while(results.next())
            {
                perms.add(results.getString(2));
            }

        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        return perms;
    }


    /**
     * Check if a player is in the database.
     * @param p Player
     * @return true.
     */
    public static boolean exist(Player p)
    {
        try
        {
            PreparedStatement statement = MySQL.getConnection().prepareStatement("SELECT * from sp_users WHERE uuid = ?");
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

    public static void assignPermissions(Player p)
    {
        PermissionAttachment attachment = p.addAttachment(SimplePermissions.getPlugin());
        perms.put(p.getUniqueId(), attachment);
        PermissionAttachment pperms = perms.get(p.getUniqueId());

        for(String str : getPermissions(p))
        {
            pperms.setPermission(str, true);
        }

    }

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
