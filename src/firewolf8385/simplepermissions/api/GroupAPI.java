package firewolf8385.simplepermissions.api;

import firewolf8385.simplepermissions.MySQL;
import firewolf8385.simplepermissions.utils.Rank;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupAPI
{
    private static final int NAME = 1;
    private static final int FAMILY = 2;
    private static final int PERMISSIONS = 2;
    private static final int ORDER = 3;

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
                PreparedStatement statement = MySQL.getConnection().prepareStatement("SELECT * from sp_group_permissions WHERE name = ?");
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
            PreparedStatement statement = MySQL.getConnection().prepareStatement("SELECT * from sp_groups WHERE name = ?");
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
            PreparedStatement statement = MySQL.getConnection().prepareStatement("SELECT * from sp_groups WHERE family = ?");
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
            PreparedStatement statement = MySQL.getConnection().prepareStatement("SELECT * from sp_groups WHERE name = ?");
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
            PreparedStatement statement = MySQL.getConnection().prepareStatement("SELECT * from sp_groups WHERE name = ?");
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

}
