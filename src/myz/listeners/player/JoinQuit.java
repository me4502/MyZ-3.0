/**
 * 
 */
package myz.listeners.player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import myz.MyZ;
import myz.mobs.CustomEntityPlayer;
import myz.support.PlayerData;
import myz.support.SQLManager;
import myz.support.interfacing.Configuration;
import myz.support.interfacing.Localizer;
import myz.support.interfacing.Messenger;
import myz.utilities.Utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * @author Jordan
 * 
 */
public class JoinQuit implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    private void onPreJoin(AsyncPlayerPreLoginEvent e) {

        if(Bukkit.getOnlinePlayers().length > Bukkit.getMaxPlayers()) return;

        if ((Boolean) Configuration.getConfig(Configuration.PRELOGIN)) {
            UUID uid = MyZ.instance.getUID(e.getName());
            PlayerData data = PlayerData.getDataFor(uid);
            /* 
             * Check if the player is still banned against the playerdata and sql.
             */
            long now = System.currentTimeMillis();
            long timeOfKickExpiry = 0L;
            if (data != null)
                timeOfKickExpiry = data.getTimeOfKickban() + (Integer) Configuration.getConfig(Configuration.KICKBAN_TIME) * 1000;
            if (MyZ.instance.getSQLManager().isConnected()) {
                timeOfKickExpiry = MyZ.instance.getSQLManager().getLong(uid, "timeOfKickban")
                        + (Integer) Configuration.getConfig(Configuration.KICKBAN_TIME) * 1000;

                if(MyZ.instance.getSQLManager().isPlayerOnline(e.getName())) {
                    e.disallow(Result.KICK_OTHER, "You must wait 30 seconds between logins!");
                    return;
                }
            }

            if (timeOfKickExpiry >= now)
                e.disallow(Result.KICK_OTHER,
                        Messenger.getConfigMessage(Localizer.DEFAULT, "kick.recur", (timeOfKickExpiry - now) / 1000 + ""));
        }
    }

    @EventHandler
    private void onJoin(PlayerJoinEvent e) {

        MyZ.instance.getSQLManager().setPlayerOnline(e.getPlayer().getName(), true);

        MyZ.instance.map(e.getPlayer());
        if (!((List<String>) Configuration.getConfig(Configuration.WORLDS)).contains(e.getPlayer().getWorld().getName()))
            return;
        if (MyZ.alertOps && e.getPlayer().isOp())
            Messenger.sendMessage(e.getPlayer(), ChatColor.YELLOW + "Visit http://my-z.org/request.php to get a free MyZ MySQL database.");
        doJoin(e);
    }

    /**
     * Run all join actions for a player join event.
     * 
     * @param e
     *            The event.
     */
    private void doJoin(PlayerJoinEvent e) {
        playerJoin(e.getPlayer());
        e.setJoinMessage(null);
    }

    /**
     * Standardized join sequence for a player.
     * 
     * @param player
     *            The player.
     */
    private void playerJoin(final Player player) {
        MyZ.instance.addPlayer(player, false);

        // Ensure our NPC doesn't remain on when we log in.
        CustomEntityPlayer ourNPC = null;
        for (CustomEntityPlayer npc : MyZ.instance.getNPCs())
            if (npc.getUniqueID().equals(player.getUniqueId())) {
                ourNPC = npc;
                break;
            }
        if (ourNPC != null) {
            ourNPC.getBukkitEntity().remove();
            MyZ.instance.getNPCs().remove(ourNPC);
        }

        PlayerData data = PlayerData.getDataFor(player);

        // Update name colors for this player and all of their online friends.
        if (MyZ.instance.getServer().getPluginManager().getPlugin("TagAPI") != null
                && MyZ.instance.getServer().getPluginManager().getPlugin("TagAPI").isEnabled()) {
            KittehTag.colorName(player);

            List<UUID> friends = new ArrayList<UUID>();
            List<String> stringFriends = new ArrayList<String>();

            if (data != null)
                friends = data.getFriends();
            if (MyZ.instance.getSQLManager().isConnected())
                stringFriends = MyZ.instance.getSQLManager().getStringList(player.getUniqueId(), "friends");
            for (String s : stringFriends) {
                friends.add(SQLManager.fromString(s));
            }
            for (UUID friend : friends) {
                Player online_friend = MyZ.instance.getPlayer(friend);
                if (online_friend != null)
                    KittehTag.colorName(online_friend);
            }
        }

        Location loc = null;

        if(data != null && data.getLocation() != null)
            loc = new Location(Bukkit.getWorld(data.getLocation().split(",")[0]), Double.parseDouble(data.getLocation().split(",")[1]), Double.parseDouble(data.getLocation().split(",")[2]), Double.parseDouble(data.getLocation().split(",")[3]), Float.parseFloat(data.getLocation().split(",")[4]), Float.parseFloat(data.getLocation().split(",")[5]));
        if (MyZ.instance.getSQLManager().isConnected()) {

            String sloc = MyZ.instance.getSQLManager().getString(player.getUniqueId(), "location");

            if(sloc != null)
                loc = new Location(Bukkit.getWorld(sloc.split(",")[0]), Double.parseDouble(sloc.split(",")[1]), Double.parseDouble(sloc.split(",")[2]), Double.parseDouble(sloc.split(",")[3]), Float.parseFloat(sloc.split(",")[4]), Float.parseFloat(sloc.split(",")[5]));
        }
        if(loc != null) {
            final Location floc = loc;
            Bukkit.getScheduler().runTask(MyZ.instance, new Runnable() {

                @Override
                public void run() {
                    player.teleport(floc);

                    if (MyZ.instance.getSQLManager().isConnected()) {

                        double health = MyZ.instance.getSQLManager().getDouble(player.getUniqueId(), "health");
                        if(health > 0)
                            player.setHealth(health);
                        double hunger = MyZ.instance.getSQLManager().getDouble(player.getUniqueId(), "hunger");
                        if(hunger > 0)
                            player.setFoodLevel((int) hunger);
                        double saturation = MyZ.instance.getSQLManager().getDouble(player.getUniqueId(), "saturation");
                        if(saturation > 0)
                            player.setSaturation((float) saturation);
                        double exhaustion = MyZ.instance.getSQLManager().getDouble(player.getUniqueId(), "exhaustion");
                        if(exhaustion > 0)
                            player.setExhaustion((float) exhaustion);
                        int level = MyZ.instance.getSQLManager().getInt(player.getUniqueId(), "level");
                        if(level > 0)
                            player.setLevel(level);
                    }
                }
            });
        }


        MyZ.instance.getFlagged().remove(player.getUniqueId());

        if (Utils.packets != null)
            for (Object packet : Utils.packets.keySet())
                if (player.getWorld().getName().equals(Utils.packets.get(packet).getWorld()))
                    Utils.sendPacket(player, packet);
    }

    @EventHandler
    private void onWorldChange(PlayerChangedWorldEvent e) {
        if (!MyZ.instance.isPlayer(e.getPlayer())) {
            if (((List<String>) Configuration.getConfig(Configuration.WORLDS)).contains(e.getPlayer().getWorld().getName()))
                playerJoin(e.getPlayer());
        } else if (!((List<String>) Configuration.getConfig(Configuration.WORLDS)).contains(e.getPlayer().getWorld().getName()))
            MyZ.instance.removePlayer(e.getPlayer(), false);
    }

    @EventHandler
    private void onLeave(PlayerQuitEvent e) {

        PlayerData data = PlayerData.getDataFor(e.getPlayer());

        if(!e.getPlayer().isDead()) {
            if(data != null)
                data.setLocation(e.getPlayer().getLocation().getWorld().getName() + "," + e.getPlayer().getLocation().getX() + "," + e.getPlayer().getLocation().getY() + "," + e.getPlayer().getLocation().getZ() + "," + e.getPlayer().getLocation().getYaw() + "," + e.getPlayer().getLocation().getPitch());
            if (MyZ.instance.getSQLManager().isConnected()) {
                MyZ.instance.getSQLManager().set(e.getPlayer().getUniqueId(), "location", e.getPlayer().getLocation().getWorld().getName() + "," + e.getPlayer().getLocation().getX() + "," + e.getPlayer().getLocation().getY() + "," + e.getPlayer().getLocation().getZ() + "," + e.getPlayer().getLocation().getYaw() + "," + e.getPlayer().getLocation().getPitch(), e.isAsynchronous());
                MyZ.instance.getSQLManager().set(e.getPlayer().getUniqueId(), "health", e.getPlayer().getHealth(), e.isAsynchronous());
                MyZ.instance.getSQLManager().set(e.getPlayer().getUniqueId(), "hunger", (double)e.getPlayer().getFoodLevel(), e.isAsynchronous());
                MyZ.instance.getSQLManager().set(e.getPlayer().getUniqueId(), "saturation", (double)e.getPlayer().getSaturation(), e.isAsynchronous());
                MyZ.instance.getSQLManager().set(e.getPlayer().getUniqueId(), "exhaustion", (double)e.getPlayer().getExhaustion(), e.isAsynchronous());
                MyZ.instance.getSQLManager().set(e.getPlayer().getUniqueId(), "level", e.getPlayer().getLevel(), e.isAsynchronous());
            }
        }

        if (MyZ.instance.getSQLManager().isConnected()) {
            MyZ.instance.getSQLManager().setPlayerOnline(e.getPlayer().getName(), false);
        }

        if (MyZ.instance.removePlayer(e.getPlayer(), MyZ.instance.getFlagged().contains(e.getPlayer().getUniqueId()))) {
            e.setQuitMessage(null);

            if (e.getPlayer().getVehicle() != null)
                e.getPlayer().getVehicle().eject();

            // Get rid of our horse.
            for (Horse horse : e.getPlayer().getWorld().getEntitiesByClass(Horse.class))
                if (horse.getOwner() != null && horse.getOwner().getName() != null
                && horse.getOwner().getName().equals(e.getPlayer().getName())) {
                    horse.setOwner(null);
                    horse.setTamed(false);
                    horse.setDomestication(0);
                }

            // Spawn our NPC unless we were flagged.
            if (!MyZ.instance.getFlagged().contains(e.getPlayer().getUniqueId()) && !Configuration.isInLobby(e.getPlayer()))
                Utils.spawnNPC(e.getPlayer());
            MyZ.instance.getFlagged().remove(e.getPlayer().getUniqueId());
        }
    }
}
