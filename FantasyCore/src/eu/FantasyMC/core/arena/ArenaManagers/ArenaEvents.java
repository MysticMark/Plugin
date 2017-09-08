package eu.FantasyMC.core.arena.ArenaManagers;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import eu.FantasyMC.core.arena.ArenaManagers.ArenaManager.GameState;
import eu.FantasyMC.core.customEvents.arena.ArenaJoinEvent;
import eu.FantasyMC.core.customEvents.arena.ArenaQuitEvent;
import eu.FantasyMC.core.main.FantasyMCCore;
import eu.FantasyMC.core.utils.PlayerUtils;

public class ArenaEvents implements Listener{

	@EventHandler
	public void onQuit(ArenaQuitEvent e){
		Arena a = e.getArena();
		Player p = e.getPlayer();
		if (a.getPlayers().size() == 0 && a.getState() == GameState.INGAME) {
			FantasyMCCore.getInstance().getAM().removeArena(a);
		}
		a.sendMessage(PlayerUtils.getPS(p) + "§7" + p.getDisplayName() + "§6 left the game §f[§a§l" + a.getPlayers().size() + "/" + a.getMaxPlayers() + "§f]");
	}

	@EventHandler
	public void onJoin(ArenaJoinEvent e){
		Arena a = e.getArena();
		Player p = e.getPlayer();
		if (a.getPlayers().size() >= a.getMaxPlayers()) {
			p.sendMessage("§cThis game is full!");
		} else {
			a.addPlayer(p);
			a.sendMessage(PlayerUtils.getPS(p) + "§7" + p.getDisplayName() + "§6 joined the game §f[§a§l" + a.getPlayers().size() + "/" + a.getMaxPlayers() + "§f]");

		}
	}
}
