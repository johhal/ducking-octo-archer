package network;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Socket socket;
		try {
			socket = new Socket("127.0.0.1", 12345);
			JobQueue readQueue = new JobQueue();
			JobQueue writeQueue = new JobQueue();
			TCPWriteThread writeThread = new TCPWriteThread(writeQueue, false);
			Session session = new Session(socket);
			TCPReadThread readThread = new TCPReadThread(session, readQueue,
					false);
			ClientJobThread clientJobThread = new ClientJobThread(readQueue,
					writeQueue, session);
			readThread.start();
			writeThread.start();
			clientJobThread.start();
			writeQueue.put(new Job(session, "HEJ"));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
