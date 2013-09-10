package network;

import com.google.gson.Gson;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Superclass to threads which will handle Jobs from the ReadQueues.
 *
 * @author Johan Hallberg
 * @author Carl Bjerggaard
 */
public abstract class JobThread extends Thread {

    protected Job<String> job;
    protected JobQueue readQueue;
    protected JobQueue writeQueue;
    private boolean running;
    protected Gson gson;
    private boolean isServerThread;

    /**
     * Constructor.
     *
     * @param readQueue the Queue from which the thread will fetch Jobs.
     * @param writeQueue the Queue to which the thread may use to communicate
     * with the JobThread on the other side.
     */
    public JobThread(JobQueue readQueue, JobQueue writeQueue, boolean isServerThread) {
        this.readQueue = readQueue;
        this.writeQueue = writeQueue;
        this.running = true;
        this.isServerThread = isServerThread;
        gson = new Gson();
    }

    @Override
    /**
     * Describes the workflow of all JobThreads; first it fetches the Job from
     * the readqueue, then it parses the actual message, then it handels the
     * corresponding type request.
     */
    public void run() {
        while (running) {
            try {
                job = readQueue.get();
                System.out.println("RECEIVED!!!");
                System.out.println(job.getMessage());
//                ProtocolMessage pm = gson.fromJson(job.getMessage(), ProtocolMessage.class);
//                String answer = null;
//                if (pm != null) {
//                    switch (pm.getType()) {
//                        case UPDATE:
//                            if (job.getSession().isLoggedIn()) {
//
//                                answer = handleUpdate(pm);
//                            }
//                            break;
//                        case SUBMIT:
//                            if (job.getSession().isLoggedIn()) {
//                                answer = handleSubmit(pm);
//                            }
//                            break;
//                        case LOGINOUT:
//
//                            answer = handleLoginLogout(pm);
//                            break;
//                        case ERROR:
//                            answer = handleError(pm);
//                            break;
//                    }
//                    handleAnswer(answer);
                
            } catch (InterruptedException ex) {
//                if (isServerThread) {
//                    BugLogger.logServer("JobThread.java run()", ex, BugLogger.ERROR);
//                } else {
//                    BugLogger.logServer("JobThread.java run()", ex, BugLogger.ERROR);
//                }
            }
        }
    }

    protected abstract void handleAnswer(String message);

//    protected abstract String handleUpdate(ProtocolMessage pm);
//
//    protected abstract String handleSubmit(ProtocolMessage pm);
//
//    protected abstract String handleLoginLogout(ProtocolMessage pm);
//
//    protected abstract String handleError(ProtocolMessage pm);
}
