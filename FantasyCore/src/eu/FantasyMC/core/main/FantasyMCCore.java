package eu.FantasyMC.core.main;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import eu.FantasyMC.core.arena.ArenaManagers.ArenaEvents;
import eu.FantasyMC.core.arena.ArenaManagers.ArenaManager;
import eu.FantasyMC.core.arena.ArenaManagers.SignManager;
import eu.FantasyMC.core.arena.GameManager.GameTypeManager;
import eu.FantasyMC.core.commands.AnnounceCommand;
import eu.FantasyMC.core.commands.GameCommand;
import eu.FantasyMC.core.commands.LeaveCommand;
import eu.FantasyMC.core.commands.ListCommand;
import eu.FantasyMC.core.commands.MapCommand;
import eu.FantasyMC.core.commands.PMCommand;
import eu.FantasyMC.core.commands.RankCommand;
import eu.FantasyMC.core.commands.VanishCommand;
import eu.FantasyMC.core.customEvents.CustomEventListener;
import eu.FantasyMC.core.customEvents.RankChangeEvent;
import eu.FantasyMC.core.events.ChatListener;
import eu.FantasyMC.core.events.JoinListener;
import eu.FantasyMC.core.events.RankListener;
import eu.FantasyMC.core.events.SignListener;
import eu.FantasyMC.core.player.PlayerData;
import eu.FantasyMC.core.ranks.RankPermissionsManager;
import eu.FantasyMC.core.ranks.RankPlayers;
import eu.FantasyMC.core.ranks.RankTags;
import eu.FantasyMC.core.region.FWorldManager;
import eu.FantasyMC.core.utils.MessageUtils;

public class FantasyMCCore extends JavaPlugin implements Listener{

	private static FantasyMCCore core;
	private FWorldManager FWM;
	private SignManager SM;
	private ArenaManager AM;
	private GameTypeManager GTM;
	private MessageUtils MSG;
	@Override
	public void onEnable(){
		
		core = this;
		FWM = new FWorldManager();
		SM = new SignManager();
		SM.init();
		AM = new ArenaManager();
		GTM = new GameTypeManager();
		GTM.load();
		
		MSG = new MessageUtils();
		registerListener(new CustomEventListener());
		registerListener(new ChatListener());
		registerListener(new JoinListener());
		registerListener(new RankListener());
		registerListener(new SignListener());
		registerListener(new ArenaEvents());
		registerListener(this);
		
		setExecutor("rank", new RankCommand(), new RankCommand());
		setExecutor("updateperms", new RankCommand());
		setExecutor("list", new ListCommand());
		setExecutor("vanish", new VanishCommand());
		setExecutor("announce", new AnnounceCommand());	
		setExecutor("broadcast", new AnnounceCommand());	
		setExecutor("message", new PMCommand());
		setExecutor("msg", new PMCommand());
		setExecutor("whisper", new PMCommand());
		setExecutor("t", new PMCommand());
		setExecutor("tell", new PMCommand());
		setExecutor("m", new PMCommand());
		setExecutor("reply", new PMCommand());
		setExecutor("game", new GameCommand());
		setExecutor("leave", new LeaveCommand());
		setExecutor("map", new MapCommand());
		RankPermissionsManager.updateAllPermissions();	

		for (Player p : Bukkit.getOnlinePlayers()){
			PlayerData pdata = new PlayerData(p.getUniqueId());
			pdata.updatePlayerName(p.getName());
			RankPlayers.setPrimaryRank(p.getUniqueId(), pdata.getPrimaryRank());
			RankPlayers.setSecondaryRank(p.getUniqueId(), pdata.getSecondaryRank());
			RankTags rt = new RankTags(p);
			rt.updateToAll();
			RankChangeEvent ce = new RankChangeEvent(p.getUniqueId(), pdata.getPrimaryRank(), pdata.getSecondaryRank());
			Bukkit.getPluginManager().callEvent(ce);
			RankPlayers.register(p);
		}



	}

	@Override
	public void onDisable(){
		for (Player p : Bukkit.getOnlinePlayers()){
			RankPlayers.unregister(p);
		}
		AM.unloadAll();
		GTM.save();
		
	}
	


	private void registerListener(Listener listener){
		Bukkit.getServer().getPluginManager().registerEvents(listener, this);
	}

	private void setExecutor(String cmd, CommandExecutor executor){
		Bukkit.getPluginCommand(cmd).setExecutor(executor);
	}

	private void setExecutor(String cmd, CommandExecutor executor, TabCompleter completer){
		Bukkit.getPluginCommand(cmd).setExecutor(executor);
		Bukkit.getPluginCommand(cmd).setTabCompleter(completer);
	}
	
    /*public WorldEditPlugin getWorldEdit() {
        Plugin p = Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
        if (p instanceof WorldEditPlugin) return (WorldEditPlugin) p;
        else return null;
    }*/
    
    public static FantasyMCCore getInstance(){
    	return core;
    }
    
    /*public MultiverseCore getMultiverseCore() {
        Plugin plugin = getServer().getPluginManager().getPlugin("Multiverse-Core");
 
        if (plugin instanceof MultiverseCore) {
            return (MultiverseCore) plugin;
        }
 
        throw new RuntimeException("MultiVerse not found!");
    } */
    
    public FWorldManager getWM(){
    	return this.FWM;
    }
    
    public SignManager getSM(){
    	return this.SM;
    }
    
    public GameTypeManager getGTM(){
    	return this.GTM;
    }
    
    public ArenaManager getAM(){
    	return this.AM;
    }
    
    public MessageUtils getMSG(){
    	return this.MSG;
    }
}
