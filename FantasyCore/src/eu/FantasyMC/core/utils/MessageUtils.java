package eu.FantasyMC.core.utils;

import org.bukkit.Bukkit;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

public class MessageUtils {

	public MessageUtils(){}
	
	public String PREFIX = "§3[§dFantasy§6MC§3] §7";
	public String NO_PERMISSION_COMMAND = PREFIX + "§cYou dont have permission to execute this command!";
	public String NOT_PLAYED_BEFORE = PREFIX + "§cThis user has never joined before!";
	public String NOT_ONLINE  = PREFIX + "§cThis user id not online!";
	public String NO_RANK = PREFIX + "§cThis is not a vailid rank!";
	public String NO_PLAYERS = PREFIX + "§cThere are no players online!";
	public String VANISH_ON = PREFIX + "§aYou are now only visible to §fBuilder§a and higher";
	public String VANISH_OFF = PREFIX + "§cYou are now visible to §fEveryone";
	public String PERMISSIONS = PREFIX + "§aAll permissions have been reloaded, and updated for §f(" + Bukkit.getOnlinePlayers().size() + ")";
	public String NOREPLY = PREFIX + "§cYou have no one to reply to!";
	public String ARENASIGNLINEEMPTY = PREFIX + "§cYou need to specify an game!";
	public String ARENASIGNLINEGAMENOTEXISTS = PREFIX + "§cThis game doesn't exist!";
	public String ARENASIGNCREATED = PREFIX + "§aSign created!";
	public String ARENASIGNREMOVED = PREFIX + "§aSign removed!";
	
	public String getUsage(String usage){
		return PREFIX + "§cPlease use §4/" + usage;
	}
	
	public String prefix(String msg){
		return PREFIX + msg;
	}
	
	

	public String getNoRank(String rank){
		return PREFIX + "§4" + rank + " §cis not a vailid rank!";
	}

	public String getRankSucces(String name, String rank1){
		return PREFIX + "§9" + name + "'s §brank is now: §f" + rank1;
	}

	public String getRankSucces(String name, String rank1, String rank2){
		return PREFIX + "§9" + name + "'s §branks are now: §f" + rank1 + "§9, §f" + rank2;
	}

	public String getColloredMSG(String message, boolean color, boolean format) {
		String newmessage = message;
		if (color) newmessage = newmessage.replaceAll("(&([a-f0-9A-Fk-oK-OrR]))", "§$2");
		if (format) newmessage = newmessage.replaceAll("(&([k-oK-O]))", "§$2");
		return newmessage;
	}

	public void getColloredSign(Sign s, Player p){
		for (int i = 0; i < 4; i++){
			s.setLine(i, getColloredMSG(s.getLine(i), p.hasPermission("fantasymc.sign.color"), p.hasPermission("fantasymc.sign.format")));
		}
	}



}
