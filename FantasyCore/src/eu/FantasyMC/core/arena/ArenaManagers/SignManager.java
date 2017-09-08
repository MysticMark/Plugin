package eu.FantasyMC.core.arena.ArenaManagers;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import eu.FantasyMC.core.arena.ArenaManagers.ArenaSign.SignState;
import eu.FantasyMC.core.arena.GameManager.GameType;
import eu.FantasyMC.core.main.FantasyMCCore;

public class SignManager {
	//TODO: Een arena opvragen als bordje leeg is!
	
	public SignManager(){
	}
	
	public void registerSign(Location loc, GameType type){
		//signs.put(key, value);
	}
	
	public boolean removeSign(Location loc){
		GameType gt = null;
		for (GameType type : FantasyMCCore.getInstance().getGTM().getTypes()){
			for (ArenaSign sign : type.getSigns()){
				if (sign.getLocation().equals(loc.getBlock().getLocation())){
					gt = type;
					break;
				}
			}
		}
		if (gt != null) {
			gt.removeSign(loc);
			return true;
		}
		
		return false;
	}
	
	public void init(){
		Bukkit.getScheduler().scheduleSyncRepeatingTask(FantasyMCCore.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				for (GameType type : FantasyMCCore.getInstance().getGTM().getTypes()){
					for (ArenaSign sign : type.getSigns()){
						update(type, sign);
					}
				}
				
			}
		}, 10, 100);
	}
	
	public void update(GameType type, ArenaSign sign){
		if (!type.isActive()) {
			sign.setState(SignState.DISABLED);
			//sign.getArena().kickPlayers();
			sign.update();
		}
		else if (!sign.isActive()){
			Arena a = FantasyMCCore.getInstance().getAM().registerArena();
			if (a == null){
				sign.setState(SignState.SEARCHING);
			} else {
				a.setMaxPlayers(type.getMaxPlayers());
				a.setMinPlayers(type.getMinPlayers());
				sign.setArena(a);
				sign.setActive(true);
				sign.setState(SignState.FOUND);
				a.setGameType(sign.getGameType());
				a.startWaiting();
			}
			sign.update();
		}
	}
	
	public ArenaSign getSign(Location loc){
		for (GameType type : FantasyMCCore.getInstance().getGTM().getTypes()){
			for (ArenaSign sign : type.getSigns()){
				if (sign.getLocation().equals(loc.getBlock().getLocation())){
					return sign;
				}
			}
		}
		
		return null;
	}
	
	public ArenaSign getSign(Arena a){
		for (GameType type : FantasyMCCore.getInstance().getGTM().getTypes()){
			for (ArenaSign sign : type.getSigns()){
				if (sign.getArena() == a){
					return sign;
				}
			}
		}
		
		return null;
	}
}
