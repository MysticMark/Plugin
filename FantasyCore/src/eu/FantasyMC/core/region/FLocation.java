package eu.FantasyMC.core.region;

import org.bukkit.Location;
import org.bukkit.World;

public class FLocation {
	private Double x, y, z;
	private Float pitch, yaw;
	
	public FLocation(Location loc){
		this.x = loc.getX();
		this.y = loc.getY();
		this.z = loc.getZ();
		this.pitch = loc.getPitch();
		this.yaw = loc.getYaw();
	}
	
	public FLocation(Integer x, Integer y, Integer z, Float pitch, Float yaw){
		this.x = x.doubleValue();
		this.y = y.doubleValue();
		this.z = z.doubleValue();
		this.pitch = pitch;
		this.yaw = yaw;
	}
	
	public FLocation(Double x, Double y, Double z, Float pitch, Float yaw){
		this.x = x;
		this.y = y;
		this.z = z;
		this.pitch = pitch;
		this.yaw = yaw;
	}
	
	public FLocation(String str){
		String[] s = str.split(",");
		this.x = Double.parseDouble(s[0]);
		this.y = Double.parseDouble(s[1]);
		this.z = Double.parseDouble(s[2]);
		this.pitch = Float.parseFloat(s[3]);;
		this.yaw = Float.parseFloat(s[4]);;
	}
	
	public double getX(){
		return this.x;
	}
	
	public double getY(){
		return this.y;
	}
	
	public double getZ(){
		return this.z;
	}
	
	public int getBlockX(){
		return Integer.valueOf(x.intValue());
	}
	
	public int getBlockY(){
		return Integer.valueOf(y.intValue());
	}
	
	public int getBlockZ(){
		return Integer.valueOf(z.intValue());
	}
	
	@Override
	public String toString(){
		return x + "," + y + "," + z + "," + pitch + "," + yaw;
	}
	
	public String toStringBlock(){
		return getBlockX() + ", " + getBlockY() + ", " + getBlockZ();
	}
	
	public Location toLocation(World w){
		return new Location(w, x, y, z, pitch, yaw);
	}
}
