package eu.FantasyMC.core.json;

import java.util.ArrayList;

public class JSONMessageBuilder {

	//{"text":"welcome","clickEvent":{"action":"open_url","value":"www.google.com"},"hoverEvent":{"action":"show_text","value":"test"}}
	
	public String hoverevent = "";
	public String clickevent = "";
	
	private String color = "white";
	
	private ArrayList<String> messages;
	
	private String message = "";
	
	private boolean bold = false;
	private boolean italic = false;
	private boolean strikethrough = false;
	private boolean underlined = false;
	
	
	private boolean obfuscated = false;
	
	public JSONMessageBuilder(String message){
		this.message = "{\"text\":\"" + message + "\"";
		this.messages = new ArrayList<String>();
	}
	
	public JSONMessageBuilder setHoverEvent(JSONEvent.HoverEvent event, String value){
		if (value == "") hoverevent = "";
		else {
			hoverevent = "\"hoverEvent\":{\"action\":\"" + event.getName() + "\",\"value\":\"" + value + "\"}";
		}
		return this;
	}
	
	public JSONMessageBuilder setClickEvent(JSONEvent.ClickEvent event, String value){
		if (value == "") clickevent = "";
		else {
			clickevent = "\"clickEvent\":{\"action\":\"" + event.getName() + "\",\"value\":\"" + value + "\"}";
		}
		return this;
	}
	
	public JSONMessageBuilder addMessage(String message){
		saveMessage();
		reset();
		this.hoverevent = "";
		this.clickevent = "";
		this.message = ",{\"text\":\"" + message + "\"";
		return this;
	}
	
	public JSONMessageBuilder newLine(String message){
		addMessage("\n");
		addMessage(message);
		return this;
	}
	
	public JSONMessageBuilder newLine(){
		addMessage("\n");
		return this;
	}
	
	public JSONMessageBuilder setBold(boolean value){
		this.bold = value;
		return this;
	}
	
	public JSONMessageBuilder setItalic(boolean value){
		this.italic = value;
		return this;
	}
	
	public JSONMessageBuilder setStrikeThrough(boolean value){
		this.strikethrough = value;
		return this;
	}
	
	public JSONMessageBuilder setUnderlined(boolean value){
		this.underlined = value;
		return this;
	}
	
	public JSONMessageBuilder setObfuscated(boolean value){
		this.obfuscated = value;
		return this;
	}
	
	public JSONMessageBuilder setColor(JSONColor.Color color){
		this.color = color.getName();
		return this;
	}
	
	private JSONMessageBuilder saveMessage(){
		String newmsg = message;
		newmsg += ",\"bold\":" + bold + ",\"italic\":" + italic + ",\"strikethrough\":" + strikethrough + ",\"underlined\":" + underlined + ",\"obfuscated\":" + obfuscated + ",\"color\":\"" + color + "\"";
		if (clickevent != "") newmsg += "," + clickevent;
		if (hoverevent != "") newmsg += "," + hoverevent;
		newmsg += "}";
		messages.add(newmsg);
		return this;
	}
	
	public String build(){
		saveMessage();
		
		String newmsg = "[\"\",";
		for (String msg: messages){
			newmsg += msg;
		}
		newmsg += "]";
		return newmsg;
		
	}
	
	private void reset(){
		this.bold = false;
		this.italic = false;
		this.strikethrough = false;
		this.underlined = false;
		this.obfuscated = false;
		
		this.clickevent = "";
		this.hoverevent = "";
		this.color = "white";
	}
	
}
