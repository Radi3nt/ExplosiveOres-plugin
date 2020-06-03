package fr.radi3nt.explosiveores.runnables;

import fr.radi3nt.explosiveores.MainExplosiveOres;
import fr.radi3nt.explosiveores.events.OnOresInRange;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import static fr.radi3nt.explosiveores.MainExplosiveOres.*;
import static javafx.scene.input.KeyCode.L;

public class Runner extends BukkitRunnable {
    @Override
    public void run() {

        List<String> BlocksToExpload = new ArrayList<>();
        Integer range = MainExplosiveOres.getPlugin(MainExplosiveOres.class).getConfig().getInt("range");
        Boolean activated = MainExplosiveOres.getPlugin(MainExplosiveOres.class).getConfig().getBoolean("activated");



        for (String item : MainExplosiveOres.getPlugin(MainExplosiveOres.class).getConfig().getConfigurationSection("Blocks").getKeys(false)) {
            BlocksToExpload.add(item);
        }

        //CHECK FOR BLOCKS
        if (activated) {
            List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
            for (Player player : players) {
                ArrayList<Location> locations = generateSphere(player.getLocation(), range);
                for (Location location : locations) {
                    if (BlocksToExpload.contains(location.getBlock().getType().toString())) {
                        Location playerloc = player.getLocation();
                        ArrayList<Block> blocksOnWayLegs = blocksFromTwoPoints(playerloc, location);
                        playerloc.add(0, 1, 0);
                        ArrayList<Block> blocksOnWayHead = blocksFromTwoPoints(playerloc, location);
                        playerloc.add(0, 1, 0);
                        ArrayList<Block> blocksOnWayAboveHead = blocksFromTwoPoints(playerloc, location);
                        playerloc.add(-1, -1, 0);
                        ArrayList<Block> blocksOnWayLeftHead = blocksFromTwoPoints(playerloc, location);
                        playerloc.add(2, 0, 0);
                        ArrayList<Block> blocksOnWayRightHead = blocksFromTwoPoints(playerloc, location);
                        playerloc.add(-2, -1, 0);
                        ArrayList<Block> blocksOnWayLeftLegs = blocksFromTwoPoints(playerloc, location);
                        playerloc.add(2, 0, 0);
                        ArrayList<Block> blocksOnWayRightLegs = blocksFromTwoPoints(playerloc, location);
                        playerloc.add(-1, 0, 1);
                        ArrayList<Block> blocksOnWayLeftZLegs = blocksFromTwoPoints(playerloc, location);
                        playerloc.add(0, 0, -2);
                        ArrayList<Block> blocksOnWayRightZLegs = blocksFromTwoPoints(playerloc, location);
                        playerloc.add(0, 1, 2);
                        ArrayList<Block> blocksOnWayLeftZHead = blocksFromTwoPoints(playerloc, location);
                        playerloc.add(0, 0, -2);
                        ArrayList<Block> blocksOnWayRightZHead = blocksFromTwoPoints(playerloc, location);


                        if (hasBlockOnWay(blocksOnWayLegs, location.getBlock()) || hasBlockOnWay(blocksOnWayHead, location.getBlock()) || hasBlockOnWay(blocksOnWayAboveHead, location.getBlock()) || hasBlockOnWay(blocksOnWayLeftHead, location.getBlock()) || hasBlockOnWay(blocksOnWayRightHead, location.getBlock()) || hasBlockOnWay(blocksOnWayRightLegs, location.getBlock()) || hasBlockOnWay(blocksOnWayLeftLegs, location.getBlock()) || hasBlockOnWay(blocksOnWayLeftZLegs, location.getBlock()) || hasBlockOnWay(blocksOnWayRightZLegs, location.getBlock()) || hasBlockOnWay(blocksOnWayRightZHead, location.getBlock()) || hasBlockOnWay(blocksOnWayLeftZHead, location.getBlock())) {
                            Bukkit.getServer().getPluginManager().callEvent(new OnOresInRange(location));
                        }
                    }
                }
            }
        }

        //TIMER
        List<Location> BlockToExploadLocationsCopy = new CopyOnWriteArrayList<Location>();
        BlockToExploadLocationsCopy.addAll(BlockToExploadLocations);
        for (Location blockToExpload : BlockToExploadLocationsCopy) {


            int secondes = TimeLeft.get(blockToExpload);
            long timeleft = (((TimeMarker.get(blockToExpload) / 1000) + secondes) - (System.currentTimeMillis() / 1000));

            int heures = (int) (timeleft / 3600);
            int minutes = (int) ((timeleft - (timeleft / 3600) * 3600) / 60);
            int seconds = (int) (timeleft - (heures * 3600 + minutes * 60));
            if (Armor1StandMarker.containsKey(blockToExpload)) {
                Armor1StandMarker.get(blockToExpload).setCustomName(heures + ":" + minutes + ":" + seconds);
            }

            if (!blockToExpload.getBlock().getType().equals(BlockType.get(blockToExpload))) {
                if (Armor1StandMarker.containsKey(blockToExpload)) {
                    Armor1StandMarker.get(blockToExpload).remove();
                }
                if (Armor2StandMarker.containsKey(blockToExpload)) {
                    Armor2StandMarker.get(blockToExpload).remove();
                }



                TimeLeft.remove(blockToExpload);
                TimeMarker.remove(blockToExpload);
                Armor1StandMarker.remove(blockToExpload);
                Armor2StandMarker.remove(blockToExpload);
                BlockToExploadLocations.remove(blockToExpload);
                TimePass.remove(blockToExpload);
            }
            if (timeleft==3 && TimePass.get(blockToExpload)>=3) {
                blockToExpload.getWorld().playSound(blockToExpload, Sound.ENTITY_CREEPER_PRIMED, 100.0F, 0.50F);
                TimePass.put(blockToExpload, 2);
            }
            if (timeleft==2 && TimePass.get(blockToExpload)>=2) {
                blockToExpload.getWorld().playSound(blockToExpload, Sound.BLOCK_NOTE_BLOCK_HAT, 100.0F, 1F);
                TimePass.put(blockToExpload, 1);
            }
            if (timeleft==1 && TimePass.get(blockToExpload)>=1) {
                blockToExpload.getWorld().playSound(blockToExpload, Sound.BLOCK_NOTE_BLOCK_HAT, 100.0F, 1F);
                TimePass.put(blockToExpload, 0);
            }


            if (timeleft<=0) {
                if (Armor1StandMarker.containsKey(blockToExpload)) {
                    Armor1StandMarker.get(blockToExpload).remove();
                }
                if (Armor2StandMarker.containsKey(blockToExpload)) {
                    Armor2StandMarker.get(blockToExpload).remove();
                }



                TimeLeft.remove(blockToExpload);
                TimeMarker.remove(blockToExpload);
                Armor1StandMarker.remove(blockToExpload);
                Armor2StandMarker.remove(blockToExpload);
                TimePass.remove(blockToExpload);


                float force = (float) MainExplosiveOres.getPlugin(MainExplosiveOres.class).getConfig().getDouble("Blocks." + blockToExpload.getBlock().getType().toString() + ".force");
                blockToExpload.getWorld().createExplosion(blockToExpload, force);

                blockToExpload.getBlock().setType(Material.AIR);
                BlockToExploadLocations.remove(blockToExpload);

            }
        }
    }



