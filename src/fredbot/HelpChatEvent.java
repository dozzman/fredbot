package fredbot;

import org.jivesoftware.smack.packet.Message;

public class HelpChatEvent extends ChatEvent {

	@Override
	public void process(ChatEventCallback callback) {
		// TODO Auto-generated method stub
		Message msg = new Message();
		callback.chatEventCallback("No help available at the moment!");
	}

}
