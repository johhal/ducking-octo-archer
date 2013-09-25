package Server;

import java.util.ArrayList;

import network.*;

public class OutputManager {
	
	private JobQueue sendQueue;
	private ArrayList<Session> sessionList;
	
	public OutputManager(JobQueue sendQueue){
		this.sendQueue = sendQueue;
	}
	
	public void init(){
		sessionList = new ArrayList<Session>();
	}
	public void sendToAll(String message){
		System.out.println("OutputManager: Message is "+message);
		for(Session s: sessionList){
			System.out.println("OutputManager: queued to "+s.toString());
			sendQueue.put(new Job<String>(s,message));
		}
	}
	public void addSession(Session s){
		sessionList.add(s);
	}
}
