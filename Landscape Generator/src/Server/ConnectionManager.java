package Server;

import network.Session;

public class ConnectionManager {
	private boolean acceptingConnections;
	private Session session;
	private boolean hasNewSession;
	
	public ConnectionManager() {
		acceptingConnections = true;
		hasNewSession =false;
	}

	public void acceptingConnections(boolean acceptingConnections) {
		this.acceptingConnections = acceptingConnections;
	}

	public synchronized void newSession(Session session) {
		while (hasNewSession) {
			try {
				System.out.println("ALready pending connection");
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.session = session;
		hasNewSession = true;
		System.out.println("New session available");
		notifyAll();
	}
	public boolean hasNewSession(){
		return hasNewSession;
	}
	public synchronized Session getSession() {
		System.out.println("Getting session");
			hasNewSession = false;
			notifyAll();
			return session;
	}

	public boolean acceptingConnections() {
		return acceptingConnections;
	}

}
