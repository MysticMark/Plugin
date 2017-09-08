package eu.FantasyMC.core.ranks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import eu.FantasyMC.core.ranks.RankData.Ranks;

public class RankTags {

	private final Scoreboard sb;
	private final Player p;
	public RankTags(Player p){
		this.p = p;
		this.sb = p.getScoreboard();
		for (Ranks r : Ranks.values()){
			Team t = sb.getTeam(r.getTabPos());
			if (t == null) {
				t = sb.registerNewTeam(r.getTabPos());
				t.setDisplayName(r.getDisplayname());
			}
			t.setPrefix(r.getTabPrefix());
		}
	}

	@SuppressWarnings("deprecation")
	public void updateToAll(){
		for (Player online : Bukkit.getOnlinePlayers()){
			RankTags rt = new RankTags(online);
			rt.sb.getTeam(RankPlayers.getPrimaryRank(p.getUniqueId()).getTabPos()).addPlayer(p);
			
		}
	}

	@SuppressWarnings("deprecation")
	public void updateToSelf(){
		for (Player online : Bukkit.getOnlinePlayers()){
			Ranks pr = RankPlayers.getPrimaryRank(online.getUniqueId());
			sb.getTeam(pr.getTabPos()).addPlayer(online);
		}
	}

	public void updateToExact(Player p){

	}
}
