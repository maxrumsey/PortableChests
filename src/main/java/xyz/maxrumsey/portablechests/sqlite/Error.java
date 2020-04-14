package xyz.maxrumsey.portablechests.sqlite;

import xyz.maxrumsey.portablechests.PortableChests;

import java.util.logging.Level;

public class Error {
    public static void execute(PortableChests plugin, Exception ex){
        plugin.getLogger().log(Level.SEVERE, "Couldn't execute MySQL statement: ", ex);
    }
    public static void close(PortableChests plugin, Exception ex){
        plugin.getLogger().log(Level.SEVERE, "Failed to close MySQL connection: ", ex);
    }
}
