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
		for(Session s: sessionList){
			sendQueue.put(new Job<String>(s,message));
		}
	}
}
