package fredbot;


public class HelloChatEvent extends ChatEvent {

	// hello event has no initialisation
	public HelloChatEvent(String from) {
		super(from);
	}

	@Override
	public boolean process(ChatEventCallback callback) {
		System.out.println("Constructing a new message");
		callback.chatEventCallback("Hello, " + getFrom() + "!");
		return true;
	}

	@Override
	public String command() {
		return "hello";
	}

	@Override
	public boolean init(String msg) {
		//stub
		return true;
	}

}
