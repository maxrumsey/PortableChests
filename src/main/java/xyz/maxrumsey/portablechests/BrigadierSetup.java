package xyz.maxrumsey.portablechests;

import com.mojang.brigadier.tree.LiteralCommandNode;
import me.lucko.commodore.Commodore;
import me.lucko.commodore.file.CommodoreFileFormat;
import org.bukkit.command.PluginCommand;

import java.io.IOException;

public class BrigadierSetup {
    static public void setup(Commodore commodore, PortableChests plugin) {
        String[] commands = new String[]{ "chest", "inspect", "chestconfig" };
        for (String commandStr: commands) {
            command(plugin, commandStr, commodore);
        }
    }
    private static void command(PortableChests plugin, String name, Commodore commodore) {
        try {
            PluginCommand bukkitCommand = plugin.getCommand(name);
            LiteralCommandNode<?> command = CommodoreFileFormat.parse(plugin.getResource("commodore/" + name + ".commodore"));
            commodore.register(bukkitCommand, command);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
