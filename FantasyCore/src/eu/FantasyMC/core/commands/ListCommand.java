package eu.FantasyMC.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import eu.FantasyMC.core.main.FantasyMCCore;
import eu.FantasyMC.core.utils.PlayerUtils;

public class ListCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("list")){
			if (Bukkit.getOnlinePlayers().size() > 1){
				sender.sendMessage(FantasyMCCore.getInstance().getMSG().PREFIX + "§9There are currently §f§l" + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers() + "§9 players online:");
				sendPlayerList(sender);
				return true;
			} else if (Bukkit.getOnlinePlayers().size() == 1){
				sender.sendMessage(FantasyMCCore.getInstance().getMSG().PREFIX + "§9There is currently §f§l" + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers() + "§9 players online:");
				sendPlayerList(sender);
				return true;
			} else {
				sender.sendMessage(FantasyMCCore.getInstance().getMSG().NO_PLAYERS);
				return true;
			}
			
		}
		return false;
	}
	
	private void sendPlayerList(CommandSender sender){
		String output = "";
		for (Player p : Bukkit.getOnlinePlayers()){
			output += PlayerUtils.getPS(p) + "§7" + p.getName() + "§f, ";
		}
		output = output.trim();
		output = output.substring(0, output.length() -1);
		sender.sendMessage(output);
	}
	

	
}
