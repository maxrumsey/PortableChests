package xyz.maxrumsey.portablechests;

import org.bukkit.inventory.Inventory;

public class WaitingInventory {

    public String owner;
    public Inventory inventory;
    public String name;

    public WaitingInventory(String o, Inventory i, String name) {
        this.owner = o;
        this.inventory = i;
        this.name = name;
    }
}
