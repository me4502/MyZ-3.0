/**
 * 
 */
package myz.commands;

import java.util.List;

import myz.MyZ;
import myz.support.interfacing.Configuration;
import myz.support.interfacing.Messenger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Jordan
 * 
 */
public class SpawnCommand implements CommandExecutor {

	/* (non-Javadoc)
	 * @see org.bukkit.command.CommandExecutor#onCommand(org.bukkit.command.CommandSender, org.bukkit.command.Command, java.lang.String, java.lang.String[])
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			if (!((List<String>) Configuration.getConfig(Configuration.WORLDS)).contains(((Player) sender).getWorld().getName()))
				return true;
			if (Configuration.isInLobby((Player) sender)) {
				int spawnpoint = -1;
				if (args.length != 0)
					try {
						spawnpoint = Integer.parseInt(args[0]);
						if (spawnpoint <= 0)
							spawnpoint = 1;
						int numberOfSpawns = Configuration.getNumberOfSpawns();
						if (spawnpoint > numberOfSpawns)
							spawnpoint = numberOfSpawns;
					} catch (NumberFormatException exc) {

					}
				MyZ.instance.spawnPlayer((Player) sender, spawnpoint, spawnpoint != -1, 0);
			} else
				Messenger.sendConfigMessage(sender, "command.spawn.too_far_from_lobby");
		} else
			Messenger.sendConsoleMessage(ChatColor.RED + "That is a player-only command.");
		return true;
	}
}
