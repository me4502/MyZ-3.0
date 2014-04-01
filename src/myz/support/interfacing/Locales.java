/**
 * 
 */
package myz.support.interfacing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import myz.MyZ;

import org.bukkit.configuration.file.FileConfiguration;

/**
 * @author Jordan
 * 
 */
public class Locales {

	private static final Map<String, Object> defaultSet = new HashMap<String, Object>();
	private static final Map<String, Object> pirateSet = new HashMap<String, Object>();
	private static final Map<String, Object> turkishSet = new HashMap<String, Object>();
	private static final Map<String, Object> unSet = new HashMap<String, Object>();

	/**
	 * Make sure all locales have the appropriate mappings. Defaults
	 * non-english, non-mapped ones to english.
	 */
	public static void save() {
		if (defaultSet.isEmpty())
			loadDefault();
		List<String> codesCovered = new ArrayList<String>();

		// Makes everything that doesn't have a default value english.
		for (Localizer locale : Localizer.values()) {
			if (codesCovered.contains(locale.getCode()))
				continue;
			FileConfiguration file = MyZ.instance.getLocalizableConfig(locale);
			if (file != null) {
				Map<String, Object> map;
				switch (locale) {
				case DEFAULT:
					map = unSet;
					break;
				case PIRATE_SPEAK:
					map = pirateSet;
					break;
				case TURKISH:
					map = turkishSet;
					break;
				default:
					map = defaultSet;
					break;
				}
				for (String key : map.keySet())
					if (!file.isSet(key))
						file.set(key, map.get(key));

				// Make sure we assign an english default to locales that aren't
				// updated.
				if (locale != Localizer.DEFAULT)
					for (String key : defaultSet.keySet()) {
						if (!file.isSet(key)) {
							file.set(key, defaultSet.get(key));
						}
					}
				codesCovered.add(locale.getCode());
				MyZ.instance.saveLocalizableConfig(locale);
			}
		}
	}

