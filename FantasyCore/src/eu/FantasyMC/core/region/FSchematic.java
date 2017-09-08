package eu.FantasyMC.core.region;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import eu.FantasyMC.core.main.FantasyMCCore;
import eu.FantasyMC.core.utils.Config;

public class FSchematic {

	private int task, cur = 0;

	private final Map<FBlock, FVector> blocks = new HashMap<>();
	private final List<FBlock> fblocks = new ArrayList<>();
	private final List<FVector> fvector = new ArrayList<>();

	private Config c;
	private int length, width, height;
	public FSchematic(String name){
		this.c = new Config("ArenaSchematics", name);
		this.length = c.getConfig().getInt("length", 0);
		this.width = c.getConfig().getInt("width", 0);
		this.height = c.getConfig().getInt("height", 0);
	}

	public void paste(Location location){
		Location loc = new Location(location.getWorld(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
		Bukkit.broadcastMessage("" + blocks.size());
		if (isBuilding()) return;
		else task = Bukkit.getScheduler().scheduleSyncRepeatingTask(FantasyMCCore.getInstance(), new Runnable() {

			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				int now = 0;
				while (now < 5){
					if (cur < blocks.size()){
						FBlock b = fblocks.get(cur);
						FVector v = fvector.get(cur);
						loc.clone().add(v.getX(), v.getY(), v.getZ()).getChunk().load(true);

						Block bl = loc.getWorld().getBlockAt(loc.clone().add(v.getX(), v.getY(), v.getZ()));

						bl.setTypeIdAndData(b.getType().getId(), b.getData(), true);

						now++;
						cur++;
					} else stop();
				}
			}
		}, 10, 0);
	}

	public void stop(){
		Bukkit.getScheduler().cancelTask(task);
		cur = 0;
	}

	public boolean isBuilding(){
		return Bukkit.getScheduler().isCurrentlyRunning(task);
	}

	@SuppressWarnings("deprecation")
	public void save(FSelection sel){
		c.set("length", null);
		c.set("width", null);
		c.set("height", null);
		c.set("blocks", null);
		List<FVector> fv = sel.getFVector();
		int blocks = 0;

		for (Block b : sel.getBlocks()){
			if (b.getType() != Material.AIR){
				FBlock block = new FBlock(b.getType(), b.getData());
				c.set("blocks." + fv.get(blocks).toString(), block.toString());
			}
			blocks++;

		}
		c.set("length", sel.getLength());
		c.set("width", sel.getWidth());
		c.set("height", sel.getHeight());
		c.save();
	}



	public void load(){
		int current = 0;
		Bukkit.broadcastMessage("§9" + getBlocks().size());
		for (int y = 0; y < height; y++){
			for (int x = 0; x < length; x++){
				for (int z = 0; z < width; z++){

					if (!getBlocks().isEmpty() && current < getBlocks().size()){
						FBlock b = new FBlock(getBlocks().get(current));
						FVector v = new FVector(x, y, z);
						blocks.put(b, v);
						fblocks.add(b);
						fvector.add(v);
						current++;
					}
				}
			}
		}

	}

	private List<String> getBlocks(){
		if (c.getConfig().getStringList("blocks") != null){
			return c.getConfig().getStringList("blocks");
		} return new ArrayList<String>();
	}
}
