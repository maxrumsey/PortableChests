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

    public void chest(Player sender, String target, Integer chestId) {
        Database database = pluginInst.getDatabase();

        database.createChests(target);

        String title;
        if (chestId == 1) {
            title = "Chest One";
        } else {
            title = "Chest Two";
        }

        String pre_inventory = database.getChest(target, chestId);
        Integer size = pluginInst.getConfig().getInt("ChestSize");
        Inventory vault = Bukkit.createInventory(sender, size, title);

        InventorySerializer serializer = new InventorySerializer();
        if (pre_inventory == "" || pre_inventory == null || pre_inventory.isEmpty() || pre_inventory == "NOT_FOUND") {
        } else {

            try {
                vault = serializer.fromBase64(pre_inventory, title);
            } catch (IOException e) {
                e.printStackTrace();
                sender.sendMessage(ChatColor.RED + "We failed to parse your chest. Please ask your server administrator to check the logs.");
                return;
            }
        }

        sender.openInventory(vault);

        WaitingInventory waitingInventory = new WaitingInventory(target, vault, chestId);

        pluginInst.waitingInventories.add(waitingInventory);
    }
}
