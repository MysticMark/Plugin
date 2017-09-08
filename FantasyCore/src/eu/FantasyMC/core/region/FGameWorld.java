package eu.FantasyMC.core.region;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import eu.FantasyMC.core.main.FantasyMCCore;

public class FGameWorld {

	private String name, gamename;
	
	public FGameWorld(String gamename){
		this.name = gamename;
		this.gamename = gamename;
	}
	
	public void load(String mapname){
		File source = new File(FantasyMCCore.getInstance().getDataFolder() + File.separator + "Maps" + File.separator + gamename + File.separator + mapname);
		if (exists(name)){
			FantasyMCCore.getInstance().getWM().unloadWorld(Bukkit.getWorld(name));
			FantasyMCCore.getInstance().getWM().deleteWorld(new File(Bukkit.getWorldContainer() + File.separator + name));
		}
		File target = new File(Bukkit.getWorldContainer() + File.separator + name);
		FantasyMCCore.getInstance().getWM().copyWorld(source, target);
		WorldCreator wc = new WorldCreator(name);
		World w = wc.createWorld();
		w.save();
		w.setAutoSave(false);
		
		
	}
	
	private boolean exists(String wname){
		for (World w : Bukkit.getWorlds()){
			if (w.getName().equalsIgnoreCase(wname)) return true;
		}
		return false;
	}
}
