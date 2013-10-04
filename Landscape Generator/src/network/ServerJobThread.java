package network;

import Server.ServerInputManager;



public class ServerJobThread extends JobThread {
    
	private ServerInputManager inputManager;
    public ServerJobThread(JobQueue readQueue, ServerInputManager inputManager) {
        super(readQueue, true);
        this.inputManager = inputManager;
    }

  
    protected void handleUpdate(ProtocolMessage pm) {
    	ProtocolEnum.EVENT event = pm.getEvent();
    	if(event != null){
    		switch (event){
    		case MAP:
    			
    			break;
    		}
    	}
    }

    
    protected void handleSubmit(ProtocolMessage pm) {
    	ProtocolEnum.EVENT event = pm.getEvent();
    	if(event != null){
    		switch (event){
    		case NEW_HOUSE:
    			inputManager.newHouse(pm.getParameterList());
    			break;
    		}
    	}
    }

	@Override
	protected void handleError(ProtocolMessage pm) {
	}


}

