package eu.FantasyMC.core.ranks;

import java.util.ArrayList;
import java.util.List;

import eu.FantasyMC.core.ranks.RankData.Ranks;

public class RankPermissions {

	private final ArrayList<String> permissions;
	
	public RankPermissions(Ranks rank){
		permissions = new ArrayList<String>();
	}
	
	public void removePermission(String permission){
		permissions.remove(permission);
	}
	
	public void addPermission(String permission){
		permissions.add(permission);
	}
	
	public void addPermission(List<String> permission){
		permissions.addAll(permission);
	}
	
	public ArrayList<String> getPermissions(){
		return permissions;
	}
	
	
}
