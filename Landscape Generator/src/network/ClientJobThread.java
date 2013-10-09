package network;

import Client.GUIModel;
import network.JobThread;
import network.JobQueue;

public class ClientJobThread extends JobThread {
	
	private GUIModel guiModel;
	private Session session;

	public ClientJobThread(JobQueue readQueue,
			Session session, GUIModel guiModel) {
		super(readQueue, false);
		
		this.guiModel = guiModel;
		this.session = session;
	}

	protected void handleUpdate(ProtocolMessage pm) {
		if(pm != null)
		{
			switch (pm.getEvent()){
			case MAP:
				guiModel.update(pm);
				break;
			case TIME:
				guiModel.update(pm);
				break;
			}
		}
	}

	protected void handleSubmit(ProtocolMessage pm) {
		switch (pm.getEvent()) {

		}
	}

	protected void handleLoginLogout(ProtocolMessage pm) {
		switch (pm.getEvent()) {

		}

	}

	protected void handleError(ProtocolMessage pm) {
	}
}
