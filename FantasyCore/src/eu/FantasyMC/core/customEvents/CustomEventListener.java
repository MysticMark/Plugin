package eu.FantasyMC.core.customEvents;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import eu.FantasyMC.core.arena.ArenaManagers.ArenaSign;
import eu.FantasyMC.core.customEvents.arena.ArenaJoinEvent;
import eu.FantasyMC.core.customEvents.arena.ArenaSignClickEvent;
import eu.FantasyMC.core.main.FantasyMCCore;

public class CustomEventListener implements Listener {

	@EventHandler
	public void onSignChange(SignChangeEvent e){
		Player p = e.getPlayer();
		if (e.getLine(0).equalsIgnoreCase("[join]")){
			if (e.getLine(1).isEmpty()) {
				p.sendMessage(FantasyMCCore.getInstance().getMSG().ARENASIGNLINEEMPTY);
				e.getBlock().setType(Material.AIR);
			} else if (FantasyMCCore.getInstance().getGTM().get(e.getLine(1)) == null){
				p.sendMessage(FantasyMCCore.getInstance().getMSG().ARENASIGNLINEGAMENOTEXISTS);
				e.getBlock().setType(Material.AIR);
			} else {
				ArenaSign sign = new ArenaSign(e.getBlock().getLocation());
				sign.setGameType(FantasyMCCore.getInstance().getGTM().get(e.getLine(1)));
				FantasyMCCore.getInstance().getGTM().get(e.getLine(1)).addSign(sign);
				p.sendMessage(FantasyMCCore.getInstance().getMSG().ARENASIGNCREATED);
			}
			return;
		} else return;
	}

	@EventHandler
	public void onSignBreak(BlockBreakEvent e){
		Player p = e.getPlayer();
		if (isSign(e.getBlock())){
			//Bukkit.broadcastMessage("test");
			boolean bl = FantasyMCCore.getInstance().getSM().removeSign(e.getBlock().getLocation());
			if (bl) p.sendMessage(FantasyMCCore.getInstance().getMSG().ARENASIGNREMOVED); 
			return;
		}
	}

	@EventHandler
	public void onSignClick(PlayerInteractEvent e){
		if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
			if (isSign(e.getClickedBlock())){
				ArenaSign s = FantasyMCCore.getInstance().getSM().getSign(e.getClickedBlock().getLocation());
				if (s == null) return;
				else {
					ArenaSignClickEvent event = new ArenaSignClickEvent(e.getPlayer(), s);
					Bukkit.getServer().getPluginManager().callEvent(event);
				}
			}
		}
	}

	@EventHandler
	public void onClick(ArenaSignClickEvent e){
		Player p = e.getPlayer();
		ArenaSign s = e.getSign();

		if (s.getArena() == null) return;
		else {
			if (FantasyMCCore.getInstance().getAM().getArena(p) == null){
				ArenaJoinEvent event = new ArenaJoinEvent(p, s.getArena());
				Bukkit.getServer().getPluginManager().callEvent(event);
			}
		}
		s.update();
	}

	private boolean isSign(Block b){
		return b.getType() == Material.SIGN || b.getType() == Material.WALL_SIGN || b.getType() == Material.SIGN_POST;
	}
}
