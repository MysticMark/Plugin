package eu.FantasyMC.core.customEvents.arena;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import eu.FantasyMC.core.arena.ArenaManagers.ArenaSign;


public class ArenaSignClickEvent extends Event implements Cancellable {

    private final ArenaSign s;
    private final Player p;
    private boolean cancelled;

    public ArenaSignClickEvent(Player p, ArenaSign s) {
    	this.s = s;
    	this.p = p;
    }
    
    public Player getPlayer(){
    	return this.p;
    }
    
    public ArenaSign getSign(){
    	return this.s;
    }
    
    private static final HandlerList HANDLERS = new HandlerList();

    @Override
	public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

	@Override
	public boolean isCancelled() {
		return this.cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

}