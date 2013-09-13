package network;

import java.util.ArrayList;


public class ServerJobThread extends JobThread {

    private ArrayList<Parameter> parameterList;

    public ServerJobThread(JobQueue readQueue, JobQueue writeQueue) {
        super(readQueue, writeQueue, true);
    }

  
    protected String handleUpdate(ProtocolMessage pm) {
       return null;
    }

    
    protected String handleSubmit(ProtocolMessage pm) {
        return null;
    }

    @Override

    protected void handleAnswer(String message) {
        if (message != null) {
            writeQueue.put(new Job(job.getSession(), message));
        }
    }


	@Override
	protected String handleError(ProtocolMessage pm) {
		// TODO Auto-generated method stub
		return null;
	}


}

