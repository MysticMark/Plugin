package eu.FantasyMC.core.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import eu.FantasyMC.core.customEvents.RankChangeEvent;
import eu.FantasyMC.core.player.PlayerData;
import eu.FantasyMC.core.ranks.RankPlayers;
import eu.FantasyMC.core.ranks.RankTags;
import eu.FantasyMC.core.utils.PlayerUtils;

public class RankListener implements Listener {

	@EventHandler
	public void onChange(RankChangeEvent e){
		Player p = Bukkit.getPlayer(e.getUUID());
		if (p != null){
			RankPlayers.setPrimaryRank(p.getUniqueId(), e.getPrimaryRank());
			RankPlayers.setSecondaryRank(p.getUniqueId(), e.getSecondaryRank());
			PlayerData pdata = new PlayerData(p.getUniqueId());
			pdata.setPrimaryRank(e.getPrimaryRank());
			pdata.setSecondaryRank(e.getSecondaryRank());
			RankTags rt = new RankTags(p);
			rt.updateToAll();
			PlayerUtils.updateVanish(p);
			RankPlayers.calculate(p);
		}
	}
}
