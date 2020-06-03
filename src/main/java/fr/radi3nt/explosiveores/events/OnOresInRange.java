package fr.radi3nt.explosiveores.events;

import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class OnOresInRange extends Event {
    private static final HandlerList handlers = new HandlerList();
    private static Location blockLocation;


    public OnOresInRange(Location location) {
        blockLocation=location;
    }

    public Location getOreLocation() {
        return blockLocation;
    }


    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
