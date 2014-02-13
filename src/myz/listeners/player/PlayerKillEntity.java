/**
 * 
 */
package myz.listeners.player;

import myz.MyZ;
import myz.support.PlayerData;
import myz.support.interfacing.Localizer;
import myz.support.interfacing.Messenger;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

/**
 * @author Jordan
 * 
 */
public class PlayerKillEntity implements Listener {

	@EventHandler(priority = EventPriority.LOWEST)
	private void onEntityDeath(EntityDeathEvent e) {
		if (!MyZ.instance.getWorlds().contains(e.getEntity().getWorld().getName()))
			return;
		e.setDroppedExp(0);
		if (e.getEntity().getKiller() != null)
			incrementKills(e.getEntity(), e.getEntity().getKiller());
	}

	/**
	 * Increment the statistics for the given player for killing the specified
	 * entity.
	 * 
	 * @param typeFor
	 *            The Entity that was killed.
	 * @param playerFor
	 *            The Player that killed the entity.
	 * @param e
	 *            The EntityDeathEvent that drives this call.
	 */
	private void incrementKills(Entity typeFor, Player playerFor) {
		PlayerData data = PlayerData.getDataFor(playerFor);
		int amount = 0;
		switch (typeFor.getType()) {
		case ZOMBIE:
			if (data != null) {
				data.setZombieKillsLife(amount = data.getZombieKillsLife() + 1);
				data.setZombieKills(data.getZombieKills() + 1);
				if (amount > data.getZombieKillsLifeRecord())
					data.setZombieKillsLifeRecord(amount);
			}
			if (MyZ.instance.getSQLManager().isConnected()) {
				MyZ.instance.getSQLManager().set(playerFor.getName(), "zombie_kills_life",
						amount = MyZ.instance.getSQLManager().getInt(playerFor.getName(), "zombie_kills_life") + 1, true);
				MyZ.instance.getSQLManager().set(playerFor.getName(), "zombie_kills",
						MyZ.instance.getSQLManager().getInt(playerFor.getName(), "zombie_kills") + 1, true);
				if (amount > MyZ.instance.getSQLManager().getInt(playerFor.getName(), "zombie_kills_life_record"))
					MyZ.instance.getSQLManager().set(playerFor.getName(), "zombie_kills_life_record", amount, true);
			}
			Messenger.sendMessage(playerFor, Messenger.getConfigMessage(Localizer.getLocale(playerFor), "zombie.kill_amount", amount));
			break;
		case PIG_ZOMBIE:
			if (data != null) {
				data.setPigmanKillsLife(amount = data.getPigmanKillsLife() + 1);
				data.setPigmanKills(data.getPigmanKills() + 1);
				if (amount > data.getPigmanKillsLifeRecord())
					data.setPigmanKillsLifeRecord(amount);
			}
			if (MyZ.instance.getSQLManager().isConnected()) {
				MyZ.instance.getSQLManager().set(playerFor.getName(), "pigman_kills_life",
						amount = MyZ.instance.getSQLManager().getInt(playerFor.getName(), "pigman_kills_life") + 1, true);
				MyZ.instance.getSQLManager().set(playerFor.getName(), "pigman_kills",
						MyZ.instance.getSQLManager().getInt(playerFor.getName(), "pigman_kills") + 1, true);
				if (amount > MyZ.instance.getSQLManager().getInt(playerFor.getName(), "pigman_kills_life_record"))
					MyZ.instance.getSQLManager().set(playerFor.getName(), "pigman_kills_life_record", amount, true);
			}
			Messenger.sendMessage(playerFor, Messenger.getConfigMessage(Localizer.getLocale(playerFor), "pigman.kill_amount", amount));
			break;
		case GIANT:
			if (data != null) {
				data.setGiantKillsLife(amount = data.getGiantKillsLife() + 1);
				data.setGiantKills(data.getGiantKills() + 1);
				if (amount > data.getGiantKillsLifeRecord())
					data.setGiantKillsLifeRecord(amount);
			}
			if (MyZ.instance.getSQLManager().isConnected()) {
				MyZ.instance.getSQLManager().set(playerFor.getName(), "giant_kills_life",
						amount = MyZ.instance.getSQLManager().getInt(playerFor.getName(), "giant_kills_life") + 1, true);
				MyZ.instance.getSQLManager().set(playerFor.getName(), "giant_kills",
						MyZ.instance.getSQLManager().getInt(playerFor.getName(), "giant_kills") + 1, true);
				if (amount > MyZ.instance.getSQLManager().getInt(playerFor.getName(), "giant_kills_life_record"))
					MyZ.instance.getSQLManager().set(playerFor.getName(), "giant_kills_life_record", amount, true);
			}
			Messenger.sendMessage(playerFor, Messenger.getConfigMessage(Localizer.getLocale(playerFor), "giant.kill_amount", amount));
			break;
		case PLAYER:
			Messenger.sendFancyDeathMessage(playerFor, (Player) typeFor);

			if (data != null) {
				data.setPlayerKillsLife(amount = data.getPlayerKillsLife() + 1);
				data.setPlayerKills(data.getPlayerKills() + 1);
				if (amount > data.getPlayerKillsLifeRecord())
					data.setPlayerKillsLifeRecord(amount);
			}
			if (MyZ.instance.getSQLManager().isConnected()) {
				MyZ.instance.getSQLManager().set(playerFor.getName(), "player_kills_life",
						amount = MyZ.instance.getSQLManager().getInt(playerFor.getName(), "player_kills_life") + 1, true);
				MyZ.instance.getSQLManager().set(playerFor.getName(), "player_kills",
						MyZ.instance.getSQLManager().getInt(playerFor.getName(), "player_kills") + 1, true);
				if (amount > MyZ.instance.getSQLManager().getInt(playerFor.getName(), "player_kills_life_record"))
					MyZ.instance.getSQLManager().set(playerFor.getName(), "player_kills_life_record", amount, true);
			}
			Messenger.sendMessage(playerFor, Messenger.getConfigMessage(Localizer.getLocale(playerFor), "bandit.amount", amount));
			if (MyZ.instance.getServer().getPluginManager().getPlugin("TagAPI") != null
					&& MyZ.instance.getServer().getPluginManager().getPlugin("TagAPI").isEnabled())
				KittehTag.colorName(playerFor);
			break;
		default:
			break;
		}
	}
}