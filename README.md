# PortableChests
A bukkit/spigot plugin.

This plugin gives users the ability to open virtual chests. Everything is configurable, from the size of the chest, how many chests are available and who can open each chest.

## Benefits
* Easy to understand yet powerful permissions

The permissions are very simple and easy to set up, with minimal headache. 
The permissions work with your favourite permission client, so it's use and complexity is completely up to you!
* Highly configurable and flexible

Virtually everything can be configured, from the `permission-denied` message or chest-size, for each chest!
* Fast storage via SQLite

Don't resort to having a directory full with hundreds of .yaml files, easily broken by one wrong keystroke! The chest information is stored using the SQLite format.

* Brigadier Support

Brigadier autocompletes commands, saving you from pulling up the command reference, overall making the plugin much easier to use and more efficient.

## Setup
1. Download the .jar file either from the [releases page](https://github.com/maxrumsey/PortableChests/releases) or from the [Spigot Listing](https://www.spigotmc.org/resources/portablechests.77408/).
2. Place the .jar file into your `/plugins/` directory. 
3. Restart the server (or `/reload` it [not recommended])
4. To allow a user to access a chest, simply give them the permission `PortableChests.chest.<chest_name>`. <br />
(Make sure to replace `<chest_name>` with the desired name of your chest).
### Optional:
1. Change the configuration of the chest with the `/chestconfig` command. (See below:)
2. Change the default configuration with the `/chestconfig` command.
## Commands:
#### /chest <chest_name>

Permission: PortableChests.chest.<chest_name>

This command opens the chest specified.

Example: `/chest vault4`
#### /inspect <player_name> <chest_name>

Permission: PortableChests.inspect.<chest_name>

This command opens the chest specified from the user specified.

Example: `/inspect ExiFlame vault4`
#### /chestconfig

Permission: PortableChests.admin

This command allows you to edit the configuration for various chests, as well as the default configuration.
##### Changing the Chest Configuration
To change the chest configuration, execute the following command: `/chestconfig set <chest_name> <key> <value>`.

###### Example Keys and Values:
|Key|Value|Example|
|---|---|---|
|rows|The amount of rows the chest should have|3|
|name|The name the chest should have when opened|Top Secret Vault|
|permission-denied|The message sent when a user tries to open the chest without permission.|No access!|

* Example: `/chestconfig set vault4 rows 6`
* Example: `/chestconfig set vault4 name Vault Number 4`
* Example: `/chestconfig set vault4 permission-denied You can't access vault number 4. Contact an admin with /adminhelp!`
##### Reloading a changed configuration file:
This is only to be used when you change the config.yml file. You do not need to execute this command when using `/chestconfig set` or, `/chestconfig default`.
* Example: `/chestconfig reload`
##### Changing the default configuration
This changes the default configuration for chests, when they don't have a pre-defined configuration.
######Defaults:
|Key|Default Value|
|---|---|
|rows|5|
|name|Portable Chest|
|permission-denied|Sorry, you do not have permission to access this chest.|
* Example: `/chestconfig default rows 2`
* Example: `/chestconfig default name The Vault`
* Example: `/chestconfig default permission-denied No access!`
##### Help
Shows a help message.
* Example: `/chestconfig help`

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

**Warning: This effectively allows the user to open unlimited, infinite chests, of any user.**

#### PortableChests.admin
Allows the user to edit the plugin configuration.

## Links
[Spigot Listing](https://www.spigotmc.org/resources/portablechests.77408/)

[Bukkit Listing](https://dev.bukkit.org/projects/PortableChests)
