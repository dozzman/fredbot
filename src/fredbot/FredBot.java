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
				System.out.println("Received message: " + msg.getBody());
				ChatEvent event = ParseMessage(msg);
				if(event != null)
				{
					// message was for fred
					eventQueue.add(event);
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
				// block on empty queue
				event = eventQueue.take();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				room.leave();
				conn.disconnect();
				return;
			}
			
			event.process(this);
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
		ChatEvent event;
		String [] words = msg.getBody().split(" ");
		
		for(String w : words)
		{
			System.out.println(w);
		}
		
		if(!words[0].equals(mention))
		{
			System.out.println("Message not for me!");
			// message wasn't for fred
			return null;
		}
		
		// from person is in from[1]
		String [] from = msg.getFrom().split("/");
		
		switch(words[1])
		{
		case Commands.HELLO:
			System.out.println("Received a HELLO command");
			event = new HelloChatEvent();
			event.setFrom(from[1]);
			return event;
		case Commands.PAPER:
			System.out.println("Received a PAPER command");
			event = new PaperChatEvent();
			// split the string into year,paper,question
			String [] ypq = words[2].split("[pq]");
			
			for(String w : ypq)
			{
				System.out.println(w);
			}
			// set the paper values in the event
			try
			{
				((PaperChatEvent)event).setYear(Integer.parseInt(ypq[0]));
				((PaperChatEvent)event).setPaper(Integer.parseInt(ypq[1]));
				((PaperChatEvent)event).setQuestion(Integer.parseInt(ypq[2]));
			}
			catch (NumberFormatException e) {
				return null;
			}
			
			return event;
			
		case Commands.HELP:
			System.out.println("Received a HELP command");
			event = new HelpChatEvent();
			return event;
		case Commands.EXIT:
			event = new ExitChatEvent();
			return event;
		default:
			return null;
		}
	}
	
	

}
