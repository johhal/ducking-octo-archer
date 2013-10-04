package Client;

import java.awt.Point;

import com.google.gson.Gson;

import network.Job;
import network.JobQueue;
import network.Parameter;
import network.ProtocolEnum;
import network.ProtocolMessage;
import network.Session;

public class MessageGenerator {
	private JobQueue writeQueue;
	private Session session;
	private Gson gson;

	public MessageGenerator(JobQueue writeQueue, Session session) {
		this.writeQueue = writeQueue;
		this.session = session;
		gson = new Gson();
	}

	public void putHouse(Point p) {
		
		ProtocolMessage pm = new ProtocolMessage(ProtocolEnum.TYPE.SUBMIT, ProtocolEnum.EVENT.NEW_HOUSE);
		Parameter param = new Parameter(ProtocolEnum.PARAMETER_TYPE.POINT);
		param.setData(p);
		pm.addParameter(param);
		writeQueue.put(new Job<String>(session, gson.toJson(pm)));
	}
	

}
