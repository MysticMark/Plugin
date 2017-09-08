package eu.FantasyMC.core.region;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import eu.FantasyMC.core.utils.FFileUtil;

public class FWorldManager {
	
	public FWorldManager(){}
	
	public void unloadWorld(World world) {
		for (Player p : world.getPlayers()){
			if (p.isDead()) p.spigot().respawn();
			p.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
		}
	        Bukkit.getServer().unloadWorld(world, true);
	}
	
	public boolean deleteWorld(File path) {
		return FFileUtil.deleteFile(path);
	}
	
	public void copyWorld(File source, File target){
		FFileUtil.copyFile(source, target, new ArrayList<String>(Arrays.asList("uid.dat", "session.dat")));
	}
}
