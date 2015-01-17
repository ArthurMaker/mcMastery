package ga.dracomeister.mcmastery;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import static ga.dracomeister.mcmastery.resources.AttributesAccess.saveDefaultPlayerData;
import static ga.dracomeister.mcmastery.resources.AttributesAccess.savePlayerData;

public class Main extends JavaPlugin {
    private static Plugin plugin;

    private static void registerEvents(Plugin plugin, Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
        }
    }

    public static Plugin getPlugin() {
        return plugin;
    }

    public void onEnable() {
        plugin = this;

        saveDefaultPlayerData();
        savePlayerData();

        saveDefaultConfig();
        saveConfig();

        registerEvents(this, new DamageHandler(), new GUIHandler());
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("mcmastery")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "You must be a player to use this command.");
                return true;
            }
            ((Player) sender).openInventory(GUIHandler.SkillsGUI((Player) sender));
        }
        return true;
    }

    public void onDisable() {
        plugin = null;
    }

}