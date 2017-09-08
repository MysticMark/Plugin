package eu.FantasyMC.core.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import eu.FantasyMC.core.customEvents.RankChangeEvent;
import eu.FantasyMC.core.main.FantasyMCCore;
import eu.FantasyMC.core.ranks.RankData;
import eu.FantasyMC.core.ranks.RankData.Ranks;
import eu.FantasyMC.core.ranks.RankPermissionsManager;
import eu.FantasyMC.core.ranks.RankPlayers;
import eu.FantasyMC.core.utils.UUIDFetcher;

public class RankCommand implements TabExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("rank")){
			if (!sender.hasPermission("fantasymc.rank")){
				sender.sendMessage(FantasyMCCore.getInstance().getMSG().NO_PERMISSION_COMMAND);
				return true;
			}
			
			
			// /rank <Player> <Primary> [<Secondary>]
			if (args.length < 2){
				sender.sendMessage(FantasyMCCore.getInstance().getMSG().getUsage("rank <Player> <Primary> [<Secondary>]"));
				return true;
			} else {
				if (!UUIDFetcher.hasPlayedBefore(UUIDFetcher.getUUID(args[0]))){
					sender.sendMessage(FantasyMCCore.getInstance().getMSG().NOT_PLAYED_BEFORE);
					return true;
				}
				else if (args.length == 2){
					if (RankData.isRank(args[1])){
						RankChangeEvent e = new RankChangeEvent(UUIDFetcher.getUUID(args[0]), RankData.getRank(args[1]), Ranks.MEMBER);
						Bukkit.getPluginManager().callEvent(e);
						sender.sendMessage(FantasyMCCore.getInstance().getMSG().getRankSucces(args[0], RankData.getRank(args[1]).getName()));
						return true;
					} else {
						sender.sendMessage(FantasyMCCore.getInstance().getMSG().getNoRank(args[1]));
						return true;
					}
				} else {
					if (RankData.isRank(args[1])){
						if (RankData.isRank(args[2])){
							RankChangeEvent e = new RankChangeEvent(UUIDFetcher.getUUID(args[0]), RankData.getRank(args[1]), RankData.getRank(args[2]));
							Bukkit.getPluginManager().callEvent(e);
							sender.sendMessage(FantasyMCCore.getInstance().getMSG().getRankSucces(args[0], RankData.getRank(args[1]).getName(), RankData.getRank(args[2]).getName()));
							return true;
						} else {
							sender.sendMessage(FantasyMCCore.getInstance().getMSG().getNoRank(args[2]));
							return true;
						}
					} else {
						sender.sendMessage(FantasyMCCore.getInstance().getMSG().getNoRank(args[1]));
						return true;
					}
				}
			}
		}
		if (cmd.getName().equalsIgnoreCase("updateperms")){
			if (!sender.hasPermission("fantasymc.rank")){
				sender.sendMessage(FantasyMCCore.getInstance().getMSG().NO_PERMISSION_COMMAND);
				return true;
			}
			else {
				RankPermissionsManager.updateAllPermissions();
				for (Player online : Bukkit.getOnlinePlayers()){
					RankPlayers.calculate(online);
				}
				sender.sendMessage(FantasyMCCore.getInstance().getMSG().PERMISSIONS);
			}
		}
		return false;

	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("rank")) {
			if (args.length == 2) {
				ArrayList<String> ranks = new ArrayList<String>();

				if (!args[1].equals("")) {
					for (Ranks rank : Ranks.values()) {
						if (rank.getName().toLowerCase().startsWith(args[1].toLowerCase())){
						ranks.add(rank.getName());
						}
					}
				}

				else {
					for (Ranks rank : Ranks.values()) {
						ranks.add(rank.getName());
					}
				}
				return ranks;
			}
			else if (args.length == 3) {
				ArrayList<String> ranks = new ArrayList<String>();

				if (!args[2].equals("")) {
					for (Ranks rank : Ranks.values()) {
						if (rank.getName().toLowerCase().startsWith(args[2].toLowerCase())){
						ranks.add(rank.getName());
						}
					}
				}

				else {
					for (Ranks rank : Ranks.values()) {
						ranks.add(rank.getName());
					}
				}
				return ranks;
			}
		}

		return null;
	}


}
