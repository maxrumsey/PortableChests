package xyz.maxrumsey.portablechests;

import me.lucko.commodore.Commodore;
import me.lucko.commodore.CommodoreProvider;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.maxrumsey.portablechests.commands.Chest;
import xyz.maxrumsey.portablechests.commands.ChestConfig;
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

    /** Runs on initialisation of plugin.
     */
    @Override
    public void onEnable() {

        // Handles initialisation of config and logger
        FileConfiguration config = getConfig();
        Logger logger = getLogger();

        // Copying default config from resources
        config.options().copyDefaults();
        saveDefaultConfig();
        reloadConfig();

        // Check for previous configuration version
        if (config.contains("ChestSize")) {
            Bukkit.getConsoleSender().sendMessage("[PortableChests] " + ChatColor.RED + "It looks like you are using an old version of the configuration file!");
            logger.warning("If you have recently updated PortableChests, simply delete the config.yaml file in the PortableChests folder and reload to recreate the config file automatically.");
            logger.warning("Caution: Your players will also not be able to access their chest1 and chest2, so consider downgrading to v1.1.1 and asking users to empty their chest!");
        }

        // Registration of commands
        this.getCommand("chest").setExecutor(new Chest(this));
        this.getCommand("inspect").setExecutor(new Inspect(this));
        this.getCommand("chestconfig").setExecutor(new ChestConfig(this));

        // Registration of inventory closed event.
        getServer().getPluginManager().registerEvents(new InventoryClosed(this), this);

        // Initialising database
        this.db = new SQLite(this);
        this.db.load();

        // Initialising command handler
        this.CommandRunner = new CommandRunner(this);

        // Initialising and loading of Brigadier / Commodore
        if (CommodoreProvider.isSupported()) {
            Commodore commodore = CommodoreProvider.getCommodore(this);

            BrigadierSetup.setup(commodore, this);
        }

        logger.info("PortableChests has loaded successfully.");

    }

    /** Method for returning the inuse SQLite database object.
     *
     * @return The SQLite database object
     */
    public Database getDatabase() {
        return this.db;
    }
}
