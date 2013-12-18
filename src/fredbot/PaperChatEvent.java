package fredbot;


public class PaperChatEvent extends ChatEvent {
	private int year;
	private int paper;
	private int question;
	@Override
	public void process(ChatEventCallback callback) {
		callback.chatEventCallback("Getting " + Integer.toString(year) + ", paper " + Integer.toString(paper) + ", question " + Integer.toString(question) + "...");
		
		callback.chatEventCallback(Strings.PAPER_PREFIX+"y"+Integer.toString(year)+"p"+Integer.toString(paper)+"q"+Integer.toString(question)+Strings.PAPER_SUFFIX);
		/*Thread getThread = new Thread() {
			@Override
			public void run()
			{
				
			}
		};*/
		
	}
	
	public int getYear() {
		return year;
	}
	
	public void setYear(int year) {
		this.year = year;
	}
	
	public int getPaper() {
		return paper;
	}
	
	public void setPaper(int paper) {
		this.paper = paper;
	}
	
	public int getQuestion() {
		return question;
	}
	
	public void setQuestion(int question) {
		this.question = question;
	}

}
