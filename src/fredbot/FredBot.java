package fredbot;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smackx.muc.DiscussionHistory;
import org.jivesoftware.smackx.muc.MultiUserChat;

public class FredBot implements ChatEventCallback {

	private static String username = "64306_586723";
	private static String password = "raspberrypi";
	private static String roomname = "64306_fred_test_room@conf.hipchat.com";
	private static String nickname = "Fred the Helpful";
	private static String mention = "@fred";
	private static int queueCapacity = 20;
	private BlockingQueue<ChatEvent> eventQueue;
	private HipChatConnection conn;
	private MultiUserChat room;
	
	
	// bot initialisation function
	public void init() {
		// initialise the queue
		 eventQueue = new ArrayBlockingQueue<ChatEvent>(queueCapacity);
		
		// TODO: look in plugin folder for extra ChatEvent classes and load them into the Commands list
		 
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
			// don't read any history when joining
			DiscussionHistory history = new DiscussionHistory();
		    history.setMaxStanzas(0);
			room.join(nickname,null,history,SmackConfiguration.getPacketReplyTimeout());
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
				System.out.println("To: " + msg.getTo());
				System.out.println("From: " + msg.getFrom());
				
				System.out.println("Received message: " + msg.getBody());
				ChatEvent event = ParseMessage(msg);
				if(event != null)
				{
					// message was for fred
					eventQueue.add(event);
				}
			}
			
		});
		
		// finally greet everyone!
		Message msg = room.createMessage();
		msg.setBody("Hello everyone, I'm back!");
		try {
			room.sendMessage(msg);
		} catch (XMPPException e) {
			e.printStackTrace();
		}
	}
	
	// bot main event loop function
	public void run()
	{
		// main event loop
		while(true)
		{
			ChatEvent event = null;
			
			try {
				// block on empty queue
				event = eventQueue.take();
			} catch (InterruptedException e) {
				e.printStackTrace();
				return;
			}
			
			// quit if exit event was posted
			if(event instanceof ExitChatEvent)
			{
				System.out.println("Qutting now!");
				try {
					room.sendMessage("Goodbye!");
				} catch (XMPPException e) {
					e.printStackTrace();
				}
				room.leave();
				conn.disconnect();
				return;
			}
			event.process(this);
		}
	}
	
	// Function parses the a message and creates an appropriate ChatEvent object based
	// on the contents
	public ChatEvent ParseMessage(Message msg)
	{
		ChatEvent event;
		
		if(!msg.getBody().startsWith(mention))
		{
			System.out.println("Message not for me!");
			return null;
		}
		
		String rest = msg.getBody().substring(mention.length()).trim();
		String [] from = msg.getFrom().split("/");
		
		for(Class<? extends ChatEvent> c : Commands.commands)
		{
			String command;
			
			// attempt to create a new event object
			try {
				event = c.getDeclaredConstructor(String.class).newInstance(from[1]);
			} catch (Exception e) {
				e.printStackTrace();
				break;
			}
			
			// attempt to get the command associated with this event object
			try {
				command = (String)c.getMethod("command", (Class<?>[])null).invoke(event);
			} catch (Exception e) {
				e.printStackTrace();
				break;
			}
			
			// continue if the command doesn't match
			if(!rest.matches(command+".*"))
			{
				continue;
			}
			
			System.out.println("Received a '" + command + "' command");
			
			// if initialisation goes OK, return this new event
			if(event.init(rest))
			{
				return event;
			}
			
			System.out.println("Event initialisation of command '" + command + "' failed! Moving on...");
		}
		
		return null;
		
		
	}

	@Override
	public void chatEventCallback(String msg) {
		
		System.out.println("Attempting to send message to room!");
		try {
			room.sendMessage(msg);
		} catch (XMPPException e) {
			e.printStackTrace();
		}
		
	}
	
	

}
