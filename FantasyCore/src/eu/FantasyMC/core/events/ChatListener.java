package eu.FantasyMC.core.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import eu.FantasyMC.core.utils.PlayerUtils;

public class ChatListener implements Listener{

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e){
		Player p = e.getPlayer();
		e.setFormat(PlayerUtils.getPS(p) + "§7" + p.getDisplayName() + " §9§l» §7" + "%2$s");
		
	}
	
}
