package eu.FantasyMC.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import eu.FantasyMC.core.json.JSONMessage;
import eu.FantasyMC.core.main.FantasyMCCore;

public class AnnounceCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("announce")){
			if (sender.hasPermission("fantasymc.announce")){
				if (args.length == 0) {
					sender.sendMessage(FantasyMCCore.getInstance().getMSG().getUsage("announce <message>"));
					return true;
				} else {
					String title = "§e«§6Announcement§e»";
					String subtitle = "";
					for (int i = 0; i < args.length; i++){
						subtitle += args[i] + " ";
					}
					for (Player p : Bukkit.getOnlinePlayers()){
						JSONMessage.sendTitle(p, title, FantasyMCCore.getInstance().getMSG().getColloredMSG(subtitle.trim(), sender.hasPermission("fantasymc.announce.color"), sender.hasPermission("fantasymc.announce.format")), 20, 80, 20);
					}
					return true;
				}
			} else {
				sender.sendMessage(FantasyMCCore.getInstance().getMSG().NO_PERMISSION_COMMAND);
				return true;
			}
		}
		
		if (cmd.getName().equalsIgnoreCase("broadcast")){
			if (sender.hasPermission("fantasymc.broadcast")){
				if (args.length == 0) {
					sender.sendMessage(FantasyMCCore.getInstance().getMSG().getUsage("broadcast <message>"));
					return true;
				} else {
					String text = "";
					for (int i = 0; i < args.length; i++){
						text += args[i] + " ";
					}
					Bukkit.broadcastMessage(FantasyMCCore.getInstance().getMSG().PREFIX + FantasyMCCore.getInstance().getMSG().getColloredMSG(text.trim(), sender.hasPermission("fantasymc.broadcast.color"), sender.hasPermission("fantasymc.broadcast.format")));
					return true;
				}
			} else {
				sender.sendMessage(FantasyMCCore.getInstance().getMSG().NO_PERMISSION_COMMAND);
				return true;
			}
		}
		return false;
	}

	
}
