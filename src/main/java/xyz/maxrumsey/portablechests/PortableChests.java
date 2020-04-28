package xyz.maxrumsey.portablechests;

import org.bukkit.plugin.java.JavaPlugin;
import xyz.maxrumsey.portablechests.commands.Chest;
import xyz.maxrumsey.portablechests.commands.Inspect;
import xyz.maxrumsey.portablechests.listeners.InventoryClosed;
import xyz.maxrumsey.portablechests.sqlite.Database;
import xyz.maxrumsey.portablechests.sqlite.SQLite;

import java.util.ArrayList;

public final class PortableChests extends JavaPlugin {
    private Database db;

    public ArrayList<WaitingInventory> waitingInventories = new ArrayList<>();
    public CommandRunner CommandRunner;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        this.getCommand("chest").setExecutor(new Chest(this));
        this.getCommand("inspect").setExecutor(new Inspect(this));

        getServer().getPluginManager().registerEvents(new InventoryClosed(this), this);

        this.db = new SQLite(this);
        this.db.load();
        this.CommandRunner = new CommandRunner(this);
        System.out.println("Plugin has loaded successfully.");

    }

    public Database getDatabase() {
        return this.db;
    }
}