	/**
	 * Load all the values for the default ENGLISH configuration. Non-english
	 * files will still get english as a default unless they are hardcoded in
	 * too.
	 */
	public static void loadDefault() {
		// Chest commands
		defaultSet.put("loot.set.arguments", "&4Please specify the name of the lootset.");
		defaultSet.put("loot.set.percent", "Enter the spawn chance percent (0-100)");
		defaultSet
				.put("loot.set.info",
						"&ePlace ONE item (with proper stack size) into the inventory that opens above. Once you close the inventory, enter the percent chance of spawning the item. Type anything to continue.");
		defaultSet.put("chest.get.click", "&eRight-click the chest you want to get.");
		defaultSet.put("chest.get.nonchest", "&4That isn't a chest.");
		defaultSet.put("chest.get.typeis", "&eThat chest has the loot table: %s");
		defaultSet.put("chest.set.click", "&eRight-click the chest you want to set.");
		defaultSet.put("chest.set.begin", "&eStarting MyZ Chest Log. This will cause some lag and take awhile.");
		defaultSet.put("chest.set.nonchest", "&4That isn't a chest.");
		defaultSet.put("chest.set.typeis", "&eThat chest now has the loot table: %s");
		defaultSet.put("chest.set.coordinate1", "&eRight-click one corner of the area you want scanned.");
		defaultSet.put("chest.set.coordinate2", "&eRight-click the other corner of the area you want scanned.");
		defaultSet.put("chest.set.initialize",
				"&eInitializing a scan on the selected area. Run another scan concurrently at your own peril.");

		// NPC names
		unSet.put("npc_names.archer.friendly", new ArrayList<String>(Arrays.asList("Robin Hood", "Jeremy", "Ramses")));
		unSet.put("npc_names.archer.enemy", new ArrayList<String>(Arrays.asList("Evil Bob", "Stephen", "Monte")));
		unSet.put("npc_names.swordsman.friendly", new ArrayList<String>(Arrays.asList("Knight Phil", "MrTeePee", "Isocrates")));
		unSet.put("npc_names.swordsman.enemy", new ArrayList<String>(Arrays.asList("Amadeus", "Jason", "Clayton")));
		unSet.put("npc_names.wanderer.friendly", new ArrayList<String>(Arrays.asList("Frederico", "Arthur", "Virgil")));
		unSet.put("npc_names.wanderer.enemy", new ArrayList<String>(Arrays.asList("Osborne", "Alastar", "Teo")));

		// Science
		unSet.put("science_gui", "Science Centre - %s pts.");
		defaultSet.put("gui.purchased", "You now have &e%s&r points.");
		defaultSet.put("gui.afford", "You don't know enough to use that.");
		defaultSet.put("gui.next_page", "Next Page");
		defaultSet.put("gui.previous_page", "Previous Page");
		defaultSet.put("gui.cost", "%s Research Points");
		defaultSet.put("research.fail", "&eThe Science Gods refuse your offering.");
		defaultSet.put("research.success", "&eYou gain a better understanding of the disease and %s research points.");
		defaultSet.put("research.success-short", "&e+%s research points!");
		defaultSet.put("research.rank", "&eThe Science Gods will not hear you. You must be ranked on this server to do research.");

		// Chat
		unSet.put("radio_name", "[&8Radio - &7%s.0&8 Hz&f]");
		unSet.put("radio_color_override", "&2");
		defaultSet.put("private.to_prefix", "&7To %s:");
		defaultSet.put("private.from_prefix", "&7From %s:");
		defaultSet.put("private.clan_prefix", "&8Clan chat:");
		defaultSet.put("private.many_players", "&4More than one player was found.");
		defaultSet.put("private.no_player", "&4The player could not be found.");

		// Special Events
		defaultSet.put("damage.bleed_begin", "&4Ouch! I think I'm bleeding.");
		defaultSet.put("damage.bleed_end", "That ought to stop the bleeding.");
		defaultSet.put("damage.leg_break", "&4Oof! That was a far fall!");
		defaultSet.put("damage.leg_fix", "That's more like it.");
		defaultSet.put("damage.poison_begin", "&5Wh&ko&r&da, &5&kI &1d&kon&r&3't &kF&r&afeel &4so &kg&r&6oo&cd...");
		defaultSet.put("damage.poison_end", "Ah, much better!");
		defaultSet.put("damage.headshot", "&eHeadshot! 2x damage.");
		defaultSet.put("special.giant_summoned", "&eYou hear the ground shake. A giant is about to be summoned.");
		defaultSet.put("special.giant_could_not_summon", "&eThere is not enough space here to summon a giant.");
		defaultSet.put("special.giant_summon_permission",
				"&4This is a donator-only feature. Donate today for the ability to spawn the fabled boss mobs.");

		// Safe logout
		defaultSet.put("safe_logout.beginning", "&2Safe logout will occur in:");
		defaultSet.put("safe_logout.cancelled", "&4Safe logout cancelled due to movement.");

		// Heal
		defaultSet.put("heal.amount", "&ePlayer &2healed&e. You now have %s heals this life.");
		defaultSet.put("heal.wait", "Please wait %s seconds before healing another player again.");
		defaultSet.put("heal.waste", "&eWell that was a waste.");
		unSet.put("heal.medkit.regeneration", "Regeneration");
		unSet.put("heal.medkit.heal", "Heal");
		unSet.put("heal.medkit.antiseptic", "Antiseptic");

		// Kill
		defaultSet.put("bandit.amount", "&ePlayer &4killed&e. You now have %s kills this life.");
		defaultSet.put("zombie.kill_amount", "&eZombie down. %s this life.");
		defaultSet.put("pigman.kill_amount", "&ePigman down. %s this life.");
		defaultSet.put("giant.kill_amount", "&eGiant down. %s this life.");
		defaultSet.put("player_npc_killed", "&e%s has been killed while combat logging.");
		defaultSet.put("player_was_killed_npc", "&eYou were killed while combat logging.");
		defaultSet.put("murdered", "&4MURDERED");

		// Kick
		defaultSet.put("kick.come_back", "&4Grab a drink. Come back in %s seconds.");
		defaultSet.put("kick.safe_logout", "&eYou have been safely logged out.");
		unSet.put("kick.recur", "&4Stop stressing. %s seconds to go.");

		// Commands
		defaultSet.put("command.base.help", "=== MyZ Help ===");

		// Blocks Command
		defaultSet.put("command.allowed.breakable", "&eYou can break:");
		defaultSet.put("command.allowed.placeable", "&eYou can place:");
		defaultSet.put("command.block.arguments", "&4Usage: /blockallow <place/destroy>");
		defaultSet.put("command.block.place.arguments", "&4Usage: /blockallow place <add [seconds until despawn]/remove>");
		defaultSet.put("command.block.destroy.arguments", "&4Usage: /blockallow destroy <add [seconds until respawn]/remove>");
		defaultSet.put("command.block.destroy.add.help",
				"&eNow break the block you want to whitelist with the item you want to allow breaking with.");
		defaultSet.put("command.block.destroy.remove.help",
				"&eNow break the block you want blacklist with the item that you can currently break with.");
		defaultSet.put("command.block.place.add.help", "&eNow place the block you would like to whitelist.");
		defaultSet.put("command.block.place.remove.help", "&eNow place the block you would like to blacklist.");
		defaultSet.put("command.block.destroy.add.summary", "&ePlayers can now destroy %s blocks with %ss.");
		defaultSet.put("command.block.destroy.remove.summary", "&ePlayers can no longer destroy %s blocks with %ss.");
		defaultSet.put("command.block.place.add.summary", "&ePlayers can now place %s blocks.");
		defaultSet.put("command.block.place.remove.summary", "&ePlayers can no longer place %s blocks.");
		defaultSet.put("command.block.destroy.add.fail", "&ePlayers can already break %s blocks with %ss.");
		defaultSet.put("command.block.destroy.remove.fail", "&ePlayers cannot destroy %s blocks with %ss.");
		defaultSet.put("command.block.place.add.fail", "&ePlayers can already place %s blocks.");
		defaultSet.put("command.block.place.remove.fail", "&ePlayers cannot place %s blocks.");

		// Spawn
		defaultSet.put("command.spawn.too_far_from_lobby", "&4You are too far from the lobby.");
		defaultSet.put("command.spawn.unable_to_spawn", "&4Unable to spawn there. Please try again shortly.");
		defaultSet.put("command.spawn.requires_rank",
				"&4This is a donator-only feature. Donate today for the ability to spawn near your friends!");
		defaultSet
				.put("spawn.zombie",
						"You are &2infected&r! As a zombie, you aren't targetted by other zombies. You never get thirsty or bleed but don't start with a spawn kit and are hunted by the living.");
		defaultSet.put("ranks.spawnmessage.0", "You have spawned in the world, find food and water.");

		// Stats Command
		defaultSet.put("command.stats.header", "==== Statistics for &e%s&r ====");
		defaultSet.put("command.stats.kills_header", "==== &eKILLS&r ====");
		defaultSet.put("command.stats.kills", "Zombie: &e%s&r  Pigman: &e%s&r  Giant: &e%s&r  Player: &e%s");
		defaultSet.put("command.stats.time_header", "==== &eTIME SURVIVED&r ====");
		defaultSet.put("command.stats.time", "Total: &e%s minutes&r  This life: &e%s minutes");
		defaultSet.put("command.stats.footer", "See complete stats at http://my-z.org#statistics");

		// Research Command
		defaultSet.put("command.research.arguments", "&4Usage: /setresearch <addreward [point cost]/add [point value]/remove>");
		defaultSet.put("command.research.reward.added", "&ePlayers can now research %s with %s research points.");
		defaultSet.put("command.research.added", "&ePlayers can now do research with %s for %s research points.");
		defaultSet.put("command.research.removed", "&ePlayers can no longer research %s.");
		defaultSet.put("command.research.item", "&eYou must be holding the item you wish to add/remove from research.");
		defaultSet.put("command.research.item_exists", "&4That item is already researchable.");
		defaultSet.put("command.research.item_no_exists", "&4That item isn't researchable.");

		// Setlobby Command
		defaultSet.put("command.setlobby.requires_cuboid", "&4You must make a &ocuboid&r&4 selection with WorldEdit.");
		defaultSet.put("command.setlobby.updated", "&2The lobby region has been updated.");

		// Rank Command
		defaultSet.put("command.setrank.success", "&eYou have successfully updated the player's rank.");
		defaultSet.put("command.setrank.failure",
				"&4You must specify the name of a player that has played before and a rank value greater or equal to 0.");
		defaultSet.put("command.saverank.requires_number", "&4You must specify a rank number to save for.");
		defaultSet.put("command.saverank.requires_prefix", "&4You must specify a prefix to set.");
		defaultSet.put("command.saverank.saved", "&eThe chat prefix for rank number %s has been set to %s.");

		// Spawnpoint Command
		defaultSet.put("command.addspawn.added", "&eYour location has been added to the spawnpoints.");
		defaultSet.put("command.addspawn.already_exists", "&4This location is already a spawnpoint.");
		defaultSet.put("command.removespawn.removed", "&eThe spawnpoint has been removed.");
		defaultSet.put("command.removespawn.unable_to_remove", "&4The number you specified is out of range.");
		defaultSet.put("command.removespawn.requires_number",
				"&4You must specify a spawnpoint number to remove. See numbers using /spawnpoints.");

		// Kit
		defaultSet.put("command.savekit.requires_number", "&4You must specify a rank number to save for.");
		defaultSet.put("command.savekit.saved", "&eThe starting kit for rank %s has been saved as your current inventory contents.");

		// Clan
		defaultSet.put("clan.name.too_long", "&4Clan names must be less than 20 characters.");
		defaultSet.put("clan.joined", "Your join request to '&e%s&r' was accepted.");
		defaultSet.put("clan.notjoined", "&4You cannot join clans.");
		defaultSet.put("clan.joining", "Sent request to join clan.");
		defaultSet.put("command.clan.leave", "You are no longer in a clan.");
		defaultSet.put("command.clan.not_in", "You are not in a clan.");
		defaultSet.put("command.clan.in", "You are in '&e%s&r' (%s online / %s total).");

		// Friends
		defaultSet.put("command.friend.requires_name", "&4You must specify a name to friend.");
		defaultSet.put("command.friend.non_exist", "&4%s has never played before.");
		defaultSet.put("command.friend.empty", "&4You have no friends.");
		defaultSet.put("friend.added", "&e%s &9has been added to your friends list.");
		defaultSet.put("friend.removed", "&e%s &9has been removed from your friends list.");

		loadPirate();
		loadTurkish();
	}

