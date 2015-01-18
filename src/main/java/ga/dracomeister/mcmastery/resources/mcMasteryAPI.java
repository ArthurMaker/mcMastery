package ga.dracomeister.mcmastery.resources;

import ga.dracomeister.mcmastery.Main;
import org.bukkit.entity.Player;

import static ga.dracomeister.mcmastery.resources.AttributesAccess.getPlayerData;

public enum mcMasteryAPI {

    SKILLS_LIMIT("Defaults.Skill-Limit"),
    SKILLS_STEP("Defaults.Skill-Step"),

    MOB_PROWESS("Mob-Data.Prowess"),
    MOB_FORTITUDE("Mob-Data.Fortitude"),
    MOB_PRECISION("Mob-Data.Precision"),
    MOB_AGILITY("Mob-Data.Agility"),

    PLAYER_NAME("Players.UUIDs.%s.Name"),
    PROWESS("Players.UUIDs.%s.Prowess"),
    FORTITUDE("Players.UUIDs.%s.Fortitude"),
    PRECISION("Players.UUIDs.%s.Precision"),
    AGILITY("Players.UUIDs.%s.Agility");

    private final String path;

    private mcMasteryAPI(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public int getData() {
        String dataPath = path;
        return Main.getPlugin().getConfig().getInt(dataPath);
    }

    public void setData(Object obj) {
        String dataPath = path;
        Main.getPlugin().getConfig().set(dataPath, obj);
    }

    public int getData(Player p) {
        String dataPath = path;
        return getPlayerData().getInt(String.format(dataPath, p.getUniqueId()));
    }

    public String getStringData(Player p) {
        String dataPath = path;
        return getPlayerData().getString(String.format(dataPath, p.getUniqueId()));
    }

    public void setData(Player p, Object obj) {
        String dataPath = path;
        getPlayerData().set(String.format(dataPath, p.getUniqueId()), obj);
    }
}
