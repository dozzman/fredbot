package fredbot;

import org.jivesoftware.smack.packet.Message;

public abstract class ChatEvent {
	private String from;
	
	public abstract void process(ChatEventCallback callback);
	
	public String getFrom() {
		return from;
	}
	
	public void setFrom(String from) {
		this.from = from;
	}
}