package fredbot;


public class HelpChatEvent extends ChatEvent {

	@Override
	public void process(ChatEventCallback callback) {
		callback.chatEventCallback(Strings.HELP);
	}

}
