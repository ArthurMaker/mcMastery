package ga.dracomeister.mcmastery;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Random;

import static ga.dracomeister.mcmastery.resources.Assets.DefaultAssets.*;
import static ga.dracomeister.mcmastery.resources.Assets.PlayerAssets.*;
import static ga.dracomeister.mcmastery.resources.Utils.checkPlayerData;

public class DamageHandler implements Listener {

    private static int skillStep = SKILLS_STEP.getData();
    Random random = new Random();

    @EventHandler
    public void onFallDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
            double d = e.getDamage();
            Player v = (Player) e.getEntity();
            checkPlayerData(v);

            double Fortitude = FORTITUDE.getData(v);
            double Agility = AGILITY.getData(v);
            double defenseMultiplier = 1;

            if (Agility * skillStep >= random.nextInt(100)) {
                defenseMultiplier = 0;
                v.sendMessage(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "Landed gracefully.");
            }

            if (defenseMultiplier != 0) defenseMultiplier -= (Fortitude * skillStep) / 100;
            d *= defenseMultiplier;
            //Bukkit.getServer().broadcastMessage("IncomingB " + d + " Mutliplier: " + defenseMultiplier);
            e.setDamage(d);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        Entity v = e.getEntity();
        Entity a = e.getDamager();
        Double d = e.getDamage();

        double Prowess; //Attacker Power
        double Precision; //Attacker Critical Chance
        double Fortitude; //Victim Defense
        double Agility; //Victim Dodge Chance

        double attackMultiplier = 1;
        double defenseMultiplier = 1;

        if (a instanceof Player) {
            checkPlayerData((Player) a);
            Prowess = PROWESS.getData((Player) a);
            Precision = PRECISION.getData((Player) a);
        } else {
            Prowess = MOB_PROWESS.getData();
            Precision = MOB_PRECISION.getData();
        }
        if (v instanceof Player) {
            checkPlayerData((Player) v);
            Fortitude = FORTITUDE.getData((Player) v);
            Agility = AGILITY.getData((Player) v);
        } else {
            Fortitude = MOB_FORTITUDE.getData();
            Agility = MOB_AGILITY.getData();
        }
        if (Precision * skillStep >= random.nextInt(100)) {
            attackMultiplier += 1;
            a.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + "Critical attack on " + v.getName() + "!");
            v.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + a.getName() + " attacked critically on you!");
        }

        attackMultiplier += (Prowess * skillStep) / 100;
        d *= attackMultiplier;
        //Bukkit.getServer().broadcastMessage("OutgoingB " + d + " Mutliplier: " + attackMultiplier);

        if (Agility * skillStep >= random.nextInt(100)) {
            defenseMultiplier = 0;
            a.sendMessage(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + v.getName() + " dodged your attack.");
            v.sendMessage(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "Dodged " + a.getName() + "'s attack.");
        }

        if (defenseMultiplier != 0) defenseMultiplier -= (Fortitude * skillStep) / 100;
        d *= defenseMultiplier;
        //Bukkit.getServer().broadcastMessage("IncomingB " + d + " Mutliplier: " + defenseMultiplier);

        e.setDamage(d);
    }
}