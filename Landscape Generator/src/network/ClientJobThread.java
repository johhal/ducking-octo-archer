package network;

import java.util.ArrayList;
import network.JobThread;
import network.JobQueue;

public class ClientJobThread extends JobThread {

    private Session session;

    public ClientJobThread(JobQueue readQueue, JobQueue writeQueue, Session session) {
        super(readQueue, writeQueue, false);

        this.session = session;
    }


    protected String handleUpdate(ProtocolMessage pm) {
        return gson.toJson(pm);
    }


    protected String handleSubmit(ProtocolMessage pm) {
        switch (pm.getEvent()) {
            
        }
        return null;
    }

    protected String handleLoginLogout(ProtocolMessage pm) {
        switch (pm.getEvent()) {
            
        }
        return null;

    }

    protected void handleAnswer(String message) {
    }


    protected String handleError(ProtocolMessage pm) {
        return null;
    }
}
