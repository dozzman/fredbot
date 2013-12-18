package fredbot;

import org.jivesoftware.smack.packet.Message;

public interface ChatEventCallback {
	public void chatEventCallback(Message msg);
}
