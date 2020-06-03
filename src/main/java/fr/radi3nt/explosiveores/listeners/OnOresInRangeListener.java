package fr.radi3nt.explosiveores.listeners;

import fr.radi3nt.explosiveores.MainExplosiveOres;
import fr.radi3nt.explosiveores.events.OnOresInRange;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

import static fr.radi3nt.explosiveores.MainExplosiveOres.*;

public class OnOresInRangeListener implements Listener {


    @EventHandler
    public void OnOresInRange(OnOresInRange e) {


        List<Material> BlocksToExpload = (List<Material>) MainExplosiveOres.getPlugin(MainExplosiveOres.class).getConfig().getList("blocks");
        Integer Radius = MainExplosiveOres.getPlugin(MainExplosiveOres.class).getConfig().getInt("radius");
        String Prefix = ChatColor.translateAlternateColorCodes('&', MainExplosiveOres.getPlugin(MainExplosiveOres.class).getConfig().getString("prefix") + ChatColor.RESET);




        Location blockLocation = e.getOreLocation();


        if (!BlockToExploadLocations.contains(blockLocation)) {
            BlockToExploadLocations.add(blockLocation);
            int Time = MainExplosiveOres.getPlugin(MainExplosiveOres.class).getConfig().getInt("Blocks." + blockLocation.getBlock().getType().toString() + ".time");


            Location above = new Location(blockLocation.getWorld(), blockLocation.getBlockX(), blockLocation.getBlockY() + 1, blockLocation.getBlockZ());
            Location under = new Location(blockLocation.getWorld(), blockLocation.getBlockX(), blockLocation.getBlockY() - 1, blockLocation.getBlockZ());
            Location left = new Location(blockLocation.getWorld(), blockLocation.getBlockX() - 1, blockLocation.getBlockY(), blockLocation.getBlockZ());
            Location right = new Location(blockLocation.getWorld(), blockLocation.getBlockX() + 1, blockLocation.getBlockY(), blockLocation.getBlockZ());
            Location behind = new Location(blockLocation.getWorld(), blockLocation.getBlockX(), blockLocation.getBlockY(), blockLocation.getBlockZ() - 1);
            Location front = new Location(blockLocation.getWorld(), blockLocation.getBlockX(), blockLocation.getBlockY(), blockLocation.getBlockZ() + 1);
            if (above.getBlock().getType().equals(Material.AIR)) {
                PlaceTimer(above, blockLocation, Time, "above");
            } else if (front.getBlock().getType().equals(Material.AIR)) {
                PlaceTimer(front, blockLocation, Time, "front");
            } else if (left.getBlock().getType().equals(Material.AIR)) {
                PlaceTimer(left, blockLocation, Time, "left");
            } else if (right.getBlock().getType().equals(Material.AIR)) {
                PlaceTimer(right, blockLocation, Time, "right");
            } else if (behind.getBlock().getType().equals(Material.AIR)) {
                PlaceTimer(behind, blockLocation, Time, "under");
            } else if (under.getBlock().getType().equals(Material.AIR)) {
                PlaceTimer(under, blockLocation, Time, "behind");
            }
            TimeLeft.put(blockLocation, Time);
            TimeMarker.put(blockLocation, System.currentTimeMillis());
            BlockType.put(blockLocation, blockLocation.getBlock().getType());
            TimePass.put(blockLocation, 100000);
        }
    }


    public void PlaceTimer(Location armorLocation, Location blockLocation, int time, String facing) {
        Location placearmorloc = new Location(armorLocation.getWorld(), armorLocation.getBlockX() + 0.5, armorLocation.getBlockY() - 1.75, armorLocation.getBlockZ() + 0.5);
        ArmorStand timer = (ArmorStand) armorLocation.getWorld().spawnEntity(placearmorloc, EntityType.ARMOR_STAND);
        timer.setVisible(false);
        timer.setGravity(false);
        timer.setCollidable(false);
        timer.setInvulnerable(true);
        int heures = (time / 3600);
        int minutes = ((time - (time / 3600) * 3600) / 60);
        int seconds = time - (heures * 3600 + minutes * 60);

        timer.setCustomName(heures + ":" + minutes + ":" + seconds);
        timer.setCustomNameVisible(true);
        Armor1StandMarker.put(blockLocation, timer);


        if (facing.equals("under")) {

            placearmorloc.subtract(0, 0.25, 0);
            ArmorStand arrow = (ArmorStand) armorLocation.getWorld().spawnEntity(placearmorloc, EntityType.ARMOR_STAND);
            arrow.setVisible(false);
            arrow.setGravity(false);
            arrow.setCollidable(false);
            arrow.setInvulnerable(true);
            arrow.setCustomNameVisible(true);
            arrow.setCustomName("/\\");
            Armor2StandMarker.put(blockLocation, arrow);
        }
    }


}
