package eu.FantasyMC.core.region;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;

public class FSelection {

	private Location min = null, max = null;

	public FSelection(/*Selection sel*/){
		this.min = Bukkit.getWorld("").getSpawnLocation(); //sel.getMinimumPoint();
		this.max = Bukkit.getWorld("").getSpawnLocation();
	}

	public int getLength(){
		return max.getBlockX() - min.getBlockX() + 1;
	}

	public int getWidth(){
		return max.getBlockZ() - min.getBlockZ() + 1;
	}

	public int getHeight(){
		return max.getBlockY() - min.getBlockY() + 1;
	}

	public Location getMinPoint(){
		return this.min;
	}

	public Location getMaxPoint(){
		return this.max;
	}

	public int getMinX(){
		return min.getBlockX();
	}

	public int getMaxX(){
		return max.getBlockX();
	}

	public int getMinY(){
		return min.getBlockY();
	}

	public int getMaxY(){
		return max.getBlockY();
	}

	public int getMinZ(){
		return min.getBlockZ();
	}

	public int getMaxZ(){
		return max.getBlockZ();
	}

	public List<Block> getBlocks(){
		List<Block> blocks = new ArrayList<Block>();
		for (int y = getMinY(); y <= getMaxY(); y++){
			for (int x = getMinX(); x <= getMaxX(); x++){
				for (int z = getMinZ(); z <= getMaxZ(); z++){
					blocks.add(min.getWorld().getBlockAt(x, y, z));
				}
			}
		}
		return blocks;
	}

	public List<Location> getLocations(){
		List<Location> blocks = new ArrayList<Location>();
		for (int y = getMinY(); y <= getMaxY(); y++){
			for (int x = getMinX(); x <= getMaxX(); x++){
				for (int z = getMinZ(); z <= getMaxZ(); z++){
					blocks.add(new Location(min.getWorld(), x, y, z));
				}
			}
		}
		return blocks;
	}

	public List<FVector> getFVector(){
		List<FVector> v = new ArrayList<>();
		for (int y = 0; y < getHeight(); y++){
			for (int x = 0; x < getLength(); x++){
				for (int z = 0; z < getWidth(); z++){
					v.add(new FVector(x, y, z));
				}
			}
		}
		return v;
	}
}
