package xyz.maxrumsey.portablechests.sqlite;

public class Errors {
    public static String sqlConnectionExecute(){
        return "Couldn't execute SQL statement: ";
    }
    public static String sqlConnectionClose(){
        return "Failed to close SQL connection: ";
    }
    public static String noSQLConnection(){
        return "Unable to retrieve SQL connection: ";
    }
    public static String noTableFound(){
        return "Database Error: No Table Found";
    }
}