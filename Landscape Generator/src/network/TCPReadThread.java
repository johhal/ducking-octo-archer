package network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class TCPReadThread extends Thread {

    private Session session;
    private JobQueue readQueue;
    private boolean isServerThread;

  
    public TCPReadThread(Session session, JobQueue readQueue, boolean isServerThread) {
        this.session = session;
        this.readQueue = readQueue;
        this.isServerThread = isServerThread;
    }


    public void run() {
        try {
            BufferedWriter w = new BufferedWriter(new OutputStreamWriter(
                    session.getSocket().getOutputStream()));
            BufferedReader r = new BufferedReader(new InputStreamReader(
                    session.getSocket().getInputStream()));
            String message;
            while ((message = r.readLine()) != null) {
                readQueue.put(new Job(session, message));
            }
            w.close();
            r.close();
            session.getSocket().close();
        } catch (IOException e) {

        }
    }
}
