package eu.FantasyMC.core.arena.GameManager;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;

import eu.FantasyMC.core.arena.ArenaManagers.ArenaSign;
import eu.FantasyMC.core.utils.Config;

public class GameTypeManager {

	private final List<GameType> types = new ArrayList<>();
	private final Config c = new Config("gametypes");
	
	public GameTypeManager(){}
	
	public void register(String name){
		types.add(new GameType(name));
	}
	
	
	public void unregister(String name){
		if (get(name) != null) types.remove(get(name));
	}
	
	public GameType get(String name){
		for (GameType type : types){
			if (type.getName().equalsIgnoreCase(name)) return type;
		} return null;
	}
	
	public List<GameType> getTypes(){
		return this.types;
	}
	
	
	public void save(){
		for (GameType type : types){
			List<String> list = new ArrayList<>();
			for (ArenaSign sign : type.getSigns()){
				list.add(sign.toString());
			}
			c.set(type.getName() + ".signs", list);
			c.set(type.getName() + ".active", type.isActive());
			c.set(type.getName() + ".players.max", type.getMaxPlayers());
			c.set(type.getName() + ".players.min", type.getMinPlayers());
		}
		c.save();
	}
	
	public void load(){
		for (String str : c.getConfig().getKeys(false)){
			GameType type = get(str);
			if (type == null) register(str);
			type = get(str);
			for (String signs : c.getConfig().getStringList(str + ".signs")){
				String[] s = signs.split(",");
				ArenaSign sign = new ArenaSign(Bukkit.getWorld(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2]), Integer.parseInt(s[3]));
				sign.setGameType(type);
				type.addSign(sign);
			}
			type.setActive(c.getConfig().getBoolean(str + ".active", false));
			type.setMaxPlayers(c.getConfig().getInt(str + ".players.max", 2));
			type.setMinPlayers(c.getConfig().getInt(str + ".players.min", 2));
		}
	}
}
