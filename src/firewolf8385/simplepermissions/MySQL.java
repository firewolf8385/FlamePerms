package firewolf8385.simplepermissions;

import org.bukkit.Bukkit;

import java.sql.*;

public class MySQL
{
    static SettingsManager settings = SettingsManager.getInstance();

    private static Connection connection;
    private static String host = settings.getConfig().getString("MySQL.host");
    private static String database = settings.getConfig().getString("MySQL.database");
    private static String username = settings.getConfig().getString("MySQL.username");
    private static String password = settings.getConfig().getString("MySQL.password");
    private static int port = settings.getConfig().getInt("MySQL.port");

    /**
     * Get the connection.
     * @return Connection
     */
    public static Connection getConnection()
    {
        return connection;
    }

    /**
     * Create the tables used by the plugin.
     */
    public static void createTables()
    {
        // "sp_groups"
        if(!tableExists("sp_groups"))
        {
            try
            {
                PreparedStatement statement = MySQL.getConnection().prepareStatement("CREATE TABLE `" + database + "`.`sp_groups` (`name` VARCHAR(45) NULL,`family` VARCHAR(45) NULL DEFAULT 'none',`order` INT NULL DEFAULT 1);");
                statement.executeUpdate();
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        }

        // "sp_group_permissions"
        if(!tableExists("sp_group_permissions"))
        {
            try
            {
                PreparedStatement statement = MySQL.getConnection().prepareStatement("CREATE TABLE `" + database + "`.`sp_group_permissions` (`name` VARCHAR(45) NULL,`permission` VARCHAR(45) NULL);");
                statement.executeUpdate();
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        }

        // "sp_users"
        if(!tableExists("sb_users"))
        {
            try
            {
                PreparedStatement statement = MySQL.getConnection().prepareStatement("CREATE TABLE `" + database + "`.`sp_users` (`uuid` VARCHAR(36) NULL, `groupName` VARCHAR(45) NULL DEFAULT 'default');");
                statement.executeUpdate();
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }

            try
            {
                PreparedStatement statement = MySQL.getConnection().prepareStatement("INSERT INTO `sp_users` (name) VALUES 'default'");
                statement.executeUpdate();
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        }

    }

    /**
     * Open a MySQL Connection
     */
    public static void openConnection()
    {
        try
        {
            if (connection != null && !connection.isClosed())
            {
                return;
            }

            synchronized(SimplePermissions.class)
            {
                if (connection != null && !connection.isClosed())
                {
                    return;
                }

                String str = "";

                if(settings.getConfig().getBoolean("MySQL.useSSL"))
                {
                    str += "?autoReconnect=true&useSSL=false";
                }

                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + str, username, password);
            }
        }
        catch(SQLException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }

    }


    /**
     * Make sure the database is connected.
     */
    public static void ensureConnection()
    {
        try
        {
            PreparedStatement statement = MySQL.getConnection().prepareStatement("SELECT * from sp_groups WHERE");
            statement.executeQuery();
        }
        catch(SQLException e)
        {
            // Nothing.
        }
    }

    /**
     * Check if a table exists
     * @param table table
     * @return exists
     */
    private static boolean tableExists(String table)
    {
        try
        {
            DatabaseMetaData dbm = connection.getMetaData();
            ResultSet tables = dbm.getTables(null, null, table, null);

            if (tables.next())
            {
                return true;
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        return false;
    }

}
