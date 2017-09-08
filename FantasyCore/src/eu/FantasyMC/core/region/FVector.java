package eu.FantasyMC.core.region;

public class FVector {

	private int x, y, z;
	
	public FVector(int x, int y, int z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public FVector(FVector vector){
		this.x = vector.getX();
		this.y = vector.getY();
		this.z = vector.getZ();
	}
	
	public int getX(){
		return this.x;
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	public int getY(){
		return this.y;
	}
	
	public void setY(int y){
		this.y = y;
	}
	
	public int getZ(){
		return this.z;
	}
	
	public void setZ(int z){
		this.z = z;
	}
	
	@Override
	public String toString(){
		return x + "," + y + "," + z;
	}
}
