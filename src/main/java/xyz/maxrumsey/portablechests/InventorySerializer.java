package xyz.maxrumsey.portablechests;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;

/** Class responsible for serialisation of Inventory to and from Base64 encoded String.
 */
public class InventorySerializer {

    /** Serializes inventory into Base64
     *
     * @param inventory The inventory object
     * @return Returns a base64 encoded string of the Inventory.
     * @throws IllegalStateException
     */
    public static String toBase64(Inventory inventory) throws IllegalStateException {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            // Write the size of the inventory
            dataOutput.writeInt(inventory.getSize());
            // Save every element in the list
            for (int i = 0; i < inventory.getSize(); i++) {
                dataOutput.writeObject(inventory.getItem(i));
            }

            // Serialize that array
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException("Unable to save item stacks.", e);
        }
    }

    /** Handles deserialization of the base64 string
     *
     * @param data The Base64 encoded string.
     * @param title The name of the chest, visible in the GUI.
     * @param size The number of columns present in the chest
     * @return Returns the inventory object
     * @throws IOException
     */
    public static Inventory fromBase64(String data, String title, Integer size) throws IOException {
        try {

            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            dataInput.readInt();
            Inventory inventory = Bukkit.getServer().createInventory(null, size, title);

            // Read the serialized inventory
            for (int i = 0; i < inventory.getSize(); i++) {
                try {
                    inventory.setItem(i, (ItemStack) dataInput.readObject());
                } catch (EOFException e) {
                    break;
                }
            }
            dataInput.close();
            return inventory;
        } catch (ClassNotFoundException e) {
            throw new IOException("Unable to decode class type.", e);
        }
    }
}
