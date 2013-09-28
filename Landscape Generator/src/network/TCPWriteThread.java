package network;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
/**
 * A thread which reads Jobs from a JobQueue and sends the message provided in
 * the Job to the SSLSocket provided in the Job.
 *
 * @author Johan Hallberg
 * @author Carl Bjerggaard
 */
public class TCPWriteThread extends Thread {

    private boolean running;
    private JobQueue writeQueue;
    private Job<String> job;
    private BufferedWriter bufferedWriter;
    private boolean isServerThread;

    /**
     * Constructor.
     *
     * @param writeQueue The JobQueue the thread will read from
     */
    public TCPWriteThread(JobQueue writeQueue, boolean isServerThread) {
        this.running = true;
        this.writeQueue = writeQueue;
        this.isServerThread = isServerThread;

    }
    @Override
    /**
     * Starts the thread.
     */
    public void run() {
        while (running) {
            try {
                job = writeQueue.get();
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(job.getSession().getSocket().getOutputStream()));
                bufferedWriter.write(job.getMessage(), 0, job.getMessage().length());
                bufferedWriter.newLine();
                bufferedWriter.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
