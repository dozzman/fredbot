package fredbot;

import org.jivesoftware.smack.packet.Message;

public class HelpChatEvent extends ChatEvent {

	@Override
	public void process(ChatEventCallback callback) {
		// TODO Auto-generated method stub
		Message msg = new Message();
		callback.chatEventCallback("Available commands (preceded by '@fred'):\n"
				+ "hello - Say hi to me :)\n"
				+ "paper YYYYpPPqQQ - get a link for a specific paper\n"
				+ "help - display this message"
				);
	}

}
