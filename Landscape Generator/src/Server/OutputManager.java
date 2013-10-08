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
			send(message,s);
		}
	}
	
	public ArrayList<Session> getSessionList(){
		return sessionList;
	}
	public void addSession(Session s){
		sessionList.add(s);
		System.out.println("Outputmanager: Session added. Sessions: "+ sessionList.size());
	}

	public void send(String message, Session s) {
		sendQueue.put(new Job<String>(s,message));
	}
}
