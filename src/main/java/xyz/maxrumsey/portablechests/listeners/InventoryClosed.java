package xyz.maxrumsey.portablechests.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import xyz.maxrumsey.portablechests.InventorySerializer;
import xyz.maxrumsey.portablechests.PortableChests;
import xyz.maxrumsey.portablechests.WaitingInventory;
import xyz.maxrumsey.portablechests.sqlite.Database;

/** Handles the closing of inventories.
 */
public class InventoryClosed implements Listener {

    private final PortableChests pluginInstance;

    /** Inventory Closure Event Class
     *
     * @param plugin The Plugin Object
     */
    public InventoryClosed(PortableChests plugin) {
        this.pluginInstance = plugin;
    }

    /** Runs every time a chest is closed.
     *
     * @param e The Inventory Closed Event
     */
    @EventHandler
    void onInventoryClosed(InventoryCloseEvent e) {
        Inventory inventory = e.getInventory();
        // Looping through inventories in pool
        for (WaitingInventory w: pluginInstance.waitingInventories) {
            // Checking to see if inventory being closed is member of pool
            if (inventory.equals(w.inventory)) {

                // If is, serializes chest and saves
                String inventoryStr = InventorySerializer.toBase64(e.getInventory());
                Database database = pluginInstance.getDatabase();
                database.setChest(w.owner, w.name, inventoryStr);

                while (pluginInstance.waitingInventories.contains(w)) {
                    pluginInstance.waitingInventories.remove(w);
                }
                break;
            }

        }

    }


}
