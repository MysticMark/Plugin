package eu.FantasyMC.core.region;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import eu.FantasyMC.core.arena.GameManager.GameType;
import eu.FantasyMC.core.main.FantasyMCCore;

public class FTemplateWorld {
	
	private String name;
	private World w = null;
	public FTemplateWorld(){
		this.name = "templateWorld";
	}
	
	public void create(){
		File source = new File(FantasyMCCore.getInstance().getDataFolder() + File.separator + "Maps" + File.separator + "template_void");
		if (exists(name)){
			FantasyMCCore.getInstance().getWM().unloadWorld(Bukkit.getWorld(name));
			FantasyMCCore.getInstance().getWM().deleteWorld(new File(Bukkit.getWorldContainer() + File.separator + name));
		}
		File target = new File(Bukkit.getWorldContainer() + File.separator + name);
		FantasyMCCore.getInstance().getWM().copyWorld(source, target);
		
		WorldCreator wc = new WorldCreator(name);
		w = wc.createWorld();
		w.getChunkAt(0, 0).load(true);
		w.getBlockAt(0, 70, 0).setType(Material.BEDROCK);
		w.setSpawnLocation(0, 71, 0);
		w.setAutoSave(false);
	}
	
	public void create(GameType type, String mapname){
		File source = new File(FantasyMCCore.getInstance().getDataFolder() + File.separator + "Maps" + File.separator + type.getName() + File.separator + mapname);
		if (exists(name)){
			FantasyMCCore.getInstance().getWM().unloadWorld(Bukkit.getWorld(name));
			FantasyMCCore.getInstance().getWM().deleteWorld(new File(Bukkit.getWorldContainer() + File.separator + name));
		}
		File target = new File(Bukkit.getWorldContainer() + File.separator + name);
		FantasyMCCore.getInstance().getWM().copyWorld(source, target);
		WorldCreator wc = new WorldCreator(name);
		w = wc.createWorld();
		w.setAutoSave(false);
	}
	
	public boolean delete(){
		if (exists(name)){
		FantasyMCCore.getInstance().getWM().unloadWorld(Bukkit.getWorld(name));
		return FantasyMCCore.getInstance().getWM().deleteWorld(new File(Bukkit.getWorldContainer() + File.separator + name));
		} return true;
	}
	
	public void save(){
		
	}
	
	public World getWorld(){
		return this.w;
	}
	
	private boolean exists(String wname){
		for (World w : Bukkit.getWorlds()){
			if (w.getName().equalsIgnoreCase(wname)) return true;
		}
		return false;
	}
}
