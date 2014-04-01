/**
 * 
 */
package myz.commands;

import java.util.UUID;

import myz.MyZ;
import myz.support.PlayerData;
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
public class FriendsCommand implements CommandExecutor {

    /* (non-Javadoc)
     * @see org.bukkit.command.CommandExecutor#onCommand(org.bukkit.command.CommandSender, org.bukkit.command.Command, java.lang.String, java.lang.String[])
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            ChatColor current = ChatColor.DARK_RED;
            String output = "";
            PlayerData data = PlayerData.getDataFor((Player) sender);
            int num = 0;
            if (data != null)
                for (UUID name : data.getFriends()) {
                    output += current + MyZ.instance.getName(name) + ChatColor.WHITE + ", ";
                    if (num++ % 2 == 0)
                        current = ChatColor.RED;
                    else
                        current = ChatColor.DARK_RED;
                }
            else if (MyZ.instance.getSQLManager().isConnected())
                for (String name : MyZ.instance.getSQLManager().getStringList(((Player) sender).getUniqueId(), "friends")) {
                    output += current + MyZ.instance.getName(UUID.fromString(name)) + ChatColor.WHITE + ", ";
                    if (num++ % 2 == 0)
                        current = MyZ.instance.getSQLManager().isPlayerOnline(name) ? ChatColor.GREEN : ChatColor.RED;
                    else
                        current =  MyZ.instance.getSQLManager().isPlayerOnline(name) ? ChatColor.DARK_GREEN : ChatColor.DARK_RED;
                }
            if (output.length() >= 2)
                output = output.substring(0, output.length() - 2);
            if (!output.trim().isEmpty() && output != "" && output.trim() != "")
                sender.sendMessage(output);
            else
                Messenger.sendConfigMessage(sender, "command.friend.empty");
        } else
            Messenger.sendConsoleMessage(ChatColor.RED + "That is a player-only command.");
        return true;
    }
}
