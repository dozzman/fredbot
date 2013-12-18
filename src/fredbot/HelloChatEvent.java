package fredbot;

import org.jivesoftware.smack.packet.Message;

public class HelloChatEvent extends ChatEvent {

	@Override
	public void process(ChatEventCallback callback) {
		Message msg = new Message();
		msg.setBody("Hello!");
		callback.chatEventCallback(msg);
		
	}

}
