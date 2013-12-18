package fredbot;

import org.jivesoftware.smack.packet.Message;

public class ChatEventProcessor {
		
	public void processEvent(HelloChatEvent event, ChatEventCallback callback)
	{
		Message msg = new Message();
		msg.setBody("Hello!");
		callback.chatEventCallback(msg);
	}

}
