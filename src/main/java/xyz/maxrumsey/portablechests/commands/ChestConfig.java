package xyz.maxrumsey.portablechests.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import xyz.maxrumsey.portablechests.PortableChests;

public class ChestConfig implements CommandExecutor {
    PortableChests pluginInst;

    public ChestConfig(PortableChests p) {
        this.pluginInst = p;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0) {
            return false;
        }

        switch (args[0]) {
            default:
                sender.sendMessage("" + ChatColor.GREEN + ChatColor.BOLD + "/chestconfig Help");
                sender.sendMessage("-----------------");
                sender.sendMessage(ChatColor.AQUA + "/chestconfig help " + ChatColor.RESET + "- Shows this help message.");
        }

        return true;
    }
}
