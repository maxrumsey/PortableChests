package xyz.maxrumsey.portablechests.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.maxrumsey.portablechests.PortableChests;

public class Chest implements CommandExecutor {
    PortableChests pluginInst;

    public Chest(PortableChests p) {
        this.pluginInst = p;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {

            if (args.length == 0) {
                return false;
            }

            String name = args[0];
            Player player = (Player) sender;

            if (!player.hasPermission("PortableChests.chest." + name) && !player.isOp() && !player.hasPermission("PortableChests.chest.*")) {
                sender.sendMessage(ChatColor.DARK_RED + "You do not have access to that chest.");
                return true;
            }



            pluginInst.CommandRunner.chest(player, player.getUniqueId().toString(), name);

        } else {
            System.out.println("You need to be a player to execute this command.");
        }
        return true;
    }
}
