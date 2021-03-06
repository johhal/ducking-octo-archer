package network;

import com.google.gson.Gson;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class JobThread extends Thread {

	protected Job<String> job;
	protected JobQueue readQueue;
	private boolean running;
	protected Gson gson;
	private boolean isServerThread;

	public JobThread(JobQueue readQueue, boolean isServerThread) {
		this.readQueue = readQueue;
		this.running = true;
		this.isServerThread = isServerThread;
		gson = new Gson();
	}

	public void run() {
		while (running) {
			try {
				job = readQueue.get();
				ProtocolMessage pm = gson.fromJson(job.getMessage(),
						ProtocolMessage.class);
				if (pm != null) {
					switch (pm.getType()) {
					case UPDATE:
						handleUpdate(pm);
						break;
					case SUBMIT:
						handleSubmit(pm);
						break;
					// case ERROR:
					// handleError(pm);
					// break;
					}
				}
			} catch (InterruptedException ex) {
				// if (isServerThread) {
				// BugLogger.logServer("JobThread.java run()", ex,
				// BugLogger.ERROR);
				// } else {
				// BugLogger.logServer("JobThread.java run()", ex,
				// BugLogger.ERROR);
				// }
			}
		}
	}

	protected abstract void handleUpdate(ProtocolMessage pm);

	protected abstract void handleSubmit(ProtocolMessage pm);

	protected abstract void handleError(ProtocolMessage pm);
}
