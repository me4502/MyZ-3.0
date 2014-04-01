/**
 * 
 */
package myz.api;

import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @author Jordan
 * 
 *         This event is called when a player throws an ender pearl and before
 *         it is converted to a grenade. Cancelling it will cause the ender
 *         pearl to act as a normal ender pearl.
 */
public class PlayerThrowGrenadeEvent extends Event implements Cancellable {

	private static final HandlerList handlers = new HandlerList();
	private boolean cancelled;
	private final Player player;
	private final EnderPearl grenade;

	public PlayerThrowGrenadeEvent(Player player, EnderPearl grenade) {
		this.player = player;
		this.grenade = grenade;
	}

	/**
	 * The player that threw the grenade.
	 * 
	 * @return The player.
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * The ender pearl that represents the grenade.
	 * 
	 * @return The ender pearl.
	 */
	public EnderPearl getGrenade() {
		return grenade;
	}

	/* (non-Javadoc)
	 * @see org.bukkit.event.Cancellable#isCancelled()
	 */
	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	/* (non-Javadoc)
	 * @see org.bukkit.event.Cancellable#setCancelled(boolean)
	 */
	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;

	}

	/* (non-Javadoc)
	 * @see org.bukkit.event.Event#getHandlers()
	 */
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
}
