package Server;

import java.awt.Point;
import java.util.ArrayList;

import com.google.gson.internal.StringMap;

import network.Parameter;
import network.ProtocolEnum;
import network.Session;

public class ServerInputManager {
	private Point house;
	private boolean hasInput;
	private Session session;

	public void newHouse(ArrayList<Parameter> parameterList, Session session) {
		if(parameterList != null){
			for(Parameter p : parameterList){
				if(p.getParameterType().equals(ProtocolEnum.PARAMETER_TYPE.POINT)){
					StringMap<Double> temp = (StringMap<Double>) p.getData();
					house = new Point(temp.get("x").intValue(), temp.get("y").intValue());
					hasInput = true;
					this.session = session;
				}
			}
		}
	}
	
	public Point getNewHousePoint(){
		return house;
	}
	
	public Session getSession(){
		return session;
	}
	
	public boolean hasInput(){
		return hasInput;
	}
	public void resetInput(){
		hasInput = false;
	}

	public void resetNewHousePoint() {
		house = null;
	}
	
}
