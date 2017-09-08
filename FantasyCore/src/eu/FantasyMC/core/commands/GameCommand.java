package eu.FantasyMC.core.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import eu.FantasyMC.core.arena.ArenaManagers.ArenaManager.GameState;
import eu.FantasyMC.core.arena.GameManager.GameType;
import eu.FantasyMC.core.json.JSONMessage;
import eu.FantasyMC.core.main.FantasyMCCore;
import eu.FantasyMC.core.utils.MessageUtils;

public class GameCommand implements CommandExecutor{

	MessageUtils msg = FantasyMCCore.getInstance().getMSG();
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0){

		} else if (args[0].equalsIgnoreCase("create")){
			if (args.length < 2){
				sender.sendMessage(FantasyMCCore.getInstance().getMSG().getUsage("game create <name>"));
				return true;
			} else {
				GameType type = FantasyMCCore.getInstance().getGTM().get(args[1]);
				if (type != null){
					sender.sendMessage(FantasyMCCore.getInstance().getMSG().prefix("§cThis game already exists!"));
					return true;
				} else {
					FantasyMCCore.getInstance().getGTM().register(args[1].toLowerCase());
					sender.sendMessage(FantasyMCCore.getInstance().getMSG().prefix("§aGame created!"));
					return true;
				}
			}
		} else if (args[0].equalsIgnoreCase("enable")){
			if (args.length < 2){
				sender.sendMessage(FantasyMCCore.getInstance().getMSG().getUsage("game enable <name>"));
				return true;
			} else {
				GameType type = FantasyMCCore.getInstance().getGTM().get(args[1]);
				if (type == null){
					sender.sendMessage(FantasyMCCore.getInstance().getMSG().prefix("§cThis game doesn't exists!"));
					return true;
				} else {
					type.setActive(true);
					type.enableSigns();
					sender.sendMessage(FantasyMCCore.getInstance().getMSG().prefix("§aGame enabled!"));
					return true;
				}
			}
		} else if (args[0].equalsIgnoreCase("disable")){
			if (args.length < 2){
				sender.sendMessage(FantasyMCCore.getInstance().getMSG().getUsage("game enable <name>"));
				return true;
			} else {
				GameType type = FantasyMCCore.getInstance().getGTM().get(args[1]);
				if (type == null){
					sender.sendMessage(FantasyMCCore.getInstance().getMSG().prefix("§cThis game doesn't exists!"));
					return true;
				} else {
					type.setActive(false);
					type.disableSigns();
					sender.sendMessage(FantasyMCCore.getInstance().getMSG().prefix("§aGame disabled!"));
					return true;
				}
			}
		} else if (args[0].equalsIgnoreCase("setmaxplayers")){
			if (args.length < 3){
				sender.sendMessage(FantasyMCCore.getInstance().getMSG().getUsage("game setmaxplayers <name> <maxplayers>"));
				return true;
			} else {
				GameType type = FantasyMCCore.getInstance().getGTM().get(args[1]);
				if (type == null){
					sender.sendMessage(FantasyMCCore.getInstance().getMSG().prefix("§cThis game doesn't exists!"));
					return true;
				} else {
					try {
						type.setMaxPlayers(Integer.parseInt(args[2]));
						sender.sendMessage(msg.prefix("§aMax players set to: " + Integer.parseInt(args[2])));
						type.updateArenas();
						type.updateSigns();
					} catch (Exception e){
						sender.sendMessage(msg.prefix("§cPlease specify a number!"));
					}
					return true;
				}
			}
		} else if (args[0].equalsIgnoreCase("setminplayers")){
			if (args.length < 3){
				sender.sendMessage(FantasyMCCore.getInstance().getMSG().getUsage("game setminplayers <name> <maxplayers>"));
				return true;
			} else {
				GameType type = FantasyMCCore.getInstance().getGTM().get(args[1]);
				if (type == null){
					sender.sendMessage(FantasyMCCore.getInstance().getMSG().prefix("§cThis game doesn't exists!"));
					return true;
				} else {
					try {
						type.setMinPlayers(Integer.parseInt(args[2]));
						sender.sendMessage(msg.prefix("§aMin players set to: " + Integer.parseInt(args[2])));
						type.updateArenas();
						type.updateSigns();
					} catch (Exception e){
						sender.sendMessage(msg.prefix("§cPlease specify a number!"));
					}
					return true;
				}
			}
		} else if (args[0].equalsIgnoreCase("arenas")){
			sender.sendMessage(msg.prefix("§6Curently running arena's"));
			String str = "";
			for (GameState state : GameState.values()){
				str += state.getName() + "§f, ";
			}
			str = str.trim();
			str = str.substring(0, str.length() -1);
			sender.sendMessage(str);
			sender.sendMessage(" ");
			if (sender instanceof Player) JSONMessage.sendChat((Player) sender, FantasyMCCore.getInstance().getAM().listJSON());
			else sender.sendMessage(FantasyMCCore.getInstance().getAM().list());
		}

		return false;
	}


}
