package eu.FantasyMC.core.ranks;

import org.bukkit.ChatColor;

public class RankData {

	public enum Ranks{
		
		OWNER("§5§lOwner", "§5§lOwner §5", "a"),
		HEAD_ADMIN("§4§lHead Admin", "§4§lH-Admin §4", "b"),
		ADMIN("§4§lAdmin", "§4§lAdmin §4", "c"),
		MOD("§9§lMod", "§9§lMod §9", "d"),
		HELPER("§e§lHelper", "§e§lHelper §e", "e"),
		DEV("§3§lDev", "§3§lDev §3", "f"),
		JRDEV("§3§lJR. Dev", "§3§lJR. Dev §3", "g"),
		HEAD_BUILDER("§2§lHead Builder", "§2§lH-Builder §2", "h"),
		BUILDER("§2§lBuilder", "§2§lBuilder §2", "i"),
		
		
		LEGEND("§d§lLegend", "§d§lLegend §f", "j"),
		VETERAN("§e§lVeteran", "§e§lVeteran §f", "k"),
		RAIDER("§cRaider", "§cRaider §f", "l"),
		INVADER("§1Invader", "§1Invader §f", "m"),
		
		YOUTUBE("§4You§fTuber", "§4Y§fT §f", "y"),
		MEMBER("§7Member", "§7", "z");

		private String prefix, tabprefix, tabpos;
		Ranks(String prefix, String tabprefix, String tabpos){
			this.prefix = prefix;
			this.tabprefix = tabprefix;
			this.tabpos = tabpos;
		}

		public String getName(){return name();}
		public String getPrefix(){return prefix;}
		public String getTabPrefix(){return tabprefix;}
		public String getTabPos(){return tabpos;}

		public String getDisplayname(){
			return ChatColor.stripColor(getPrefix());
		}
	}
	
	public static boolean isRank(String input){
		try {
			return Ranks.valueOf(input.toUpperCase()) != null;
		} catch (Exception e){
			return false;
		}
	}
	
	public static Ranks getRank(String input){
		try {
			return Ranks.valueOf(input.toUpperCase());
		} catch (Exception e){
			return Ranks.MEMBER;
		}	
	}
}