    public static boolean hasBlockOnWay(ArrayList<Block> blocksOnWay, Block block) {
        for (Block value : blocksOnWay) {
            if (!value.getType().isAir() && !value.getType().equals(block.getType())) {
                return false;
            }
        }
        return true;
    }




    public static ArrayList<Block> blocksFromTwoPoints(Location loc1, Location loc2)
    {
        ArrayList<Block> blocks = new ArrayList();

        int topBlockX = (loc1.getBlockX() < loc2.getBlockX() ? loc2.getBlockX() : loc1.getBlockX());
        int bottomBlockX = (loc1.getBlockX() > loc2.getBlockX() ? loc2.getBlockX() : loc1.getBlockX());

        int topBlockY = (loc1.getBlockY() < loc2.getBlockY() ? loc2.getBlockY() : loc1.getBlockY());
        int bottomBlockY = (loc1.getBlockY() > loc2.getBlockY() ? loc2.getBlockY() : loc1.getBlockY());

        int topBlockZ = (loc1.getBlockZ() < loc2.getBlockZ() ? loc2.getBlockZ() : loc1.getBlockZ());
        int bottomBlockZ = (loc1.getBlockZ() > loc2.getBlockZ() ? loc2.getBlockZ() : loc1.getBlockZ());

        for(int x = bottomBlockX; x <= topBlockX; x++)
        {
            for(int z = bottomBlockZ; z <= topBlockZ; z++)
            {
                for(int y = bottomBlockY; y <= topBlockY; y++)
                {
                    Block block = loc1.getWorld().getBlockAt(x, y, z);

                    blocks.add(block);
                }
            }
        }

        return blocks;
    }







    public static ArrayList<Location> generateSphere(Location center, int radius) {
        ArrayList<Location> circlesBlocks = new ArrayList<>();
        int bX = center.getBlockX();
        int bY = center.getBlockY();
        int bZ = center.getBlockZ();

        for (int x = bX - radius; x <= bX + radius; x++) {
            for (int y = bY - radius; y <= bY + radius; y++) {
                for (int z = bZ - radius; z <= bZ + radius; z++) {
                    double distance = ((bX - x) * (bX -x) + ((bZ - z) * (bZ - z)) + ((bY - y) * (bY - y)));
                    if (distance < radius * radius) {
                        Location block = new Location(center.getWorld(), x, y, z);
                        circlesBlocks.add(block);
                    }
                }
            }
        }
        return circlesBlocks;
    }
}
