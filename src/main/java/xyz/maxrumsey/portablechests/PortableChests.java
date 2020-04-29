package xyz.maxrumsey.portablechests;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.maxrumsey.portablechests.commands.Chest;
import xyz.maxrumsey.portablechests.commands.Inspect;
import xyz.maxrumsey.portablechests.listeners.InventoryClosed;
import xyz.maxrumsey.portablechests.sqlite.Database;
import xyz.maxrumsey.portablechests.sqlite.SQLite;

import java.util.ArrayList;
import java.util.logging.Logger;

public final class PortableChests extends JavaPlugin {
    private Database db;

    public ArrayList<WaitingInventory> waitingInventories = new ArrayList<>();
    public CommandRunner CommandRunner;

    @Override
    public void onEnable() {
        // Plugin startup logic

        FileConfiguration config = getConfig();
        Logger logger = getLogger();
        config.options().copyDefaults();
        saveDefaultConfig();

        if (config.contains("ChestSize")) {
            Bukkit.getConsoleSender().sendMessage("[PortableChests] " + ChatColor.RED + "It looks like you are using an old version of the configuration file!");
            logger.warning("If you have recently updated PortableChests, simply delete the config.yaml file in the PortableChests folder and reload to recreate the config file automatically.");
            logger.warning("Caution: Your players will also not be able to access their chest1 and chest2, so consider downgrading to v1.1.1 and asking users to empty their chest!");
        }

        this.getCommand("chest").setExecutor(new Chest(this));
        this.getCommand("inspect").setExecutor(new Inspect(this));

        getServer().getPluginManager().registerEvents(new InventoryClosed(this), this);

        this.db = new SQLite(this);
        this.db.load();
        this.CommandRunner = new CommandRunner(this);

        logger.info("PortableChests has loaded successfully.");

    }

    public Database getDatabase() {
        return this.db;
    }
}
