package myz.commands;

import java.util.List;

import myz.MyZ;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class AddResearchPointsCommand implements CommandExecutor, TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
        return null;
    }

    @Override
    public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {

        String player = arg3[0];
        int points = Integer.parseInt(arg3[1]);
        boolean set = false;
        if(arg3.length > 2)
            set = arg3[2].equalsIgnoreCase("set");

        Player pp = Bukkit.getPlayer(player);

        if (MyZ.instance.getSQLManager().isConnected())
            MyZ.instance.getSQLManager().set(pp.getUniqueId(), "research", (set ? 0 : MyZ.instance.getSQLManager().getInt(pp.getUniqueId(), "research")) + points, true);
        return true;
    }
}