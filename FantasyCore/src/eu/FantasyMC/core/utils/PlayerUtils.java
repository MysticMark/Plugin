package eu.FantasyMC.core.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import eu.FantasyMC.core.ranks.RankPlayers;

public class PlayerUtils {

	public static String getPS(Player p){
		String result = "";
		result = RankPlayers.getPrimaryRank(p.getUniqueId()).getPrefix();
		result += " §8§l| ";
		if (RankPlayers.hasSecondary(p.getUniqueId())){
			result += RankPlayers.getSecondaryRank(p.getUniqueId()).getPrefix();
			result += " §8§l| ";
		}
		return result;
		
	}
	
	public static void setVanish(Player p, boolean vanish){
		for (Player online : Bukkit.getOnlinePlayers()){
			if (online == p) break;
			if (!vanish){
				online.showPlayer(p);
				break;
			}
			if (online.hasPermission("fantasymc.see.vanish")){
				online.showPlayer(p);
				break;
			}
			else {
				online.hidePlayer(p);
				break;
			}
		}
	}
	
	public static void updateVanish(Player p){
		if (p.hasPermission("fantasymc.see.vanish")){
			for (Player online : Bukkit.getOnlinePlayers()){
				p.showPlayer(online);
			}
		}
	}
	
}
