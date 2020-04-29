# PortableChests
A bukkit/spigot plugin for 1.14+

This plugin gives users the ability to open virtual chests. Everything is configurable, from the size of the chest, how many chests are available to users to who can open each chest.

## Benefits
* Easy to understand yet powerful permissions

The permissions are very simple and easy to setup, with minimal headache. 
The permissions work with your favourite permission client, so it's use and complexity is completely up to you!
* Highly configurable and flexible

Virtually everything can be configured, from the `permission-denied` message or size, for each chest!
* Fast storage via SQLite

Don't resort to having a directory full with hundreds of .yaml files, easily broken by one wrong keystroke! The chest information is stored using the SQLite format.

## Commands:
#### /chest <chest_name>

Permission: PortableChests.chest.<chest_name>

This command opens the chest specified.

#### /inspect <player_name> <chest_name>

Permission: PortableChests.inspect.<chest_name>

This command opens the chest specified from the user specified.

## Permissions:

#### PortableChests.chest.<chest_name>
Allows the user to open the chest specified.

#### PortableChests.chest.*
aliases: `PortableChests.chest`
Allows the user to open all the chests on the server.
**Warning: This effectively allows the user to open unlimited, infinite chests.**

#### PortableChests.inspect.<chest_name>
Allows the user to open the chest specified of any user.

#### PortableChests.inspect.*
alias: `PortableChests.inspect`
Allows the user to open all the chests of any user.
**Warning: This effectively allows the user to open unlimited, infinite chests.**

## Links
[Spigot Listing](https://www.spigotmc.org/resources/portablechests.77408/)

[Bukkit Listing](https://dev.bukkit.org/projects/PortableChests)
