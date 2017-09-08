package eu.FantasyMC.core.ranks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import eu.FantasyMC.core.ranks.RankData.Ranks;
import eu.FantasyMC.core.utils.Config;

public class RankPermissionsConfig {

	private final Ranks rank;
	private final Config c;
	public RankPermissionsConfig(Ranks rank){
		this.rank = rank;
		c = new Config("Permissions");
		for (Ranks r: Ranks.values()){
			c.setExists("Permissions." + r.getName() + ".permissions", Arrays.asList(new String[]{"g:global", "perm.1", "perm.2"}));
		}
		c.setExists("Global.global", Arrays.asList(new String[]{"perm.g1", "perm.g2"}));
	}


	public List<String> getPermissions(){
		List<String> out = new ArrayList<String>();
		for (String perm : getRawPermissions()){
			if (perm.toLowerCase().startsWith("g:")){
				if (c.getConfig().getStringList("Global." + perm.split(":")[1]) != null){
					for (String global : c.getConfig().getStringList("Global." + perm.split(":")[1])){
						out.add(global);
					}
				} else break;
			}
			else {
				out.add(perm);
			}
		} 
		return out;
	}

	private List<String> getRawPermissions(){
		return c.getConfig().getStringList("Permissions." + rank.getName() + ".permissions");
	}
}