	/**
	 * Load all language for the default PIRATE configuration.
	 */
	private static void loadPirate() {
		// Chest commands
		pirateSet.put("loot.set.arguments", "&4Yarrr! Please specify t' name o' t' lootset.");
		pirateSet.put("loot.set.percent", "Enter t' spawn chance percent (0-100) ye scurvy dog!");
		pirateSet
				.put("loot.set.info",
						"&ePut but ONE t'ing (with t' proper amount) into t'inventory that opens above. Once you close t'inventory, enter t'percent chance o' spawnin' the booty. Type anythin t' continue.");
		pirateSet.put("chest.get.click", "&eStarboard click t' treasure chest.");
		pirateSet.put("chest.get.nonchest", "&4Yarr!");
		pirateSet.put("chest.get.typeis", "&eThere be treasure: %s");
		pirateSet.put("chest.set.click", "&eStartoboard click t' treasure chest ye want t' set.");
		pirateSet.put("chest.set.begin", "&eStartin' MyZ Chest Log. Batten down the hatches!");
		pirateSet.put("chest.set.nonchest", "&4Alas!");
		pirateSet.put("chest.set.typeis", "&eThat trasure chest now be holdin' %s");
		pirateSet.put("chest.set.coordinate1", "&eSmack one corner of t' land ye want scanned.");
		pirateSet.put("chest.set.coordinate2", "&eHit t' other corner of da land ye want scanned.");
		pirateSet.put("chest.set.initialize", "&eLand ho! Search again at ye own peril.");

		// Science
		pirateSet.put("gui.purchased", "Ye now have &e%s&r dubloons.");
		pirateSet.put("gui.afford", "Yer not smart enough t' do that.");
		pirateSet.put("gui.next_page", "More Treasure");
		pirateSet.put("gui.previous_page", "More Treasure");
		pirateSet.put("gui.cost", "%s Dubloons");
		pirateSet.put("research.fail", "&eYarr.");
		pirateSet.put("research.success", "&eYarrrr, ye found %s dubloons!");
		pirateSet.put("research.success-short", "&e+%s dubloons!");
		pirateSet.put("research.rank", "&eYe must be important on da server t' find gold dubloons.");

		// Chat
		pirateSet.put("private.to_prefix", "&7Ahoy, %s:");
		pirateSet.put("private.from_prefix", "&7From %s:");
		pirateSet.put("private.clan_prefix", "&8Crew chat:");
		pirateSet.put("private.many_players", "&4More than one crew member was found, matey.");
		pirateSet.put("private.no_player", "&4That crew member could not be found.");

		// Special Events
		pirateSet.put("damage.bleed_begin", "&4Cor blimey!");
		pirateSet.put("damage.bleed_end", "That thar is better.");
		pirateSet.put("damage.leg_break", "&4Arghh, me peg-leg!");
		pirateSet.put("damage.leg_fix", "Bettarrrrrr.");
		pirateSet.put("damage.poison_begin", "&5To&o&r &mu&5&kc&1h&k ru&r&3mmm&k.&r&a..");
		pirateSet.put("damage.poison_end", "Me spies!");
		pirateSet.put("damage.headshot", "&eShot across the bow! He'll be swimmin' with t' fishes.");
		pirateSet.put("special.giant_summoned", "&eBatten down the hatches, a giant is about t' be summoned!");
		pirateSet.put("special.giant_could_not_summon", "&eThere is not enough space here t' summon a giant.");
		pirateSet.put("special.giant_summon_permission", "&4That costs a piece o' eight. Donate for t' know-how, savvy?");

		// Safe logout
		pirateSet.put("safe_logout.beginning", "&2Safe logout in:");
		pirateSet.put("safe_logout.cancelled", "&4Safe logout stopped.");

		// Heal
		pirateSet.put("heal.amount", "&eThe lubber was &2healed&e. You now have %s heals.");
		pirateSet.put("heal.wait", "Wait %s seconds before healing yer deckmate.");
		pirateSet.put("heal.waste", "&eShiver me timbers!");

		// Kill
		pirateSet.put("bandit.amount", "&eFeed the fishes! You now have %s heads.");
		pirateSet.put("zombie.kill_amount", "&eWalk the plank! %s this life.");
		pirateSet.put("pigman.kill_amount", "&eThar she blows! %s this life.");
		pirateSet.put("giant.kill_amount", "&eShark bait. %s this life.");
		pirateSet.put("player_npc_killed", "&e%s has been hornswaggled while running from a fight.");
		pirateSet.put("player_was_killed_npc", "&eYe was killed while runnin' from a fight.");
		pirateSet.put("murdered", "&4SWASHBUCKLED");

		// Kick
		pirateSet.put("kick.come_back", "&4Grab some more rum. Come back in %s seconds.");
		pirateSet.put("kick.safe_logout", "&eYe have escaped with yer life.");

		// Commands
		pirateSet.put("command.base.help", "=== MyZ Help ===");

		// Blocks Command
		pirateSet.put("command.allowed.breakable", "&eYe can break:");
		pirateSet.put("command.allowed.placeable", "&eYe can place:");
		pirateSet.put("command.block.arguments", "&4Usage: /blockallow <place/destroy>");
		pirateSet.put("command.block.place.arguments", "&4Usage: /blockallow place <add [seconds until despawn]/remove>");
		pirateSet.put("command.block.destroy.arguments", "&4Usage: /blockallow destroy <add [seconds until respawn]/remove>");
		pirateSet.put("command.block.destroy.add.help",
				"&eNow break t' block ye want t' whitelist with t' item ye want t' allow breaking with.");
		pirateSet.put("command.block.destroy.remove.help",
				"&eNow break the block ye want blacklist with t' item that ye can currently break with.");
		pirateSet.put("command.block.place.add.help", "&eNow place the block you would like t' whitelist.");
		pirateSet.put("command.block.place.remove.help", "&eNow place the block ye would like to blacklist.");
		pirateSet.put("command.block.destroy.add.summary", "&eThe lads can now destroy %s blocks with %ss.");
		pirateSet.put("command.block.destroy.remove.summary", "&eLassies can no longer destroy %s blocks with %ss.");
		pirateSet.put("command.block.place.add.summary", "&eBucco's can now place %s blocks.");
		pirateSet.put("command.block.place.remove.summary", "&eLandlubbers can no longer place %s blocks.");
		pirateSet.put("command.block.destroy.add.fail", "&eYer crew can already break %s blocks with %ss.");
		pirateSet.put("command.block.destroy.remove.fail", "&eBucko's cannot destroy %s blocks with %ss.");
		pirateSet.put("command.block.place.add.fail", "&eYer crew can already place %s blocks.");
		pirateSet.put("command.block.place.remove.fail", "&eLads cannot place %s blocks.");

		// Spawn
		pirateSet.put("command.spawn.too_far_from_lobby", "&4Ye must be on yer ship to set sail.");
		pirateSet.put("command.spawn.unable_to_spawn", "&4There's a storm there. Wait for it to pass.");
		pirateSet.put("command.spawn.requires_rank", "&4That costs dubloons. Donate for the ability to spawn near yer crew!");
		pirateSet
				.put("spawn.zombie",
						"Yer &2infected&r! As a zombie, ye aren't targetted by other zombies. Ye don't need rum nor bleed but don't start with any treasure and are hunted by yer crew!");
		pirateSet.put("ranks.spawnmessage.0", "Go find some rum!");

		// Stats Command
		pirateSet.put("command.stats.header", "==== Statistics for &e%s&r ====");
		pirateSet.put("command.stats.kills_header", "==== &eKILLS&r ====");
		pirateSet.put("command.stats.kills", "Zombie: &e%s&r  Pigman: &e%s&r  Giant: &e%s&r  Player: &e%s");
		pirateSet.put("command.stats.time_header", "==== &eTIME SURVIVED&r ====");
		pirateSet.put("command.stats.time", "Total: &e%s minutes&r  This life: &e%s minutes");
		pirateSet.put("command.stats.footer", "See more at http://my-z.org#statistics");

		// Research Command
		pirateSet.put("command.research.arguments", "&4Usage: /setresearch <addreward [point cost]/add [point value]/remove>");
		pirateSet.put("command.research.reward.added", "&eYer crew can now research %s with %s dubloons.");
		pirateSet.put("command.research.added", "&eYer lads can now do research with %s for %s dubloons.");
		pirateSet.put("command.research.removed", "&eLassies can no longer research %s.");
		pirateSet.put("command.research.item", "&eYe must be holding the item you wish t' add/remove from research.");
		pirateSet.put("command.research.item_exists", "&4That is already researchable.");
		pirateSet.put("command.research.item_no_exists", "&4Ye can't do that.");

		// Setlobby Command
		pirateSet.put("command.setlobby.requires_cuboid", "&4Ye must make a &ocuboid&r&4 selection with WorldEdit.");
		pirateSet.put("command.setlobby.updated", "&2The crow's nest has been updated.");

		// Rank Command
		pirateSet.put("command.setrank.success", "&eYer crewmate has been promoted.");
		pirateSet.put("command.setrank.failure", "&4Ye must specify the name of a crewman and a rank value at least 0.");
		pirateSet.put("command.saverank.requires_number", "&4Ye must specify a rank number t' save for.");
		pirateSet.put("command.saverank.requires_prefix", "&4Ye must specify a prefix t' set.");
		pirateSet.put("command.saverank.saved", "&eT' chat prefix for rank number %s has been set t' %s.");

		// Spawnpoint Command
		pirateSet.put("command.addspawn.added", "&eWeigh anchor and hoist the mizzen!");
		pirateSet.put("command.addspawn.already_exists", "&4Shiver me timbers, that's already a spawnpoint.");
		pirateSet.put("command.removespawn.removed", "&eThe scurvy dog has walked t' plank.");
		pirateSet.put("command.removespawn.unable_to_remove", "&4T' number is too big.");
		pirateSet.put("command.removespawn.requires_number", "&4Ye must give a spawn number. See numbers using /spawnpoints.");

		// Kit
		pirateSet.put("command.savekit.requires_number", "&4Ye must specify a rank number t' save for.");
		pirateSet.put("command.savekit.saved", "&eThe treasure kit for rank %s has been saved as your current treasure trove.");

		// Clan
		pirateSet.put("clan.name.too_long", "&4Crew names must be less than 20 scuttles.");
		pirateSet.put("clan.joined", "Yer join request to '&e%s&r' was accepted.");
		pirateSet.put("clan.notjoined", "&4Ye can't join crews, ya scurvy dog.");
		pirateSet.put("clan.joining", "Sent request t' join crew.");
		pirateSet.put("command.clan.leave", "Ye have abandoned ship.");
		pirateSet.put("command.clan.not_in", "Yer not part of a crew.");
		pirateSet.put("command.clan.in", "Yer in '&e%s&r' (%s alive / %s total).");

		// Friends
		pirateSet.put("command.friend.requires_name", "&4You must specify a lubber to befriend.");
		pirateSet.put("command.friend.non_exist", "&4%s has never played before.");
		pirateSet.put("command.friend.empty", "&4Yer crew has abandoned you.");
		pirateSet.put("friend.added", "&e%s &9has been added to your crew.");
		pirateSet.put("friend.removed", "&e%s &9has been removed from your crew list.");
	}

