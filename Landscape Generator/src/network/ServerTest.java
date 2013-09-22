//package network;
//
//import java.io.IOException;
//import java.net.ServerSocket;
//import java.net.Socket;
//
//public class ServerTest {
//
//	/**
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		JobQueue readQueue = new JobQueue();
//        JobQueue writeQueue = new JobQueue();
//        System.out.println("Created queues");
//        new ServerJobThread(readQueue).start();
//        System.out.println("Started jobthread");
//        TCPWriteThread serverWriteThread = new TCPWriteThread(writeQueue, true);
//        serverWriteThread.start();
//        
//        ServerSocket serverSocket;
//		try {
//			serverSocket = new ServerSocket(12345);
//			while (true) {
//				System.out.println("Waiting for connection");
//				Socket socket = (Socket) serverSocket.accept();
//				System.out.println("Connected");
//				Session s = new Session(socket);
//				TCPReadThread serverReadThread = new TCPReadThread(s, readQueue, true);
//				serverReadThread.start();
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        
//        
//	}
//
//}
