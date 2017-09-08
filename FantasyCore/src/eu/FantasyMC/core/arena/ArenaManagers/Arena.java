package eu.FantasyMC.core.arena.ArenaManagers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import eu.FantasyMC.core.arena.ArenaManagers.ArenaManager.GameState;
import eu.FantasyMC.core.arena.ArenaManagers.ArenaSign.SignState;
import eu.FantasyMC.core.arena.GameManager.GameType;
import eu.FantasyMC.core.json.JSONMessage;
import eu.FantasyMC.core.main.FantasyMCCore;

public class Arena {

	private int maxPlayers = 2, minPlayers = 2;
	private String name;
	private GameType type;
	private int task, countdown = 60;
	private final Integer[] bc;
	private GameState state = GameState.LOBBY;

	private List<Player> players = new ArrayList<>();

	public Arena(String name){
		this.bc = new Integer[]{60, 30, 15, 10, 5, 4, 3, 2, 1};
		this.name = name;
	}

	public void setMaxPlayers(int max){
		this.maxPlayers = max;
	}

	public int getMaxPlayers(){
		return this.maxPlayers;
	}

	public void setMinPlayers(int min){
		this.minPlayers = min;
	}

	public int getMinPlayers(){
		return this.minPlayers;
	}

	public void addPlayer(Player p){
		if (!players.contains(p)) players.add(p);
	}

	public void removePlayer(Player p){
		players.remove(p);
	}

	public List<Player> getPlayers(){
		return this.players;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	public void setState(GameState state){
		//ArenaSign sign = FantasyMCCore.getInstance().getSM().getSign(this);
		this.state = state;
		//if (sign != null) sign.update();
	}

	public GameState getState(){
		return this.state;
	}

	public void sendMessage(String msg){
		for (Player p : players){
			p.sendMessage(msg);
		}
	}

	public void sendAction(String msg){
		for (Player p : players){
			JSONMessage.sendAction(p, msg);
		}
	}

	public void playSound(Sound sound){
		for (Player p : players){
			if (p != null) p.playSound(p.getLocation(), sound, 1.0f, 1.0f);;
		}
	}

	public void setGameType(GameType type){
		this.type = type;
	}

	public GameType getGameType(){
		return this.type;
	}

	public void kickPlayers(String msg) {
		for (Player p : players){
			if (p != null) {
				p.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
				if (msg != null) p.sendMessage(FantasyMCCore.getInstance().getMSG().prefix(msg));
			}
		}
		players.clear();
	}

	public void kickPlayer(Player p, String msg) {
		ArenaSign sign = FantasyMCCore.getInstance().getSM().getSign(this);
		if (p != null) {
			p.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
			if (msg != null) p.sendMessage(FantasyMCCore.getInstance().getMSG().prefix(msg));
		}
		removePlayer(p);
		if (sign != null) sign.update();
	}



	public String getPlayersList(){
		String out = "§cNo players";
		for (Player p : players){
			if (out == "§cNo players") out = "";
			out += "§6" + p.getName() + "§f, ";
		}
		out = out.trim();
		if (out != "") out = out.substring(0, out.length() -1);
		return out;
	}

	@SuppressWarnings("deprecation")
	public void startWaiting(){
		stopTask();
		task = Bukkit.getScheduler().scheduleAsyncRepeatingTask(FantasyMCCore.getInstance(), new Runnable() {
			int waiting = 0;
			@Override
			public void run() {
				if (getPlayers().size() < getMinPlayers()){
					switch (waiting){
					case 0:
						waiting++;
						sendAction("§eWaiting for players §c●§7●●");
						break;
					case 1:
						waiting++;
						sendAction("§eWaiting for players §c●●§7●");
						break;
					case 2:
						waiting++;
						sendAction("§eWaiting for players §c●●●§7");
						break;
					case 3:
						waiting = 0;
						sendAction("§eWaiting for players §f●●●");
						break;
					}
				} else {
					startCountdown();
				}

			}
		}, 0, 10);
	}

	@SuppressWarnings("deprecation")
	public void startCountdown(){
		stopTask();
		this.countdown = 60;
		ArenaSign sign = FantasyMCCore.getInstance().getSM().getSign(this);
		task = Bukkit.getScheduler().scheduleAsyncRepeatingTask(FantasyMCCore.getInstance(), new Runnable() {
			@Override
			public void run() {
				if (players.size() < minPlayers){ 
					startWaiting();
					setState(GameState.LOBBY);
					sign.update();
				}
				else {
					if (countdown > 11 && players.size() >= maxPlayers){
						sendMessage("§9Game is full countdown was set to 10 seconds.\n");
						countdown = 10;
					}
					if (Arrays.asList(bc).contains(countdown)) {
						sendMessage("§cGame starting in: " + countdown);
						playSound(Sound.NOTE_PLING);
						if (countdown == 1) setState(GameState.STARTING);
						sign.update();
					}
					if (countdown == 0) {
						sendMessage("Game Started!");
						playSound(Sound.NOTE_BASS);
						stopTask();

						setState(GameState.INGAME);
						sign.update();

						Bukkit.getScheduler().runTaskLaterAsynchronously(FantasyMCCore.getInstance(), new Runnable() {

							@Override
							public void run() {
								sign.setActive(false);
								sign.setState(SignState.SEARCHING);
								sign.update();
							}
						}, 20);
					}
					countdown --;
				}
			}
		}, 0, 20);
	}

	public void stopTask(){
		Bukkit.getScheduler().cancelTask(task);
	}

	public String getCountdown() {
		return this.countdown + "";
	}

}

