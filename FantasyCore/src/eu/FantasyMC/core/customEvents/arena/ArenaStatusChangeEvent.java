package eu.FantasyMC.core.customEvents.arena;
import java.util.UUID;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import eu.FantasyMC.core.ranks.RankData.Ranks;


public class ArenaStatusChangeEvent extends Event {

    private final UUID id;
    private final Ranks rank1, rank2;

    public ArenaStatusChangeEvent(UUID id, Ranks rank1, Ranks rank2) {
        this.id = id;
        this.rank1 = rank1;
        this.rank2 = rank2;
    }
    
    public UUID getUUID(){
		return this.id;
    }
    
    public Ranks getPrimaryRank(){
		return rank1;
    }
    
    public Ranks getSecondaryRank(){
		return rank2;
    }
    
    private static final HandlerList HANDLERS = new HandlerList();

    @Override
	public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

}