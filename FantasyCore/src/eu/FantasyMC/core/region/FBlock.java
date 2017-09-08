package eu.FantasyMC.core.region;

import org.bukkit.Material;

public class FBlock {

	private byte data;
	private Material type;
	
	public FBlock(Material type, byte data){
		this.data = data;
		this.type = type;
	}
	
	public FBlock(String name){
		try {
			Material arg0 = Material.valueOf(name.split(",")[0]);
			byte arg1 = Byte.parseByte(name.split(",")[1]) ;
			this.type = arg0;
			this.data = arg1;
		} catch (Exception e){
			this.type = Material.AIR;
			this.data = (byte) 0;
			
		}
	}
	
	public void setType(Material type){
		this.type = type;
	}
	
	public Material getType(){
		return this.type;
	}
	
	public void setData(byte data){
		this.data = data;
	}
	
	public byte getData(){
		return this.data;
	}
	
	@Override
	public String toString(){
		return type.toString() + "," + data;
	}
}
