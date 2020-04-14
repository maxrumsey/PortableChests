package xyz.maxrumsey.portablechests.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import xyz.maxrumsey.portablechests.InventorySerializer;
import xyz.maxrumsey.portablechests.PortableChests;
import xyz.maxrumsey.portablechests.WaitingInventory;
import xyz.maxrumsey.portablechests.sqlite.Database;

public class InventoryClosed implements Listener {

    private final PortableChests pluginInstance;

    public InventoryClosed(PortableChests p) {
        this.pluginInstance = p;
    }

    @EventHandler
    void onInventoryClosed(InventoryCloseEvent e) {
        Inventory inventory = e.getInventory();
        for (WaitingInventory w: pluginInstance.waitingInventories) {
            if (inventory.equals(w.inventory)) {
                String inventoryStr = InventorySerializer.toBase64(e.getInventory());
                Database database = pluginInstance.getDatabase();
                database.setChest(w.owner, w.id, inventoryStr);
                while (pluginInstance.waitingInventories.contains(w)) {
                    pluginInstance.waitingInventories.remove(w);
                }
                break;
            }
        }

    }


}
