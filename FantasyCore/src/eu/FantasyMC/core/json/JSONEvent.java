package eu.FantasyMC.core.json;

public class JSONEvent {

	public enum ClickEvent {
		RUN_COMMAND("run_command"),
		SUGGEST_COMMAND("suggest_command"),
		OPEN_URL("open_url");
		
		String name;
		ClickEvent(String name){
			this.name = name;
		}
		
		public String getName(){return name;}
	}
	
	public enum HoverEvent {
		SHOW_TEXT("show_text"),
		SHOW_ACHIEVEMENT("show_achievement");
		String name;
		HoverEvent(String name){
			this.name = name;
		}
		
		public String getName(){return name;}
	}
}
