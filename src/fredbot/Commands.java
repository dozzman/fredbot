package fredbot;

import java.util.Arrays;
import java.util.LinkedList;

// this class contains a list of classes which correspond to ChatEvents invoked by sending commands to the bot
public class Commands {
	@SuppressWarnings("unchecked") // the compiler doesn't like arrays of generics
	public static LinkedList<Class<? extends ChatEvent>> commands = new LinkedList<Class<? extends ChatEvent>>(
															Arrays.asList(
																HelloChatEvent.class,
																HelpChatEvent.class,
																PaperChatEvent.class,
																ExitChatEvent.class
																));
}
