package eu.FantasyMC.core.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import eu.FantasyMC.core.main.FantasyMCCore;

public class SignListener implements Listener {

	@EventHandler
	public void onChange(SignChangeEvent e){
		Player p = e.getPlayer();
		for (int i = 0; i < 4; i++){
			e.setLine(i, FantasyMCCore.getInstance().getMSG().getColloredMSG(e.getLine(i), p.hasPermission("fantasymc.sign.color"), p.hasPermission("fantasymc.sign.format")));
		}
	}
}
