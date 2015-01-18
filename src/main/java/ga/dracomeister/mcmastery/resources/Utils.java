package ga.dracomeister.mcmastery.resources;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;

import static ga.dracomeister.mcmastery.resources.Assets.DefaultAssets.SKILLS_LIMIT;
import static ga.dracomeister.mcmastery.resources.Assets.PlayerAssets.*;
import static ga.dracomeister.mcmastery.resources.AssetsHandler.savePlayerData;

public class Utils {

    private static int skillLimit = SKILLS_LIMIT.getData();
    
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

    public static void checkPlayerData(Player p) {
        String referableName = PLAYER_NAME.getStringData(p);
        if (referableName == null) {
            PLAYER_NAME.setData(p, p.getName());
        }
        double Prowess = PROWESS.getData(p);
        double Fortitude = FORTITUDE.getData(p);
        double Precision = PRECISION.getData(p);
        double Agility = AGILITY.getData(p);

        if (Prowess + Fortitude + Precision + Agility > skillLimit) {
            PROWESS.setData(p, 0);
            FORTITUDE.setData(p, 0);
            PRECISION.setData(p, 0);
            AGILITY.setData(p, 0);
        }
        savePlayerData();
    }

//    public enum ChestRow {
//        ONE(0, 8),
//        TWO(9, 17),
//        THREE(18, 26),
//        FOUR(27, 35),
//        FIVE(36, 44),
//        SIX(45, 53);
//
//        private final int start;
//        private final int end;
//
//        private ChestRow(int start, int end) {
//            this.start = start;
//            this.end = end;
//        }
//
//        public int getStart() {
//            return start;
//        }
//
//        public int getEnd() {
//            return end;
//        }
//    }
    
}
