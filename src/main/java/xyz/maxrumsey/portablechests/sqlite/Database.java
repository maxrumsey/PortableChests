package xyz.maxrumsey.portablechests.sqlite;

import xyz.maxrumsey.portablechests.PortableChests;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

public abstract class Database {
    PortableChests plugin;
    Connection connection;
    public String table = "chests";

    public Database(PortableChests instance){
        plugin = instance;
    }

    public abstract Connection getSQLConnection();

    public abstract void load();

    public void initialize(){
        connection = getSQLConnection();
        try{
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM " + table + " WHERE player = \"312\"");
            ResultSet rs = ps.executeQuery();
            close(ps,rs);

        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, "Unable to retrieve connection", ex);
        }
    }

    public String getChest(String uuid, String chestName) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs;

        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("SELECT * FROM " + table + " WHERE (player = ?) AND (name = ?)");
            ps.setString(1, uuid);
            ps.setString(2, chestName);
            rs = ps.executeQuery();

            while(rs.next()) {
                if (rs.getString("player").equalsIgnoreCase(uuid.toLowerCase())) {
                    return rs.getString("contents");
                }
            }
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
            }
        }
        return "NOT_FOUND";
    }
    public void createChest(String uuid, String name) {
        Connection conn = null;
        PreparedStatement ps = null;
        String existingChest = this.getChest(uuid, name);
        try {
            conn = getSQLConnection();
            if (existingChest.equals("NOT_FOUND")) {
                ps = conn.prepareStatement("INSERT INTO " + table + " (name, contents, player) VALUES (?, ?, ?)");
                ps.setString(1, name);
                ps.setString(2, "");

                ps.setString(3, uuid);
                ps.executeUpdate();
            }
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
            }
        }
    }
    public void setChest(String uuid, String chestName, String chestStr) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("UPDATE " + table + " SET contents = ? WHERE (player = ?) AND (name = ?)");

            ps.setString(1, chestStr);
            ps.setString(2, uuid);
            ps.setString(3, chestName);
            ps.executeUpdate();
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
            }
        }
    }

    public void close(PreparedStatement ps,ResultSet rs){
        try {
            if (ps != null)
                ps.close();
            if (rs != null)
                rs.close();
        } catch (SQLException ex) {
            Error.close(plugin, ex);
        }
    }
}