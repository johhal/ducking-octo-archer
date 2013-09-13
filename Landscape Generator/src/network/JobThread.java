package network;

import com.google.gson.Gson;
import java.util.logging.Level;
import java.util.logging.Logger;


public abstract class JobThread extends Thread {

    protected Job<String> job;
    protected JobQueue readQueue;
    protected JobQueue writeQueue;
    private boolean running;
    protected Gson gson;
    private boolean isServerThread;


    public JobThread(JobQueue readQueue, JobQueue writeQueue, boolean isServerThread) {
        this.readQueue = readQueue;
        this.writeQueue = writeQueue;
        this.running = true;
        this.isServerThread = isServerThread;
        gson = new Gson();
    }

    public void run() {
        while (running) {
            try {
            	System.out.println("Waiting for data");
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

    protected abstract String handleUpdate(ProtocolMessage pm);

    protected abstract String handleSubmit(ProtocolMessage pm);

    protected abstract String handleError(ProtocolMessage pm);
}
