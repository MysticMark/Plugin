package eu.FantasyMC.core.arena.GameManager;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

import eu.FantasyMC.core.arena.ArenaManagers.Arena;
import eu.FantasyMC.core.arena.ArenaManagers.ArenaSign;
import eu.FantasyMC.core.arena.ArenaManagers.ArenaSign.SignState;
import eu.FantasyMC.core.main.FantasyMCCore;

public class GameType {

	private int max = 2, min = 2;
	private String name;
	private boolean active = false;
	
	private List<ArenaSign> signs = new ArrayList<>();
	
	
	public GameType(String name){
		this.name = name;
	}
	
	public void setMaxPlayers(int max){
		this.max = max;
	}
	
	public int getMaxPlayers(){
		return this.max;
	}
	
	public void setMinPlayers(int min){
		this.min = min;
	}
	
	public int getMinPlayers(){
		return this.min;
	}
	
	public List<ArenaSign> getSigns(){
		return this.signs;
	}
	
	public void addSign(ArenaSign sign){
		signs.add(sign);
		sign.update();
	}
	
	public void removeSign(Location loc){
		ArenaSign s = null;
		for (ArenaSign sign : signs){
			if (sign.getLocation().equals(loc)) s = sign;
		}
		if (s != null) {
			FantasyMCCore.getInstance().getAM().removeArena(s.getArena());
			signs.remove(s);
		}
	}
	
	public void disableSigns(){
		for (ArenaSign sign : getSigns()){
			if (sign.hasArena()) sign.getArena().kickPlayers("§cThe game you were in has been disabled so you have been returned to the lobby!");
			FantasyMCCore.getInstance().getAM().removeArena(sign.getArena());
			sign.setActive(false);
			sign.setState(SignState.DISABLED);
			sign.update();
		}
	}
	
	public void enableSigns(){
		for (ArenaSign sign : getSigns()){
			sign.setState(SignState.SEARCHING);
			sign.update();
		}
	}
	
	public void updateSigns(){
		for (ArenaSign sign : getSigns()){
			sign.update();
		}
	}
	
	public void updateArenas(){
		for (ArenaSign sign : getSigns()){
			Arena a = sign.getArena();
			if (a != null) {
				a.setMaxPlayers(this.getMaxPlayers());
				a.setMinPlayers(this.getMinPlayers());
			}
		}
	}
	
	public void setActive(boolean active){
		this.active = active;
	}
	
	public boolean isActive(){
		return this.active;
	}
	
	public String getName(){
		return this.name;
	}
	
}
