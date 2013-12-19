package fredbot;

// superclass for all recognisable chat events passed to fred.
// each class must have an associated entry in Commands.java
// and the large switch-style if statement in Fredbot.java to
// construct the ChatEvent properly.
public abstract class ChatEvent {
	private final String from;
	
	// this function processes the chat event and uses the callback to message the user
	public abstract boolean process(ChatEventCallback callback);
	
	// make the string an abstract function so that it MUST be defined by any subclasses
	public abstract String command();
	
	// constructor provides information about the message
	public ChatEvent(String from)
	{
		this.from = from;
	}
	public abstract boolean init(String msg);
	
	public String getFrom() {
		return from;
	}
}