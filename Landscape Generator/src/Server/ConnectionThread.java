package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import network.Session;

public class ConnectionThread extends Thread {

	private ServerSocket serverSocket;
	private int port;
	private ConnectionManager connectionManager;

	public ConnectionThread(int port, ConnectionManager connectionManager) {
		this.port = port;
		this.connectionManager = connectionManager;
	}

	public void run() {
		try {
			serverSocket = new ServerSocket(port);
			while (connectionManager.acceptingConnections()) {
				Socket socket = (Socket) serverSocket.accept();
				connectionManager.newSession(new Session(socket));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
