package ga.dracomeister.mcmastery.resources;

import ga.dracomeister.mcmastery.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

public class AttributesAccess {

    private static final Plugin plugin = Main.getPlugin();
    private static final String pluginDataFileName = "player-data.yml";
    private static int skillLimit = mcMasteryAPI.SKILLS_LIMIT.getData();
    private static FileConfiguration playerData = null;
    private static File playerDataFile = null;

    public static void reloadPlayerData() {
        if (playerDataFile == null) playerDataFile = new File(plugin.getDataFolder(), pluginDataFileName);
        playerData = YamlConfiguration.loadConfiguration(playerDataFile);
        Reader defDataStream = new InputStreamReader(plugin.getResource(pluginDataFileName), StandardCharsets.UTF_8);
        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defDataStream);
        playerData.setDefaults(defConfig);
    }

    public static FileConfiguration getPlayerData() {
        if (playerData == null) reloadPlayerData();
        return playerData;
    }

    public static void savePlayerData() {
        if (playerData == null || playerDataFile == null) return;
        try {
            getPlayerData().save(playerDataFile);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not save player data to " + playerDataFile, e);
        }
    }

    public static void saveDefaultPlayerData() {
        if (playerDataFile == null) playerDataFile = new File(plugin.getDataFolder(), pluginDataFileName);
        if (!playerDataFile.exists()) plugin.saveResource(pluginDataFileName, false);
    }

    public static void checkPlayerData(Player p) {
        String referableName = mcMasteryAPI.PLAYER_NAME.getStringData(p);
        if (referableName == null) {
            mcMasteryAPI.PLAYER_NAME.setData(p, p.getName());
        }
        double Prowess = mcMasteryAPI.PROWESS.getData(p);
        double Fortitude = mcMasteryAPI.FORTITUDE.getData(p);
        double Precision = mcMasteryAPI.PRECISION.getData(p);
        double Agility = mcMasteryAPI.AGILITY.getData(p);

        if (Prowess + Fortitude + Precision + Agility > skillLimit) {
            mcMasteryAPI.PROWESS.setData(p, 0);
            mcMasteryAPI.FORTITUDE.setData(p, 0);
            mcMasteryAPI.PRECISION.setData(p, 0);
            mcMasteryAPI.AGILITY.setData(p, 0);
        }
        savePlayerData();
    }
}