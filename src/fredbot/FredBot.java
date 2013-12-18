package fredbot;

import java.io.IOException;
import java.util.Collection;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;

import org.jivesoftware.smack.*;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smackx.muc.*;

public class FredBot implements ChatEventCallback {

	private static String username = "64306_586723";
	private static String password = "raspberrypi";
	private static String roomname = "64306_fred_test_room@conf.hipchat.com";
	private static String nickname = "Fred the Helpful";
	private static String mention = "@fred ";
	private static int eventSleepTime = 500;
	private BlockingQueue<ChatEvent> eventQueue;
	private Queue<Message> messageQueue;
	private HipChatConnection conn;
	private MultiUserChat room;
	private ChatEventProcessor eventProcessor;
	
	
	// bot initialisation function
	public void init() {
		// TODO Auto-generated method stub
		eventProcessor = new ChatEventProcessor(this);
		
		// attempt to connect to hipchat server
		try {
			conn = new HipChatConnection(username, password);
		} catch (XMPPException e) {
			System.out.println("Unable to connect to the hipchat server!");
			System.out.println(e.getMessage());
			e.printStackTrace();
			return;
		}
		
		// join the room
		room = new MultiUserChat(conn, roomname);
		try {
			room.join(nickname);
			System.out.println("Subject = " + room.getSubject());
			
		} catch (XMPPException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		// listen for room messages
		room.addMessageListener(new PacketListener() {

			@Override
			public void processPacket(Packet arg0) {
				// ok to down-cast as only messages will be sent here
				Message msg = (Message) arg0;
				ChatEvent event = ParseMessage(msg);
				if(event != null)
				{
					// message was for fred
					enqueueEvent(event);
				}
			}
			
		});
	}
	
	// bot main event loop function
	public void run()
	{
		// main event loop
		while(true)
		{
			ChatEvent event = null;
			
			try {
				event = eventQueue.take();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
			
			if(event instanceof ExitChatEvent)
			{
				System.out.println("Qutting now!");
				try {
					room.sendMessage("Goodbye!");
				} catch (XMPPException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				room.leave();
				conn.disconnect();
				return;
			}
		}
	}

	@Override
	public void chatEventCallback(Message msg) {
		try {
			room.sendMessage(msg);
		} catch (XMPPException e) {
			e.printStackTrace();
		}
		
	}
	
	public ChatEvent ParseMessage(Message msg)
	{
		return null;
	}
	

}
