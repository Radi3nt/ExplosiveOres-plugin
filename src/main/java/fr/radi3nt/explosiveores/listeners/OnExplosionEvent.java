package fr.radi3nt.explosiveores.listeners;

import fr.radi3nt.explosiveores.MainExplosiveOres;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.plugin.Plugin;


public class OnExplosionEvent implements Listener {


    @EventHandler
    public void OnExplosionEvent(BlockExplodeEvent e) {
        for(Block b : e.blockList()){
            b.setType(Material.AIR);
        }
    }


}
