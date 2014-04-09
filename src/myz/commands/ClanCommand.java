/**
 * 
 */
package myz.commands;

import myz.MyZ;
import myz.support.PlayerData;
import myz.support.interfacing.Localizer;
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
public class ClanCommand implements CommandExecutor {

    /* (non-Javadoc)
     * @see org.bukkit.command.CommandExecutor#onCommand(org.bukkit.command.CommandSender, org.bukkit.command.Command, java.lang.String, java.lang.String[])
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            PlayerData data = PlayerData.getDataFor((Player) sender);
            String clan = "";
            int online = 1, total = 1;
            if (data != null) {
                clan = data.getClan();
                online = data.getOnlinePlayersInClan().size() - 1;
                total = data.getNumberInClan();
            }
            if (MyZ.instance.getSQLManager().isConnected()) {
                clan = MyZ.instance.getSQLManager().getClan(((Player) sender).getUniqueId());
                online = MyZ.instance.getSQLManager().getOnlinePlayersInClan(((Player) sender).getUniqueId()).size();
                total = MyZ.instance.getSQLManager().getNumberInClan(((Player) sender).getUniqueId());
            }
            if (clan == null || clan.isEmpty())
                Messenger.sendConfigMessage(sender, "command.clan.not_in");
            else {
                Messenger.sendMessage(sender,
                        Messenger.getConfigMessage(Localizer.getLocale((Player) sender), "command.clan.in", clan, online + "", total + ""));
                StringBuilder builder = new StringBuilder();
                boolean first = true;
                for(Player player : MyZ.instance.getSQLManager().getOnlinePlayersInClan(((Player) sender).getUniqueId())) {
                    if(first) {
                        first = false;
                    } else
                        builder.append(", ");
                    builder.append(player.getName());
                }

                sender.sendMessage("The online members of your clan are: " + builder.toString());
            }
        } else
            Messenger.sendConsoleMessage(ChatColor.RED + "That is a player-only command.");
        return true;
    }
}
