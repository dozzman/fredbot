package fredbot;


public class HelloChatEvent extends ChatEvent {

	@Override
	public void process(ChatEventCallback callback) {
		System.out.println("Constructing a new message");
		callback.chatEventCallback("Hello, " + getFrom() + "!");
		
	}

}
