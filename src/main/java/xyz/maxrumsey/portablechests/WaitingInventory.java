package xyz.maxrumsey.portablechests;

import org.bukkit.inventory.Inventory;

/** Represents a chest currently open.
 *
 */
public class WaitingInventory {

    public String owner;
    public Inventory inventory;
    public String name;

    /**
     *
     * @param owner The UUID of the owner of the chest/
     * @param inventory The actual inventory object
     * @param name The name of the chest
     */
    public WaitingInventory(String owner, Inventory inventory, String name) {
        this.owner = owner;
        this.inventory = inventory;
        this.name = name;
    }
}
