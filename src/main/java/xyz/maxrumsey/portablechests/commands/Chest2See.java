package xyz.maxrumsey.portablechests.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.maxrumsey.portablechests.PortableChests;

public class Chest2See implements CommandExecutor {
    PortableChests pluginInst;

    public Chest2See(PortableChests p) {
        this.pluginInst = p;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {

            Player player = (Player) sender;

            if (!player.hasPermission("PortableChests.admin")) {
                sender.sendMessage(ChatColor.DARK_RED + "You do not have access to that command.");
                return true;
            }
            String uuid;

            if (args.length == 0) {
                return false;
            }
            Player target = Bukkit.getPlayer(args[0]);

            if (target instanceof Player) {
                uuid = target.getUniqueId().toString();
            } else {
                sender.sendMessage(ChatColor.RED + "It doesn't look like this player is online. Looking at offline players...");
                OfflinePlayer op = Bukkit.getOfflinePlayer(args[0]);
                if (op.hasPlayedBefore()) {
                    uuid = op.getUniqueId().toString();
                } else {
                    sender.sendMessage(ChatColor.RED + "That player has never logged onto the server before. Are you sure you entered their name correctly? It is case sensitive.");
                    return true;
                }
            }

            pluginInst.CommandRunner.chest(player, uuid, 2);
        } else {
            System.out.println("You need to be a player to execute this command.");
        }
        return true;
    }
}
