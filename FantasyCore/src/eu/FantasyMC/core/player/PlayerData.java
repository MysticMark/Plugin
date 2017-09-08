package eu.FantasyMC.core.player;

import java.util.UUID;

import eu.FantasyMC.core.ranks.RankData;
import eu.FantasyMC.core.ranks.RankData.Ranks;
import eu.FantasyMC.core.utils.Config;

public class PlayerData {

	private final Config c;
	
	public PlayerData(UUID id){
		this.c = new Config("playerdata", id.toString());
	}
	
	public void updatePlayerName(String name){
		c.set("name", name);
		c.save();
	}
	
	public void setVanish(boolean vanish){
		c.set("vanish", vanish);
		c.save();
	}
	
	public boolean getVanish(){
		try {
			return c.getConfig().getBoolean("vanish");
		} catch (Exception e){
			return false;
		}
	}
	
	public void setPrimaryRank(Ranks rank){
		c.set("ranks.rank1", rank.getName());
		c.save();
	}
	
	public void setSecondaryRank(Ranks rank){
		c.set("ranks.rank2", rank.getName());
		c.save();
	}
	
	public Ranks getPrimaryRank(){
		return getRank("ranks.rank1", Ranks.MEMBER);
	}
	
	public Ranks getSecondaryRank(){
		return getRank("ranks.rank2", Ranks.MEMBER);
	}
	
	private Ranks getRank(String path, Ranks defaultRank){
		return c.getConfig().isSet(path) ? RankData.getRank(c.getConfig().getString(path).toUpperCase()): defaultRank;
	}
}
