package eu.FantasyMC.core.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import eu.FantasyMC.core.main.FantasyMCCore;
import eu.FantasyMC.core.player.PlayerData;
import eu.FantasyMC.core.utils.PlayerUtils;

public class VanishCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("vanish")){
			if (args.length == 0){
				if (sender.hasPermission("fantasymc.vanish")){
					if (sender instanceof ConsoleCommandSender){
						sender.sendMessage(FantasyMCCore.getInstance().getMSG().getUsage("vanish <Player>"));
						return true;
					} else {
						Player p = (Player) sender;
						PlayerData pdata = new PlayerData(p.getUniqueId());
						if (pdata.getVanish()){
							sender.sendMessage(FantasyMCCore.getInstance().getMSG().VANISH_OFF);
							PlayerUtils.setVanish(p, false);
							pdata.setVanish(false);
						} else {
							sender.sendMessage(FantasyMCCore.getInstance().getMSG().VANISH_ON);
							PlayerUtils.setVanish(p, true);
							pdata.setVanish(true);
						}
					}
				}
			}
		}
		return false;
	}


}
