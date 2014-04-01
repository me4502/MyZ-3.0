/**
 * 
 */
package myz.api;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @author Jordan
 * 
 *         This event is fired every time the player takes damage from bleeding.
 *         Every player will take bleed damage on the same tick. Cancelling this
 *         event will prevent damage from occurring.
 */
public class PlayerTakeBleedingDamageEvent extends Event implements Cancellable {

	private static final HandlerList handlers = new HandlerList();
	private boolean cancelled;
	private final Player player;

	public PlayerTakeBleedingDamageEvent(Player player) {
		this.player = player;
	}

	/**
	 * The player that took damage.
	 * 
	 * @return The player.
	 */
	public Player getPlayer() {
		return player;
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
