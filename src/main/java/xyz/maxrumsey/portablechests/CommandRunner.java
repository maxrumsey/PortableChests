package xyz.maxrumsey.portablechests;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import xyz.maxrumsey.portablechests.sqlite.Database;

import java.io.IOException;

public class CommandRunner {
    PortableChests pluginInst;

    public CommandRunner(PortableChests plugin) {
        this.pluginInst = plugin;
    }

    /*
     * ToDo: Add permission check
     * ToDo: Add size check / config.
     */
    public void chest(Player sender, String target, String chestName) {
        Database database = pluginInst.getDatabase();

        database.createChest(target, chestName);

        String title = "Chest: " + chestName;

        String pre_inventory = database.getChest(target, chestName);
        int size = pluginInst.getConfig().getInt("ChestSize");
        Inventory vault = Bukkit.createInventory(sender, size, title);

        if (!(pre_inventory.isEmpty() || pre_inventory.equals("NOT_FOUND"))) {
            try {
                vault = InventorySerializer.fromBase64(pre_inventory, title);
            } catch (IOException e) {
                e.printStackTrace();
                sender.sendMessage(ChatColor.RED + "We failed to parse your chest. Please ask your server administrator to check the logs for errors.");
                return;
            }
        }

        sender.openInventory(vault);

        WaitingInventory waitingInventory = new WaitingInventory(target, vault, chestName);

        pluginInst.waitingInventories.add(waitingInventory);
    }
}
