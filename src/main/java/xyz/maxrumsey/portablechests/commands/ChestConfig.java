package xyz.maxrumsey.portablechests.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import xyz.maxrumsey.portablechests.PortableChests;

import java.util.Arrays;

public class ChestConfig implements CommandExecutor {
    PortableChests pluginInst;

    public ChestConfig(PortableChests p) {
        this.pluginInst = p;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        FileConfiguration config = pluginInst.getConfig();

        if (args.length == 0) {
            return false;
        }

        switch (args[0]) {
            case "set":
                if (args[1] == null ||
                    args[2] == null ||
                    args[3] == null) return false;

                String[] valueSlice = Arrays.copyOfRange(args, 3, args.length);
                String values = String.join(" ", valueSlice);
                switch (args[2]) {
                    case "name":
                    case "permission-denied":
                        config.set("Chests." + args[1] + "." + args[2], values);
                        break;
                    case "rows":
                        int rows;

                        try {
                            rows = Integer.parseInt(args[3]);
                            if (rows < 1 || rows > 6) {
                                sender.sendMessage(ChatColor.RED + "Invalid value. The number of rows must be greater than 0 and less than 7.");
                                return true;
                            }
                        } catch (NumberFormatException e) {
                            return false;
                        }
                        config.set("Chests." + args[1] + "." + args[2], rows);
                        break;
                    default:
                        sendHelp(sender);
                        break;
                }

                pluginInst.saveConfig();
                sender.sendMessage("Successfully updated chest: " + args[1] + "!");
                break;
            case "default":
                if (args[1] == null ||
                        args[2] == null) return false;

                valueSlice = Arrays.copyOfRange(args, 2, args.length);
                values = String.join(" ", valueSlice);
                switch (args[1]) {
                    case "name":
                    case "permission-denied":
                        config.set("default." + args[1], values);
                        break;
                    case "rows":
                        int rows;

                        try {
                            rows = Integer.parseInt(args[2]);
                            if (rows < 1 || rows > 6) {
                                sender.sendMessage(ChatColor.RED + "Invalid value. The number of rows must be greater than 0 and less than 7.");
                            }
                        } catch (NumberFormatException e) {
                            return false;
                        }
                        config.set("default." + args[1], rows);
                        break;
                    default:
                        sendHelp(sender);
                        break;
                }

                pluginInst.saveConfig();
                sender.sendMessage("Successfully updated the default configuration.");
                break;
            case "reload":
                pluginInst.reloadConfig();
                sender.sendMessage("Successfully reloaded the configuration file!");
                break;
            default:
                sendHelp(sender);
        }

        return true;
    }
    private void sendHelp(CommandSender sender) {
        sender.sendMessage("" + ChatColor.GREEN + ChatColor.BOLD + "/chestconfig Help");
        sender.sendMessage("-----------------");
        sender.sendMessage(ChatColor.AQUA + "/chestconfig help " + ChatColor.RESET + "- Shows this help message.");
        sender.sendMessage(ChatColor.AQUA + "/chestconfig reload " + ChatColor.RESET + "- Reloads the plugin configuration.");
        sender.sendMessage(ChatColor.AQUA + "/chestconfig set <chest_name> <key> <value> " + ChatColor.RESET + "- Sets a value in the configuration");
    }
}
