package fredbot;

public class ExitChatEvent extends ChatEvent {

	public ExitChatEvent(String from) {
		super(from);
	}

	@Override
	public boolean process(ChatEventCallback callback) {
		// stub as this event does nothing but quit
		return true;
	}

	@Override
	public String command() {
		//stub as this command is never processed
		return "gtfo";
	}

	@Override
	public boolean init(String msg) {
		//stub
		return true;
	}

}
