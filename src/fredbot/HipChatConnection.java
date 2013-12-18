package fredbot;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

public class HipChatConnection extends XMPPConnection {
	private static String hipChatServer = "chat.hipchat.com";
	private static int port = 5222;
	private static ConnectionConfiguration config = new ConnectionConfiguration(hipChatServer, port);
	public HipChatConnection(String username, String password) throws XMPPException {
		super(config);
		
		this.connect();
		
		this.login(username, password);
		
		
	}

}
