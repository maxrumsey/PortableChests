package xyz.maxrumsey.portablechests.sqlite;

import xyz.maxrumsey.portablechests.PortableChests;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

/** Handles connection to the DB
 *
 */
public class SQLite extends Database{
    String dbname;
    public SQLite(PortableChests instance){
        super(instance);
        dbname = plugin.getConfig().getString("SQLite.Filename", "database");
    }

    public String SQLiteCreateInventoryUserTable = "CREATE TABLE IF NOT EXISTS chests (" +
            "`player` varchar(36) NOT NULL," +
            "`name` text NOT NULL," +
            "`contents` text NOT NULL" +
            ");";


    // SQL creation stuff, You can leave the blow stuff untouched.
    public Connection getSQLConnection() {
        File dataFolder = new File(plugin.getDataFolder(), dbname+".db");

        // ToDo: Remove this check, just replace with check of the output of createNewFile()
        if (!dataFolder.exists()){
            try {
                dataFolder.createNewFile();

            } catch (IOException e) {
                plugin.getLogger().log(Level.SEVERE, "File write error: "+dbname+".db");
            }
        }
        try {
            if(connection!=null&&!connection.isClosed()){
                return connection;
            }
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + dataFolder);
            return connection;
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE,"SQLite exception on initialize", ex);
        } catch (ClassNotFoundException ex) {
            plugin.getLogger().log(Level.SEVERE, "You need the SQLite JBDC library. Google it. Put it in /lib folder.");
        }
        return null;
    }

    public void load() {
        connection = getSQLConnection();
        try {
            Statement s = connection.createStatement();
            s.executeUpdate(SQLiteCreateInventoryUserTable);
            s.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        initialize();
    }
}