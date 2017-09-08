package eu.FantasyMC.core.ranks;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import eu.FantasyMC.core.main.FantasyMCCore;
import eu.FantasyMC.core.ranks.RankData.Ranks;

public class RankPlayers {
	private static Field pField;
	private static HashMap<UUID, Ranks> rankp = new HashMap<UUID, Ranks>();
	private static HashMap<UUID, Ranks> ranks = new HashMap<UUID, Ranks>();
	private static HashMap<UUID, PermissionAttachment> attachment = new HashMap<UUID, PermissionAttachment>();

	public static void setPrimaryRank(UUID id, Ranks rank){
		rankp.put(id, rank);
	}

	public static void setSecondaryRank(UUID id, Ranks rank){
		ranks.put(id, rank);
	}

	public static Ranks getPrimaryRank(UUID id){
		return rankp.containsKey(id) ? rankp.get(id) : Ranks.MEMBER;
	}

	public static Ranks getSecondaryRank(UUID id){
		return ranks.containsKey(id) ? ranks.get(id) : Ranks.MEMBER;
	}

	public static boolean hasSecondary(UUID id){
		return getSecondaryRank(id) != Ranks.MEMBER;
	}
	
	public static void register(Player p){
		if (attachment.containsKey(p.getUniqueId())){
			unregister(p);
		}
	    PermissionAttachment att = p.addAttachment(FantasyMCCore.getInstance());
	    attachment.put(p.getUniqueId(), att);
	    calculate(p);
	}

	public static void unregister(Player p){
		if (attachment.containsKey(p.getUniqueId())){
			try {
				p.removeAttachment(attachment.get(p.getUniqueId()));
			}
			catch (IllegalArgumentException ex) {ex.printStackTrace();}
			attachment.remove(p.getUniqueId());
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void calculate(Player p){
		if (p == null) {
			return;
		}
		PermissionAttachment att = attachment.get(p.getUniqueId());
		if (att == null)
		{
			return;
		}

		Map<String, Boolean> perms = new LinkedHashMap();

		Ranks r1 = RankPlayers.getPrimaryRank(p.getUniqueId());

		Ranks r2 = RankPlayers.getPrimaryRank(p.getUniqueId());
		RankPermissions rank1 = RankPermissionsManager.getRankPermissions(r1);
		RankPermissions rank2 = RankPermissionsManager.getRankPermissions(r2);
		if (RankPlayers.hasSecondary(p.getUniqueId())){
			for (String permissions : rank2.getPermissions()){
				if (permissions.startsWith("-")) perms.put(permissions.replaceFirst("-", ""), false);
				else perms.put(permissions, true);
			}
		}
		for (String permissions : rank1.getPermissions()){
			if (permissions.startsWith("-")) perms.put(permissions.replaceFirst("-", ""), false);
			else perms.put(permissions, true);
		}
		Map<String, Boolean> dest = reflectMap(att);
		dest.clear();
		dest.putAll(perms);

		p.recalculatePermissions();
	}

	private <K, V> void put(Map<K, V> dest, K key, V value)
	{
		dest.remove(key);
		dest.put(key, value);
	}

	@SuppressWarnings("unused")
	private <K, V> void putAll(Map<K, V> dest, Map<K, V> src)
	{
		for (Map.Entry<K, V> entry : src.entrySet()) {
			put(dest, entry.getKey(), entry.getValue());
		}
	}

	@SuppressWarnings("unchecked")
	private static Map<String, Boolean> reflectMap(PermissionAttachment attachment)
	{
		try
		{
			if (pField == null)
			{
				pField = PermissionAttachment.class.getDeclaredField("permissions");
				pField.setAccessible(true);
			}
			return (Map<String, Boolean>)pField.get(attachment);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}
	
	  protected void refreshForPlayer(UUID player)
	  {
	    
	    Player onlinePlayer = Bukkit.getServer().getPlayer(player);
	    if (onlinePlayer != null) {
	      calculate(onlinePlayer);
	    }
	  }
}
