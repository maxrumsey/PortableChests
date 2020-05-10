package xyz.maxrumsey.portablechests;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import xyz.maxrumsey.portablechests.sqlite.Database;

import java.io.IOException;

/** This class coordinates the deserialization and opening of the chest.
 */
public class CommandRunner {
    PortableChests pluginInst;

    /** Initialises class with plugin.
     *
     * @param plugin Plugin Object
     */
    public CommandRunner(PortableChests plugin) {
        this.pluginInst = plugin;
    }

    /** Handles every the opening and deserialization of a chest
     *
     * @param sender The user who executed the command
     * @param target The user the command is targetting
     * @param chestName The name of the chest, as found in the DB
     */
    public void chest(Player sender, String target, String chestName) {
        Database database = pluginInst.getDatabase();

        // Creates chest in DB if not already present
        database.createChest(target, chestName);

        // Gets title from chest level config, then default config and finally project default if all other are not present.
        String title = pluginInst.getConfig().getString("Chests." + chestName + ".name", pluginInst.getConfig().getString("default.name", "Chest"));

        String pre_inventory = database.getChest(target, chestName);

        int size = pluginInst.getConfig().getInt("Chests." + chestName + ".rows", pluginInst.getConfig().getInt("default.size", 5)) * 9;


        Inventory vault = Bukkit.createInventory(sender, size, title);

        // Handles deserialization of chest
        if (!(pre_inventory.isEmpty() || pre_inventory.equals("NOT_FOUND"))) {
            try {
                vault = InventorySerializer.fromBase64(pre_inventory, title, size);
            } catch (IOException e) {
                e.printStackTrace();
                sender.sendMessage(ChatColor.RED + "We failed to parse your chest. Please ask your server administrator to check the logs for errors.");
                return;
            }
        }

        sender.openInventory(vault);

        // Adds chest to open inventory pool.
        WaitingInventory waitingInventory = new WaitingInventory(target, vault, chestName);

        pluginInst.waitingInventories.add(waitingInventory);
    }
}
