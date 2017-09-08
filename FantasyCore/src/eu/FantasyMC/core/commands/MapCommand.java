package eu.FantasyMC.core.commands;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import eu.FantasyMC.core.arena.GameManager.GameType;
import eu.FantasyMC.core.main.FantasyMCCore;
import eu.FantasyMC.core.region.FTemplateWorld;
import eu.FantasyMC.core.utils.MessageUtils;

public class MapCommand implements CommandExecutor{

	MessageUtils msg = FantasyMCCore.getInstance().getMSG();
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof ConsoleCommandSender) return true;
		Player p = (Player) sender;
		if (args.length == 0){

		} else if (args[0].equalsIgnoreCase("create")){
			if (args.length < 1){
				sender.sendMessage(msg.getUsage("map create"));
				return true;
			} else {
				if (Bukkit.getWorld("templateWorld") != null) {
					sender.sendMessage(msg.prefix("§cThe template world is already in use!"));
					return true;
				}

				FTemplateWorld world = new FTemplateWorld();
				world.create();
				p.teleport(world.getWorld().getSpawnLocation());

				return true;
			}
		} else if (args[0].equalsIgnoreCase("edit")){
			if (args.length < 3){
				sender.sendMessage(msg.getUsage("map edit <gametype> <mapname>"));
				return true;
			} else {
				if (Bukkit.getWorld("templateWorld") != null) {
					sender.sendMessage(msg.prefix("§cThe template world is already in use!"));
					return true;
				}
				GameType type = FantasyMCCore.getInstance().getGTM().get(args[1]);
				if (type == null){
					sender.sendMessage(msg.prefix("§cThis game doesnt exists!"));
					return true;
				} else {
					File f = new File(FantasyMCCore.getInstance().getDataFolder() + File.separator + "Maps" + File.separator + type.getName() + File.separator + args[2]);
					if (!f.exists()){
						sender.sendMessage(msg.prefix("§cThis map doesn't exists!"));
						return true;
					} else {
						FTemplateWorld world = new FTemplateWorld();
						world.create(type, args[2]);
						p.teleport(world.getWorld().getSpawnLocation());
					}
					return true;
				}
			}
		} else if (args[0].equalsIgnoreCase("save")){
			if (args.length < 3){
				sender.sendMessage(msg.getUsage("map save <gametype> <mapname>"));
				return true;
			} else {
				GameType type = FantasyMCCore.getInstance().getGTM().get(args[1]);
				if (type == null){
					sender.sendMessage(msg.prefix("§cThis game doesnt exists!"));
					return true;
				} else {
					if (Bukkit.getWorld("templateWorld") == null) {
						sender.sendMessage(msg.prefix("§cThe template world is not loaded!"));
						return true;
					}
					File target = new File(FantasyMCCore.getInstance().getDataFolder() + File.separator + "Maps" + File.separator + type.getName() + File.separator + args[2]);
						File source = Bukkit.getWorld("templateWorld").getWorldFolder();
						Bukkit.getWorld("templateWorld").save();
						for (Chunk chunk : Bukkit.getWorld("templateWorld").getLoadedChunks()){
							for (BlockState state : chunk.getTileEntities()){
								if (state instanceof Sign) Bukkit.broadcastMessage(((Sign) state).getLine(0));
							}
						}
						FantasyMCCore.getInstance().getWM().copyWorld(source, target);
						FantasyMCCore.getInstance().getWM().unloadWorld(Bukkit.getWorld("templateWorld"));
						FantasyMCCore.getInstance().getWM().deleteWorld(source);
					
					return true;
				}
			}
		} else if (args[0].equalsIgnoreCase("exit")){
			if (args.length < 1){
				sender.sendMessage(msg.getUsage("map exit"));
				return true;
			} else {
				if (Bukkit.getWorld("templateWorld") == null) {
					sender.sendMessage(msg.prefix("§cThe template world is not loaded!"));
					return true;
				}
				File source = Bukkit.getWorld("templateWorld").getWorldFolder();
				FantasyMCCore.getInstance().getWM().unloadWorld(Bukkit.getWorld("templateWorld"));
				FantasyMCCore.getInstance().getWM().deleteWorld(source);


			}
		} else if (args[0].equalsIgnoreCase("goto")){
			if (args.length < 1){
				sender.sendMessage(msg.getUsage("map goto"));
				return true;
			} else {
				if (Bukkit.getWorld("templateWorld") == null) {
					sender.sendMessage(msg.prefix("§cThe template world is not loaded!"));
					return true;
				}
				p.teleport(Bukkit.getWorld("templateWorld").getSpawnLocation());


			}
		}


		return false;
	}


}
