package fredbot;


public class HelpChatEvent extends ChatEvent {
	private static final String HELP_STRING = "Available commands (preceded by '@fred'):\n"
			+ "hello - Say hi to me :)\n"
			+ "paper <YYYYpPPqQQ> - get a link for a specific paper\n"
			+ "help - display this message\n"
			+ "code is available at https://github.com/dozzman/fredbot, please send Dorian your github emails so he can add you as contributors (if you would like to contribute).";
		
	public HelpChatEvent(String from) {
		super(from);
	}

	@Override
	public boolean process(ChatEventCallback callback) {
		callback.chatEventCallback(HELP_STRING);
		return true;
	}

	@Override
	public String command() {
		return "help";
	}

	@Override
	public boolean init(String msg) {
		//stub
		return true;
	}

}
