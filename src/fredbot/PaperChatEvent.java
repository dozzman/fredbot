package fredbot;

import org.jivesoftware.smack.packet.Message;

public class PaperChatEvent extends ChatEvent {
	private int year;
	private int paper;
	private int question;
	@Override
	public void process(ChatEventCallback callback) {
		Message msg = new Message();
		msg.setBody("Getting " + Integer.toString(year) + ", paper " + Integer.toString(paper) + ", question" + Integer.toString(question));
		callback.chatEventCallback(msg);
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
