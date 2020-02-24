package firewolf8385.simplepermissions.utils;

import firewolf8385.simplepermissions.MySQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Rank
{
    /**
     * Check if a rank exists.
     * @param rank Rank
     * @return Exists
     */
    public static boolean exists(String rank)
    {
        try
        {
            PreparedStatement statement = MySQL.getConnection().prepareStatement("SELECT * from sp_groups WHERE name = ?");
            statement.setString(1, rank.toLowerCase());
            ResultSet results = statement.executeQuery();

            return results.next();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        return false;
    }

    public static int getOrder(String rank)
    {
        try
        {
            PreparedStatement statement = MySQL.getConnection().prepareStatement("SELECT * from sp_groups WHERE name = ?");
            statement.setString(1, rank.toLowerCase());
            ResultSet results = statement.executeQuery();
            results.next();
            return results.getInt(3);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        return -1;
    }

    public static String getFamily(String rank)
    {
        try
        {
            PreparedStatement statement = MySQL.getConnection().prepareStatement("SELECT * from sp_groups WHERE name = ?");
            statement.setString(1, rank.toLowerCase());
            ResultSet results = statement.executeQuery();
            results.next();
            return results.getString(2);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

}