	private static void loadTurkish() {
		Map<String, Object> set = turkishSet;

		set.put("command.research.item_no_exists", "&4Bu esya aratilabilir degil.");
		set.put("command.research.arguments", "&4Kullanim: /setresearch <addreward [puan degeri]/add [point degeri]/remove>");
		set.put("command.research.added", "&eArtik oyuncular %s ile %s arama noktalari icin arama yapabiliyorlar!");
		set.put("command.research.item", "&eElinde aranabilirlige eklemek/cikarmak istedigin esyayi tutuyor olman gerek.");
		set.put("command.research.reward.added", "&ePlayers can now research %s with %s research points.");
		set.put("command.research.removed", "&ePlayers can no longer research %s.");
		set.put("command.research.item_exists", "&4Bu madde zaten arastirilabilir oldugunu.");
		set.put("command.spawn.unable_to_spawn", "&4Belirtiginiz yerde canlanma mumkun olamadi. Lutfen biraz sonra deneyin.");
		set.put("command.spawn.requires_rank", "&4Arkadaslarinin yaninda canlanmak VIP Ozelligidir! Hemen Kos bir tane al derim.");
		set.put("command.spawn.too_far_from_lobby", "&4Lobiden cok uzaksin.");
		set.put("command.friend.non_exist", "&4%s boyle bir oyuncu daha once hic bu servera gelmemis.");
		set.put("command.friend.requires_name", "&4Arkadasinin ismini de yazmalisin.");
		set.put("command.friend.empty", "&4Hic arkadasin yok.");
		set.put("command.stats.kills_header", "==== &eKILLS&r ====");
		set.put("command.stats.footer", "Tum statlarini buradan gorebilirsin http://my-z.org#statistics");
		set.put("command.stats.time", "Toplam: &e%s dakika&r Bu yasaminda: &e%s dakika.");
		set.put("command.stats.header", "==== &e%s&r icin istatikler ====");
		set.put("command.stats.time_header", "==== &eHayatta Kalinan sure&r ====");
		set.put("command.stats.kills", "Zombi: &e%s&r  Zombi Domuz: &e%s&r  Dev: &e%s&r  Oyuncu: &e%s");
		set.put("command.saverank.saved", "&eDerece %s icin prefix %s olarak ayarlandi.");
		set.put("command.saverank.requires_number", "&4Kaydetmek icin belli bir derece belirtmen gerek.");
		set.put("command.saverank.requires_prefix", "&4Belli bir prefix belirtmeniz gerek.");
		set.put("command.block.destroy.remove.help", "&eSimdi karalistelemek istedigin bloka suan kirabildigin bir esya ile vur.");
		set.put("command.block.destroy.remove.fail", "&eOyuncular %s blocklarini  %ss kullanarak kiramiyorlar.");
		set.put("command.block.destroy.remove.summary", "&eArtik oyuncular %s bloklarini %ss kullanarak yok edemiyorlar.");
		set.put("command.block.destroy.add.summary", "&eArtik oyuncular %s blocklari %ss kullanarak kirabilirler.");
		set.put("command.block.destroy.add.fail", "&eOyuncular zaten %s bloklarini %ss kullanarak kirabiliyorlar");
		set.put("command.block.destroy.add.help",
				"&eSimdi beyazlisteye eklemek istedigin bloka hangi esya ile kirilmasini istiyorsan o esyayi kullanarak vur.");
		set.put("command.block.destroy.arguments", "&4Kullanim: /blockallow destroy <add [canlanmasina kadar gececek olan saniye]/remove>");
		set.put("command.block.place.remove.help", "&eKaralistelemek istedigin blogu yere koy.");
		set.put("command.block.place.remove.summary", "&eArtik oyuncular %s bloklarini koyamiyorlar.");
		set.put("command.block.place.remove.fail", "&eOyuncular %s lari koyamiyorlar.");
		set.put("command.block.place.add.help", "&eBeyaz listelemek istedigin blogu yere koy.");
		set.put("command.block.place.add.summary", "&eArtik oyuncular %s bloklarini koyabiliyorlar.");
		set.put("command.block.place.add.fail", "&eOyuncular zaten %s bloklarini koyabiliyorlar.");
		set.put("command.block.place.arguments", "&4Kullanim: /blockallow place <add [yok olmasina kadar gececek olan saniye]/remove>");
		set.put("command.block.arguments", "&4Kullanim: /blockallow <place/destroy>");
		set.put("command.setlobby.updated", "&2Lobi guncellendi.");
		set.put("command.setlobby.requires_cuboid", "&4WorldEdit kullanarak &ocuboid&r &4bir secim yapmalisin.");
		set.put("command.setrank.success", "&eOyuncunun derecesini basariyla ayarladiniz.");
		set.put("command.setrank.failure", "&4Oyuncunun ismini ve derece seviyesini(0'a esit veya 0'dan buyuk) belirtmeniz gerek.");
		set.put("command.addspawn.added", "&eCanlanma noktasi eklendi.");
		set.put("command.addspawn.already_exists", "&4Burasi zaten bir canlanma noktasi.");
		set.put("command.removespawn.requires_number",
				"&4Canlanma noktasi numarasi belirtmelisiniz. Numaralari gormek icin /spawnpoints yazin.");
		set.put("command.removespawn.unable_to_remove", "&4yazdiginiz numara cok uzakta.");
		set.put("command.removespawn.removed", "&eCanlanma noktasi kaldirildi.");
		set.put("command.base.help", "=== MyZ Yardim ===");
		set.put("command.savekit.saved", "&e%s derecesi icin canlanma kiti suanki envanterinize gore duzenlendi.");
		set.put("command.savekit.requires_number", "&4Kaydetmek icin belli bir derece belirtmeniz gerek.");
		set.put("command.clan.in", "Oldugun klan &e%s&r (%s acik / %s toplam).");
		set.put("command.clan.not_in", "Bir klanda degilsin.");
		set.put("command.clan.leave", "Artik klanda degilsin.");
		set.put("command.allowed.breakable", "&eKirabileceklerin:");
		set.put("command.allowed.placeable", "&eKoyabileceklerin:");
		set.put("damage.headshot", "&eTam kafadan! &42x &ehasar.");
		set.put("damage.leg_break", "&4Of! Biraz yuksek bir duşustu!");
		set.put("damage.leg_fix", "Heh, artik daha iyi yuruyebilirim!");
		set.put("damage.poison_end", "Ah, cok daha iyi!");
		set.put("damage.bleed_end", "Bunun kanamayi durdurmasi gerek.");
		set.put("damage.poison_begin", "&5Wh&ko&r&da, &5&kBen &1p&ke&4k &3i&4&ky&r&5i &6h&2i&1s&4&ks&r&7e&8t&9mi&ay&b&ko&r&4r&cu&dm!");
		set.put("damage.bleed_begin", "&4Ovovovov! Galiba kaniyorum!");
		set.put("loot.set.arguments", "&4Lutfen loot listesine bir isim verin.");
		set.put("loot.set.percent", "Lutfen %de kac canlanma sansi oldugunu sohbet bolumune yazarak belirtin (0-100).");
		set.put("loot.set.info",
				"&eSimdi envanterinize loot listesinde bulunmasini istediginiz esyalari alin ve sohbet bolumune her hangi bir sey yazin ve onunuze gelen ekranda yukari bolume esyalari kacar kacar canlanmasini istiyorsaniz o kadarli koyun ve sohbet bolumune canlanma yuzdesini yazin bu islemi istediginiz kadar tekrarlayin.");
		set.put("friend.added", "&e%s &9arkadas listene eklendi.");
		set.put("friend.removed", "&e%s &9arkadas listenden silindi.");
		set.put("gui.purchased", "Artik &e%s&r puanin var.");
		set.put("gui.cost", "%s Arama Puani");
		set.put("gui.previous_page", "Onceki Sayfa");
		set.put("gui.next_page", "Siradaki Sayfa");
		set.put("gui.afford", "Bunu kullanmak icin yeterli bilgin yok.");
		set.put("zombie.kill_amount", "&eZombi olduruldu. toplam oldurme %s.");
		set.put("murdered", "&4OLDURULDU");
		set.put("research.fail", "&eTip tanrilari istegini red ettiler gibi.");
		set.put("research.success", "&eArtik hastalik hakkinda daha fazla bilgin var ve %s kadar arama puani kazandin. Tebrikler!");
		set.put("research.rank", "&eTanrilar seni duyamiyor. Arama yapmak icin dereceli olman gerek.");
		set.put("safe_logout.cancelled", "&4Hareket ettiginiz icin guvenli cikis iptal edildi.");
		set.put("safe_logout.beginning", "&2Guvenli cikis icin:");
		set.put("chest.set.typeis", "&eBu sandik artik %s adli loot listesindeki lootlara sahip.");
		set.put("chest.set.begin", "&eMyZ sandik logu calistiriliyor.Bu Biraz laga sebeb olabilir.");
		set.put("chest.set.click", "&eAyarlamak istedigin sandiga sag tikla.");
		set.put("chest.set.initialize", "&eTarama basliyor!");
		set.put("chest.set.coordinate2", "&eSimdide taramak istedigin yerin obur kosesine vur.");
		set.put("chest.set.coordinate1", "&eTaramak istedigin yerin bir kosesine vur.");
		set.put("chest.set.nonchest", "&4O bir sandik degil.");
		set.put("chest.get.typeis", "&eBu sandik %s lootlarina sahip.");
		set.put("chest.get.nonchest", "&4O bir sandik degil.");
		set.put("chest.get.click", "&eAlmak istedigin sandiga sag tikla.");
		set.put("private.to_prefix", "&7Gonderilen %s:");
		set.put("private.no_player", "&4Oyuncu bulunamadi.");
		set.put("private.many_players", "&4Birden fazla oyuncu bulundu.");
		set.put("private.from_prefix", "&7Gonderen kisi %s:");
		set.put("private.clan_prefix", "&8Klan konusmasi:");
		set.put("giant.kill_amount", "&eDev olduruldu. Toplam dev oldurmelerin %s.");
		set.put("special.giant_summon_permission", "&4Bu VIPlere ozel bir ozellik! Hemen kos bir tane al.");
		set.put("special.giant_could_not_summon", "&eBurada bir dev icin yeterli alan yok.");
		set.put("special.giant_summoned", "&eYer sallaniyor! Bu bir anlama gelebilir, bir dev geliyor!");
		set.put("heal.wait", "Lutfen birini iyilestirmek icin %s saniye bekleyin.");
		set.put("heal.waste", "&eHm, guzel bir harcama oldu.");
		set.put("heal.amount", "&eOyuncu &2iyilestirildi&e. Toplam iyilestirmeler %s.");
		set.put("pigman.kill_amount", "&eZombi domuz olduruldu. Toplam %s tane oldurdun.");
		set.put("clan.namez.too_long", "&4Klan isimleri 20 harften kisa olmalidir.");
		set.put("clan.joined", "&e%s&r adli klana katilma istedigin kabul edildi.");
		set.put("clan.joining", "Klana katilma istegi gonderildi.");
		set.put("kick.safe_logout", "&eBasariyla guvenli cikis yaptiniz.");
		set.put("kick.come_back", "&4Git bir icecek al ! %s saniyeye geri gel.");
		set.put("bandit.amount", "&eOyuncu &olduruldu&e. toplam oldurmeler %s.");
		set.put("ranks.spawnmessage.0", "Zalim dunyaya canlandin. Hayatta kal!");
		set.put("player_npc_killed", "&e%s combat logginde oldu.");
		set.put("player_was_killed_npc", "&eCombat logginde oldun.");
		set.put("spawn.zombie",
				"Artik bir &2zombisin&r! Zombiyken obur zombiler sana saldirmayacaktir. Asla susamassin veya kanamazsin ancak canlanma kitin yoktur ve yasayanlara karsi dusmansin!");

	}
}
