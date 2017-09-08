package eu.FantasyMC.core.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import eu.FantasyMC.core.player.PlayerData;
import eu.FantasyMC.core.ranks.RankPlayers;
import eu.FantasyMC.core.ranks.RankTags;

public class JoinListener implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		e.setJoinMessage(null);
		RankPlayers.register(e.getPlayer());
		Player p = e.getPlayer();
		PlayerData pdata = new PlayerData(p.getUniqueId());
		pdata.updatePlayerName(p.getName());
		RankPlayers.setPrimaryRank(p.getUniqueId(), pdata.getPrimaryRank());
		RankPlayers.setSecondaryRank(p.getUniqueId(), pdata.getSecondaryRank());
		RankTags rt = new RankTags(p);
		rt.updateToAll();
		
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e){
		e.setQuitMessage(null);
		RankPlayers.unregister(e.getPlayer());
	}
}
