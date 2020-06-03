package fr.radi3nt.explosiveores;

import fr.radi3nt.explosiveores.commands.ExplosiveOreCommand;
import fr.radi3nt.explosiveores.listeners.OnExplosionEvent;
import fr.radi3nt.explosiveores.listeners.OnOresInRangeListener;
import fr.radi3nt.explosiveores.runnables.Runner;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class MainExplosiveOres extends JavaPlugin {


    public static ArrayList<Location> BlockToExploadLocations = new ArrayList<>();
    public static HashMap<Location, Integer> TimePass = new HashMap<>();
    public static HashMap<Location, Material> BlockType = new HashMap<>();
    public static HashMap<Location, Integer> TimeLeft = new HashMap<>();
    public static HashMap<Location, ArmorStand> Armor1StandMarker = new HashMap<>();
    public static HashMap<Location, ArmorStand> Armor2StandMarker = new HashMap<>();
    public static HashMap<Location, Long> TimeMarker = new HashMap<>();


    //--------------------------------------------------//
    public static final String VERSION = "1.0";
    private final ConsoleCommandSender console = Bukkit.getConsoleSender();
    //--------------------------------------------------//


    @Override
    public void onEnable() {
        console.sendMessage(ChatColor.GOLD + "[ExplosiveOres] " + ChatColor.YELLOW + "Starting up !");
        console.sendMessage(ChatColor.GOLD + "[ExplosiveOres] " + ChatColor.YELLOW + "ExplosiveOres Plugin by " + ChatColor.AQUA + ChatColor.BOLD + "Radi3nt");
        console.sendMessage(ChatColor.GOLD + "[ExplosiveOres] " + ChatColor.YELLOW + "If you have any issues, please report it");

        RegisterEvents();
        console.sendMessage(ChatColor.GOLD + "[ExplosiveOres] " + ChatColor.RED + "Registered Events");
        RegisterCommands();
        console.sendMessage(ChatColor.GOLD + "[ExplosiveOres] " + ChatColor.RED + "Registered Commands");
        RegisterRunnables();
        console.sendMessage(ChatColor.GOLD + "[ExplosiveOres] " + ChatColor.RED + "Registered Runnables");

        getConfig().options().copyDefaults();
        saveDefaultConfig();
        getConfig().set("version", VERSION);


    }

    private void RegisterRunnables() {
        Runner runner = new Runner();
        runner.runTaskTimer(this, 1L, 1L);
    }

    private void RegisterCommands() {
        getCommand("exploseore").setExecutor(new ExplosiveOreCommand());
    }

    private void RegisterEvents() {
        getServer().getPluginManager().registerEvents(new OnOresInRangeListener(), this);
        getServer().getPluginManager().registerEvents(new OnExplosionEvent(), this);
    }

    @Override
    public void onDisable() {
        for (int i = 0; i < BlockToExploadLocations.size(); i++) {
            Location blockToExpload = BlockToExploadLocations.get(i);
            Armor1StandMarker.get(blockToExpload).remove();
            if (Armor2StandMarker.containsKey(blockToExpload)) {
                Armor2StandMarker.get(blockToExpload).remove();
            }


            TimeLeft.remove(blockToExpload);
            TimeMarker.remove(blockToExpload);
            Armor1StandMarker.remove(blockToExpload);
            Armor2StandMarker.remove(blockToExpload);
            BlockToExploadLocations.remove(blockToExpload);
        }
    }
}
