package eu.FantasyMC.core.arena.ArenaManagers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import eu.FantasyMC.core.json.JSONEvent.HoverEvent;
import eu.FantasyMC.core.json.JSONMessageBuilder;

public class ArenaManager {
	
	//private final Map<String, Arena> active = new HashMap<>();
	private final List<Arena> arena = new ArrayList<>();
	private int maxGames = 200;
	
	public ArenaManager(){
		
	}
	
	public Arena registerArena(){
		Random r = new Random();
		boolean bl = true;
		while (bl && arena.size() < maxGames){
			int i = r.nextInt(9999);
			if (!exists("" + i)){
				Arena a = new Arena("" + i);
				arena.add(a);
				bl = false;
				return a;
			}
		}
		System.out.println("No new arena's can be created!");
		return null;
	}
	
	public void removeArena(Arena a){
		if (a != null) {
			arena.remove(a);
			a = null;
		}
	}
	
	public String list(){
		String out = "";
		for (Arena a : arena){
			out += ChatColor.getLastColors(a.getState().getName()) + a.getName() + "§f, ";
		}
		out = out.trim();
		out = out.substring(0, out.length() -1);
		return out;
	}
	
	public String listJSON(){
		JSONMessageBuilder builder = new JSONMessageBuilder("");
		for (int i = 0; i < arena.size(); i++){
			Arena a = arena.get(i);
			builder.addMessage(ChatColor.getLastColors(a.getState().getName()) + a.getName()).setHoverEvent(HoverEvent.SHOW_TEXT,
					"§f[§bID: §6" + a.getName() + "§f]"
					+ "\n\n" +
					"§bGameState: §f§l" + a.getState().getName() 
					+ "\n" +
					"§bGameType: §f§l" + a.getGameType().getName() 
					+ "\n" +
					"§bMaxPlayers: §f§l" + a.getMaxPlayers()
					+ "\n" +
					"§bMinPlayers: §f§l" + a.getMinPlayers()
					+ "\n" +
					"§bCountdown: §f§l" + a.getCountdown()
					+ "\n" +
					"§bPlayers §f[" + a.getPlayers().size() + "]§b: §f" + a.getPlayersList()
					);
			if (i < (arena.size() -1)) builder.addMessage("§f, ");
			//out += ChatColor.getLastColors(a.getState().getName()) + a.getName() + "§f, ";
		}
		return builder.build();
	}
	
	
	
	private boolean exists(String name){
		for (Arena a : arena){
			if (a.getName().equalsIgnoreCase(name)) return true;;
		}
		return false;
	}
	
	public Arena getArena(Player p){
		for (Arena a : arena){
			if (a.getPlayers().contains(p)) return a;
		}
		return null;
	}
	
	public Arena getArena(String name){
		for (Arena a : arena){
			if (a.getName().equalsIgnoreCase(name)) return a;
		}
		return null;
	}
	
	public void unloadAll(){
		for (Arena a : arena){
			a.kickPlayers("§4SERVER RELOAD §Call games have been stoped! and you are send back to the lobby!");
		}
	}
	
	
	public enum GameState{
		LOBBY("§9Lobby"),
		STARTING("§aStarting"),
		INGAME("§4Ingame");
		
		private String name;
		private GameState(String name) {
			this.name = name;
		}
		
		public String getName(){
			return this.name;
		}
	}
	
	
	
	
}
