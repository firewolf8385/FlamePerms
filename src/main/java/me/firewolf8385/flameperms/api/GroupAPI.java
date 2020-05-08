package me.firewolf8385.flameperms.api;

import me.firewolf8385.flameperms.MySQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GroupAPI
{
    private static final int NAME = 1;
    private static final int FAMILY = 2;
    private static final int PERMISSIONS = 2;
    private static final int ORDER = 3;

    /**
     * Add a permission to a group.
     * @param group Group to add permission to.
     * @param permission The permission to be added.
     */
    public static void addPermission(String group, String permission, String server)
    {
        try
        {
            PreparedStatement statement = MySQL.getConnection().prepareStatement("INSERT INTO fp_group_permissions (name, permission, server) VALUES (?,?,?)");
            statement.setString(1, group.toLowerCase());
            statement.setString(2, permission);
            statement.setString(3, server);
            statement.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Create a group.
     * @param group Group to be created.
     */
    public static void createGroup(String group)
    {
        try
        {
            PreparedStatement statement = MySQL.getConnection().prepareStatement("INSERT INTO fp_groups (name) VALUES (?)");
            statement.setString(1, group.toLowerCase());

            statement.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Delete a group from the database.
     * @param group Group to be deleted.
     */
    public static void deleteGroup(String group)
    {
        // Deletes the group from sp_groups.
        try
        {
            PreparedStatement statement = MySQL.getConnection().prepareStatement("DELETE FROM fp_groups WHERE name = ?)");
            statement.setString(1, group.toLowerCase());
            statement.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        // Deletes the group from sp_group_permissions.
        try
        {
            PreparedStatement statement = MySQL.getConnection().prepareStatement("DELETE FROM fp_group_permissions WHERE name = ?)");
            statement.setString(1, group.toLowerCase());
            statement.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        // Deletes the group from sp_users.
        try
        {
            PreparedStatement statement = MySQL.getConnection().prepareStatement("UPDATE fp_users SET groupName=? WHERE groupName = ?");
            statement.setString(1, group.toLowerCase());
            statement.setString(2, "default");
            statement.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Get permissions of a group.
     * @param group The group to get permissions from.
     * @return Permissions of the group.
     */
    public static List<String> getPermissions(String group)
    {
        List<String> groups = getInheritedGroups(group);
        groups.add(group);

        List<String> perms = new ArrayList<>();

        // Using a for loop lessens the amount of data looked through.
        // Especially when using a large amount of groups/permissions.
        for(String g : groups)
        {
            try
            {
                PreparedStatement statement = MySQL.getConnection().prepareStatement("SELECT * from fp_group_permissions WHERE name = ?");
                statement.setString(1, g);
                ResultSet results = statement.executeQuery();

                while(results.next())
                {
                    perms.add(results.getString(PERMISSIONS));
                }

            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        }

        return perms;
    }

    /**
     * Get family of a group.
     * @param group Group to get the family of.
     * @return Family of the group.
     */
    public static String getFamily(String group)
    {
        try
        {
            PreparedStatement statement = MySQL.getConnection().prepareStatement("SELECT * from fp_groups WHERE name = ?");
            statement.setString(1, group.toLowerCase());
            ResultSet results = statement.executeQuery();
            results.next();

            return results.getString(FAMILY);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        return "none";
    }

    /**
     * Get the groups that another group inherits.
     * @param group Group to get inherited groups of.
     * @return Inherited groups of the group.
     */
    public static List<String> getInheritedGroups(String group)
    {
        List<String> inheritedGroups = new ArrayList<>();

        // Get the family of the group.
        String family = getFamily(group);

        // Returns empty list if no family.
        if(family.equals("none"))
        {
            return inheritedGroups;
        }

        // Save a call to the database by checked order after.
        int order = getOrder(group);

        try
        {
            PreparedStatement statement = MySQL.getConnection().prepareStatement("SELECT * from fp_groups WHERE family = ?");
            statement.setString(1, family);
            ResultSet results = statement.executeQuery();

            while(results.next())
            {
                String inheritedGroup = results.getString(NAME);
                if(getOrder(inheritedGroup) < order)
                {
                    inheritedGroups.add(inheritedGroup);
                }
            }

        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        if(!group.equals("default"))
        {
            inheritedGroups.add("default");
        }

        return inheritedGroups;
    }

    /**
     * Get the order of a group.
     * @param group Group to get order of.
     * @return Order of the group.
     */
    public static int getOrder(String group)
    {
        try
        {
            PreparedStatement statement = MySQL.getConnection().prepareStatement("SELECT * from fp_groups WHERE name = ?");
            statement.setString(1, group.toLowerCase());
            ResultSet results = statement.executeQuery();
            results.next();

            return results.getInt(ORDER);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        return 1;
    }

    /**
     * Checks to see if a rank exists.
     * @param group The rank to check.
     * @return Whether rank exists.
     */
    public static boolean groupExists(String group)
    {
        try
        {
            PreparedStatement statement = MySQL.getConnection().prepareStatement("SELECT * from fp_groups WHERE name = ?");
            statement.setString(1, group.toLowerCase());
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
     * Remove a permission from a group.
     * @param group Group to remove permission from.
     * @param permission Permission to be removed.
     */
    public static void removePermission(String group, String permission)
    {
        try
        {
            PreparedStatement statement = MySQL.getConnection().prepareStatement("DELETE FROM fp_group_permissions WHERE name = ? AND permission = ?");
            statement.setString(1, group.toLowerCase());
            statement.setString(2, permission);
            statement.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Set the family of a group.
     * @param group Group to set the family of.
     * @param family Family to set it to.
     */
    public static void setFamily(String group, String family)
    {
        try
        {
            PreparedStatement statement = MySQL.getConnection().prepareStatement("UPDATE fp_groups SET family=? WHERE name = ?");
            statement.setString(1, family);
            statement.setString(2, group.toLowerCase());
            statement.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Set the order of a group.
     * @param group Group to set the order of.
     * @param order Order to set the group to.
     */
    public static void setOrder(String group, int order)
    {
        try
        {
            PreparedStatement statement = MySQL.getConnection().prepareStatement("UPDATE fp_groups SET orderNum=? WHERE name = ?");
            statement.setInt(1, order);
            statement.setString(2, group.toLowerCase());
            statement.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Set the group of a player.
     * @param player Player to set the group of.
     * @param group Group the player should be in.
     */
    public static void setPlayerGroup(UUID player, String group)
    {
        try
        {
            PreparedStatement statement = MySQL.getConnection().prepareStatement("UPDATE fp_users SET groupName=? WHERE uuid = ?");
            statement.setString(1, group.toLowerCase());
            statement.setString(2, player.toString());
            statement.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
}