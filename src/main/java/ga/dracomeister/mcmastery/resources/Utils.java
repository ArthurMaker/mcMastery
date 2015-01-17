package ga.dracomeister.mcmastery.resources;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;

public class Utils {
    public static ItemStack createItem(Material material, int amount, byte data, String name, ArrayList<String> loreList) {
        ItemStack item = new ItemStack(material, amount, data);

        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(name);
        if (loreList != null) {
            itemMeta.setLore(loreList);
        }
        item.setItemMeta(itemMeta);

        return item;
    }

    public static ArrayList<String> createList(String... texts) {
        ArrayList<String> textList = new ArrayList<>();
        Collections.addAll(textList, texts);
        return textList;
    }
}
