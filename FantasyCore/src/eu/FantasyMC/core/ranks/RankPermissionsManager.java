package eu.FantasyMC.core.ranks;

import java.util.HashMap;
import java.util.List;

import org.bukkit.permissions.Permission;

import eu.FantasyMC.core.ranks.RankData.Ranks;

public class RankPermissionsManager {

	private static HashMap<Ranks, RankPermissions> rankperms = new HashMap<Ranks, RankPermissions>();
	
	public static void addPermission(Ranks rank, String permission){
		getRankPermissions(rank).addPermission(permission);
	}

	public static void addPermission(Ranks rank, Permission permission){
		addPermission(rank, permission.getName());
	}

	public static void addPermission(Ranks rank, List<String> permission){
		getRankPermissions(rank).addPermission(permission);
	}


	public static void removePermission(Ranks rank, String permission){
		getRankPermissions(rank).removePermission(permission);
	}

	public static void removePermission(Ranks rank, Permission permission){
		removePermission(rank, permission.getName());
	}


	public static void updatePermissions(Ranks rank){
		RankPermissionsConfig rcfg = new RankPermissionsConfig(rank);
		addPermission(rank, rcfg.getPermissions());
	}
	
	public static void updateAllPermissions(){
		for (Ranks rank: Ranks.values()){
			updatePermissions(rank);
		}
	}

	public static RankPermissions getRankPermissions(Ranks rank){
		if (!rankperms.containsKey(rank)) rankperms.put(rank, new RankPermissions(rank));
		return rankperms.get(rank);
	}
}
