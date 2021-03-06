package ga.dracomeister.mcmastery;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import static ga.dracomeister.mcmastery.resources.Assets.DefaultAssets.SKILLS_LIMIT;
import static ga.dracomeister.mcmastery.resources.Assets.DefaultAssets.SKILLS_STEP;
import static ga.dracomeister.mcmastery.resources.Assets.PlayerAssets.*;
import static ga.dracomeister.mcmastery.resources.AssetsHandler.savePlayerData;
import static ga.dracomeister.mcmastery.resources.Utils.*;
import static org.bukkit.ChatColor.*;
import static org.bukkit.Material.*;

public class GUIHandler implements Listener {

    private static int skillLimit = SKILLS_LIMIT.getData();
    private static int skillStep = SKILLS_STEP.getData();

    public static Inventory SkillsGUI(Player p) {
        checkPlayerData(p);

        int Prowess = PROWESS.getData(p);
        int Fortitude = FORTITUDE.getData(p);
        int Precision = PRECISION.getData(p);
        int Agility = AGILITY.getData(p);

        int limit = skillLimit - (Prowess + Fortitude + Precision + Agility);

        Inventory inv = Bukkit.createInventory(null, 54, DARK_GRAY.toString() + BOLD + limit + " Skill Points");

        inv.setItem(9, createItem(DIAMOND_SWORD, 1, (byte) 0, RED.toString() + BOLD + "Prowess", createList(YELLOW + "Click to reset this skill.")));
        inv.setItem(18, createItem(DIAMOND_CHESTPLATE, 1, (byte) 0, BLUE.toString() + BOLD + "Fortitude", createList(YELLOW + "Click to reset this skill.")));
        inv.setItem(27, createItem(FLINT, 1, (byte) 0, GOLD.toString() + BOLD + "Precision", createList(YELLOW + "Click to reset this skill.")));
        inv.setItem(36, createItem(FEATHER, 1, (byte) 0, LIGHT_PURPLE.toString() + BOLD + "Agility", createList(YELLOW + "Click to reset this skill.")));

        int c;
        for (c = 0; c < 8; c++) {
            inv.setItem(c + 10, createItem(WOOL, (c + 1),
                    DyeColor.GRAY.getData(), RED +
                            "Prowess Level " + (c + 1),
                    createList(YELLOW + "Increase damage by : "
                            + GOLD.toString() + BOLD + ((c + 1) * skillStep) + "%")));
        }
        for (c = 0; c < 8; c++) {
            inv.setItem(c + 19, createItem(WOOL, (c + 1),
                    DyeColor.GRAY.getData(), BLUE +
                            "Fortitude Level " + (c + 1),
                    createList(YELLOW + "Decrease incoming damage by : "
                            + GOLD.toString() + BOLD + ((c + 1) * skillStep) + "%")));
        }
        for (c = 0; c < 8; c++) {
            inv.setItem(c + 28, createItem(WOOL, (c + 1),
                    DyeColor.GRAY.getData(), GOLD +
                            "Precision Level " + (c + 1),
                    createList(YELLOW + "Increase critical chances by : "
                            + GOLD.toString() + BOLD + ((c + 1) * skillStep) + "%")));
        }
        for (c = 0; c < 8; c++) {
            inv.setItem(c + 37, createItem(WOOL, (c + 1),
                    DyeColor.GRAY.getData(), LIGHT_PURPLE +
                            "Agility Level " + (c + 1),
                    createList(YELLOW + "Increase dodging chances by : "
                            + GOLD.toString() + BOLD + ((c + 1) * skillStep) + "%")));
        }

        for (c = 0; c < Prowess; c++) {
            inv.setItem(c + 10, createItem(WOOL, (c + 1),
                    DyeColor.RED.getData(), RED +
                            "Prowess Level " + (c + 1),
                    createList(GREEN + "Power at : "
                            + DARK_GREEN.toString() + BOLD + ((c + 1) * skillStep) + "%")));
        }
        for (c = 0; c < Fortitude; c++) {
            inv.setItem(c + 19, createItem(WOOL, (c + 1),
                    DyeColor.CYAN.getData(), BLUE +
                            "Fortitude Level " + (c + 1),
                    createList(GREEN + "Defense at : "
                            + DARK_GREEN.toString() + BOLD + ((c + 1) * skillStep) + "%")));
        }
        for (c = 0; c < Precision; c++) {
            inv.setItem(c + 28, createItem(WOOL, (c + 1),
                    DyeColor.ORANGE.getData(), GOLD +
                            "Precision Level " + (c + 1),
                    createList(GREEN + "Critical chances at : "
                            + DARK_GREEN.toString() + BOLD + ((c + 1) * skillStep) + "%")));
        }
        for (c = 0; c < Agility; c++) {
            inv.setItem(c + 37, createItem(WOOL, (c + 1),
                    DyeColor.PURPLE.getData(), LIGHT_PURPLE +
                            "Agility Level " + (c + 1),
                    createList(GREEN + "Dodging chances at : "
                            + DARK_GREEN.toString() + BOLD + ((c + 1) * skillStep) + "%")));
        }
        return inv;
    }

    @EventHandler
    public void onPlayerClickInventory(InventoryClickEvent e) {
        if (e.getInventory().getName() == null || e.getCurrentItem() == null) {
            return;
        }
        if (e.getInventory().getName().contains("Skill Points")) {
            if (e.getCurrentItem().getType() == AIR
                    || e.getCurrentItem().getType() == null) {
                return;
            }
            e.setCancelled(true);
            int slot = e.getSlot();
            Player p = (Player) e.getWhoClicked();

            int Prowess = PROWESS.getData(p);
            int Precision = PRECISION.getData(p);
            int Fortitude = FORTITUDE.getData(p);
            int Agility = AGILITY.getData(p);
            int limit = skillLimit - (Prowess + Fortitude + Precision + Agility);

            int entry;
            if (slot >= 10 && slot <= 17) {
                entry = (slot - 10) + 1;
                if (entry > limit + Prowess) {
                    entry = limit + Prowess;
                }
                PROWESS.setData(p, entry);
            }
            if (slot >= 19 && slot <= 26) {
                entry = (slot - 19) + 1;
                if (entry > limit + Fortitude) {
                    entry = limit + Fortitude;
                }
                FORTITUDE.setData(p, entry);
            }
            if (slot >= 28 && slot <= 35) {
                entry = (slot - 28) + 1;
                if (entry > limit + Precision) {
                    entry = limit + Precision;
                }
                PRECISION.setData(p, entry);
            }
            if (slot >= 37 && slot <= 44) {
                entry = (slot - 37) + 1;
                if (entry > limit + Agility) {
                    entry = limit + Agility;
                }
                AGILITY.setData(p, entry);
            }

            if (slot == 9) PROWESS.setData(p, 0);
            if (slot == 18) FORTITUDE.setData(p, 0);
            if (slot == 27) PRECISION.setData(p, 0);
            if (slot == 36) AGILITY.setData(p, 0);

            p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 1, 1);

            savePlayerData();
            p.openInventory(SkillsGUI(p));
        }
    }
}