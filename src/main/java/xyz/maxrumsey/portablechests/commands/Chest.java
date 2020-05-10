package xyz.maxrumsey.portablechests.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.maxrumsey.portablechests.PortableChests;

/** The /chest command
 */
public class Chest implements CommandExecutor {
    PortableChests pluginInst;

    /** Chest Command Class
     *
     * @param plugin The Plugin Object
     */
    public Chest(PortableChests plugin) {
        this.pluginInst = plugin;
    }

    /** Runs when /chest is executed
     *
     * @param sender The sender of the command
     * @param command The command object
     * @param label The label
     * @param args String Array of Arguments
     * @return !Syntax_Error
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {

            if (args.length == 0) {
                return false;
            }

            String name = args[0];
            Player player = (Player) sender;

            // Permission Check
            if (!player.hasPermission("PortableChests.chest." + name) && !player.isOp() && !player.hasPermission("PortableChests.chest.*")) {
                sender.sendMessage(ChatColor.DARK_RED + pluginInst.getConfig().getString("Chests." + name + ".permission-denied", pluginInst.getConfig().getString("default.permission-denied", "You not have permission to access this chest.")));
                return true;
            }

            // Sends to Command Runner
            pluginInst.CommandRunner.chest(player, player.getUniqueId().toString(), name);

        } else {
            System.out.println("You need to be a player to execute this command.");
        }
        return true;
    }
}
