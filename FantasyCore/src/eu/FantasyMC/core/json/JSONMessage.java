package eu.FantasyMC.core.json;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class JSONMessage {
	
	public static void sendTitle(Player p, String title, String subtitle, int fadeIn, int stay, int fadeOut){
        try {
            Object enumTitle = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get(null);
            Object enumSubTitle = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE").get(null);
            
            Object t = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + title + "\"}");
            Object s = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + subtitle + "\"}");
           
            Constructor<?> titleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"), int.class, int.class, int.class);
            Object a = titleConstructor.newInstance(enumTitle, t, fadeIn, stay, fadeOut);
            Object b = titleConstructor.newInstance(enumSubTitle, s, fadeIn, stay, fadeOut);
           
            sendPacket(p, a);
            sendPacket(p, b);
        }
   
        catch (Exception e1) {
            e1.printStackTrace();
        }
	}
	
	public static void sendTabHF(Player p, String header, String footer){
		try {
		  Constructor<?> constructor = getNMSClass("PacketPlayOutPlayerListHeaderFooter").getConstructor(new Class[0]);
	      Object packet = constructor.newInstance(new Object[0]);
	      
	      Object h = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + header + "\"}");
	      Object f = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + footer + "\"}");
	      
	      setField(packet, "a", h);
	      setField(packet, "b", f);
	      sendPacket(p, packet);
		} catch (Exception e1) {
	         e1.printStackTrace();
	    }
	}
	
	public static void sendAction(Player p, String message){
        try {
            Object action = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + message + "\"}");
           
            Constructor<?> actionConstructor = getNMSClass("PacketPlayOutChat").getConstructor(getNMSClass("IChatBaseComponent"), byte.class);
            Object a = actionConstructor.newInstance(action, (byte) 2);
           
            sendPacket(p, a);
        }
   
        catch (Exception e1) {
            e1.printStackTrace();
        }
	}
	
	public static void sendChat(Player p, String message){
        try {
            Object action = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, message);
           
            Constructor<?> actionConstructor = getNMSClass("PacketPlayOutChat").getConstructor(getNMSClass("IChatBaseComponent"), byte.class);
            Object a = actionConstructor.newInstance(action, (byte) 0);
           
            sendPacket(p, a);
        }
   
        catch (Exception e1) {
            e1.printStackTrace();
        }
	}
	
    private static void sendPacket(Player player, Object packet) {
        try {
                Object handle = player.getClass().getMethod("getHandle").invoke(player);
                Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
                playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
        }
       
        catch (Exception e) {
                e.printStackTrace();
        }
    }

    private static Class<?> getNMSClass(String name) {
        // org.bukkit.craftbukkit.v1_8_R3...
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
                return Class.forName("net.minecraft.server." + version + "." + name);
        }
       
        catch (ClassNotFoundException e) {
                e.printStackTrace();
                return null;
        }
    }
    private static void setField(Object change, String name, Object to)
    	    throws Exception
    	  {
    	    Field field = change.getClass().getDeclaredField(name);
    	    field.setAccessible(true);
    	    field.set(change, to);
    	    field.setAccessible(false);
    	  }
}
