package eu.FantasyMC.core.utils;

import java.io.File;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import eu.FantasyMC.core.main.FantasyMCCore;

/*
Uncomment this if you want the helper method for BungeeCord:
import net.md_5.bungee.api.connection.ProxiedPlayer;
*/

/*
Uncomment this if you want the helper method for Bukkit/Spigot:
import org.bukkit.entity.Player;
*/

/**
 * Helper-class for getting UUIDs of players
 */
public class UUIDFetcher {
	
	/**
	 * @param player The player
	 * @return The UUID of the given player
	 */
	 //Uncomment this if you want the helper method for BungeeCord:
	/*
	public static UUID getUUID(ProxiedPlayer player) {
		return getUUID(player.getName());
	}
	*/
	
	/**
	 * @param player The player
	 * @return The UUID of the given player
	 */
	 //Uncomment this if you want the helper method for Bukkit/Spigot:
	/*
	public static UUID getUUID(Player player) {
		return getUUID(player.getName());
	}
	*/
	
	/**
	 * @param playername The name of the player
	 * @return The UUID of the given player
	 */
	@SuppressWarnings("deprecation")
	public static UUID getUUID(String playername) {
		OfflinePlayer op = Bukkit.getOfflinePlayer(playername);
		return op.getUniqueId();
	}
	
	public static boolean hasPlayedBefore(UUID id){
		File file = new File(FantasyMCCore.getInstance().getDataFolder() + File.separator + "playerdata", id.toString() + ".yml");
		return file.exists();
	}
	
	
}