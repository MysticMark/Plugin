package eu.FantasyMC.core.commands;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import eu.FantasyMC.core.main.FantasyMCCore;
import eu.FantasyMC.core.utils.PlayerUtils;

public class PMCommand implements CommandExecutor{

	public static HashMap<Player, Player> lastMSG = new HashMap<Player, Player>();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("reply")){
			if (args.length < 1){
				sender.sendMessage(FantasyMCCore.getInstance().getMSG().getUsage(label + " <message>"));
				return true;
			} else {
				if (sender instanceof ConsoleCommandSender) return true;
				Player from = (Player) sender;
				Player p = lastMSG.get(from);
				if (p == null){
					sender.sendMessage(FantasyMCCore.getInstance().getMSG().NOREPLY);
					sender.sendMessage(lastMSG.entrySet().toString());
					return true;
				} else {
					String msg = "";
					for (int i = 0; i < args.length; i++){
						msg += args[i] + " ";
					}
					msg = msg.trim();
					sendMSG(sender, p, msg);
					return true;
				}
			}
		} else {
			if (args.length < 2){
				sender.sendMessage(FantasyMCCore.getInstance().getMSG().getUsage(label + " <player> <message>"));
				return true;
			} else {
				Player p = Bukkit.getPlayerExact(args[0]);
				if (p == null){
					sender.sendMessage(FantasyMCCore.getInstance().getMSG().NOT_ONLINE);
					return true;
				} else {
					String msg = "";
					for (int i = 1; i < args.length; i++){
						msg += args[i] + " ";
					}
					msg = msg.trim();
					sendMSG(sender, p, msg);
					return true;
				}
			}
		}
	}

	private void sendMSG(CommandSender sender, Player reciever, String msg){
		if (sender instanceof ConsoleCommandSender){
			sender.sendMessage("§3[§9Me §f» " + PlayerUtils.getPS(reciever) + "§7" + reciever.getName() + "§3] §7" + msg);
			reciever.sendMessage("§3[§4Console §f» §9Me§3] §7" + msg);
		} else {
			Player from = (Player) sender;
			sender.sendMessage("§3[§9Me §f» " + PlayerUtils.getPS(reciever) + "§7" + reciever.getName() + "§3] §7" + msg);
			reciever.sendMessage("§3[" + PlayerUtils.getPS(from) + "§7" + from.getName()+ " §f» §9Me§3] §7" + msg);
			lastMSG.put(from, reciever);
			lastMSG.put(reciever, from);
		}
	}
}
