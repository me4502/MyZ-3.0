package myz.support;

import myz.mobs.CustomEntityZombie;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerZombieDropEvent extends Event {

    CustomEntityZombie customEntityZombie;

    public PlayerZombieDropEvent(CustomEntityZombie customEntityZombie) {
        this.customEntityZombie = customEntityZombie;
    }

    public CustomEntityZombie getZombie() {

        return customEntityZombie;
    }

    private static final HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}