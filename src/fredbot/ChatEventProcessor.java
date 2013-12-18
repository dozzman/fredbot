package fredbot;

import org.jivesoftware.smack.packet.Message;

public class ChatEventProcessor extends Thread {
	
	private ChatEventCallback callback;
	
	public ChatEventProcessor(ChatEventCallback callback)
	{
		this.callback = callback;
	}
	
	public void processEvent(ChatEvent event)
	{
		
	}
	
	private void processEventHelper(HelloChatEvent event)
	{
		Message msg = new Message();
		msg.setBody("Hello!");
		callback.chatEventCallback(msg);
	}

	@Override
	public void run() {
		this.setDaemon(true);
		
	}

}
