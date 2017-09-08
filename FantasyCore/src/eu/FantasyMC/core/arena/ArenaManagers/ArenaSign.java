package eu.FantasyMC.core.arena.ArenaManagers;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Sign;

import eu.FantasyMC.core.arena.GameManager.GameType;

public class ArenaSign {

	private Location loc;
	private GameType type = null;
	private SignState state = SignState.SEARCHING;
	private Arena a = null;
	
	private boolean active = false;
	
	public ArenaSign(World w, int x, int y, int z){
		this.loc = new Location(w, x, y, z);
	}
	
	public ArenaSign(Location loc){
		this.loc = loc;
	}
	
	public void setGameType(GameType type){
		this.type = type;
	}
	
	public GameType getGameType(){
		return this.type;
	}
	
	public Location getLocation(){
		return loc;
	}
	
	public void setState(SignState state){
		update();
		this.state = state;
	}
	
	public SignState getState(){
		return this.state;
	}
	
	public void update(){
		Sign s = (Sign) loc.getBlock().getState();
		if (this.state == SignState.DISABLED){
			s.setLine(0, "");
			s.setLine(1, "§4§lThis game is");
			s.setLine(2, "§4§lDisabled");
			s.setLine(3, "");
		} else if (this.state == SignState.SEARCHING){
			s.setLine(0, "");
			s.setLine(1, "§d§lSearching");
			s.setLine(2, "§d§lfor games!");
			s.setLine(3, "");
		} else{
			s.setLine(0, "§5Join Game");
			s.setLine(1, "§a" + a.getPlayers().size() + "§2/§a" + a.getMaxPlayers());
			s.setLine(2, a.getState().getName());
			s.setLine(3, "§l" + getGameType().getName());
		} s.update(true);
	}
	
	public void setArena(Arena a){
		this.a = a;
	}
	
	public Arena getArena(){
		return this.a;
	}
	
	public boolean hasArena(){
		return this.a != null;
	}
	
	public void setActive(boolean active){
		this.active = active;
	}
	
	public boolean isActive(){
		return this.active;
	}
	
	@Override
	public String toString(){
		return loc.getWorld().getName() + "," + loc.getBlockX() + "," + loc.getBlockY() + "," + loc.getBlockZ();
	}
	
	public enum SignState{
		DISABLED,
		SEARCHING,
		FOUND;
	}
}
