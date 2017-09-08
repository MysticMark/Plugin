package eu.FantasyMC.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import eu.FantasyMC.core.arena.ArenaManagers.Arena;
import eu.FantasyMC.core.customEvents.arena.ArenaQuitEvent;
import eu.FantasyMC.core.main.FantasyMCCore;
import eu.FantasyMC.core.utils.MessageUtils;

public class LeaveCommand implements CommandExecutor {

	MessageUtils msg = FantasyMCCore.getInstance().getMSG();
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) return false;
		Arena a = FantasyMCCore.getInstance().getAM().getArena((Player)sender);
		if (a == null) {
			sender.sendMessage(msg.prefix("§cYou are not in a arena!"));
			return false;
		} else {
			a.kickPlayer((Player) sender, "§cYou have left the arena!");
			ArenaQuitEvent event = new ArenaQuitEvent((Player) sender, a);
			Bukkit.getServer().getPluginManager().callEvent(event);
			return false;
		}
	}

	
}
