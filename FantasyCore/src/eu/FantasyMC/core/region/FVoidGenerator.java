package eu.FantasyMC.core.region;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

public class FVoidGenerator
  extends ChunkGenerator
{
  @Override
public byte[][] generateBlockSections(World world, Random random, int x, int z, BiomeGrid biomes)
  {
    return new byte[world.getMaxHeight() / 16][];
  }
  
  @Override
public Location getFixedSpawnLocation(World world, Random random)
  {
    return new Location(world, 0.0D, world.getMaxHeight() / 2.0D, 0.0D);
  }
}
