package fredbot;


public class PaperChatEvent extends ChatEvent {
	public static final String PAPER_PREFIX = "http://www.cl.cam.ac.uk/teaching/exams/pastpapers/";
	public static final String PAPER_SUFFIX = ".pdf";
	
	private int year;
	private int paper;
	private int question;
	
	public PaperChatEvent(String from) {
		super(from);
	}
	
	@Override
	public boolean init(String msg)
	{
		// split the string into year,paper,question
		String paperString = msg.split(" ")[1];
		
		// strip off leading y
		if(paperString.startsWith("y"))
		{
			paperString = paperString.substring(1);
		}
		
		String [] ypq = paperString.split("[ypq]");
		
		// set the paper values in the event
		try
		{
			year = Integer.parseInt(ypq[0]);
			paper = Integer.parseInt(ypq[1]);
			question = Integer.parseInt(ypq[2]);
		}
		catch (NumberFormatException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	@Override
	public boolean process(ChatEventCallback callback) {
		callback.chatEventCallback("Getting " + Integer.toString(year) + ", paper " + Integer.toString(paper) + ", question " + Integer.toString(question) + "...");
		
		callback.chatEventCallback(PAPER_PREFIX+
				"y"+Integer.toString(year)+
				"p"+Integer.toString(paper)+
				"q"+Integer.toString(question)+PAPER_SUFFIX);
	
		return true;
	}
	
	@Override
	public String command() {
		return "paper";
	}
}
