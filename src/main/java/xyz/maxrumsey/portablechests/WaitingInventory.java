package xyz.maxrumsey.portablechests;

import org.bukkit.inventory.Inventory;

public class WaitingInventory {

    public String owner;
    public Inventory inventory;
    public Integer id;

    public WaitingInventory(String o, Inventory i, Integer id) {
        this.owner = o;
        this.inventory = i;
        this.id = id;
    }
}
