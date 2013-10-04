package Server;

import java.awt.Point;
import java.util.ArrayList;

import com.google.gson.internal.StringMap;

import network.Parameter;
import network.ProtocolEnum;

public class ServerInputManager {
	private Point house;
	private boolean hasInput;

	public void newHouse(ArrayList<Parameter> parameterList) {
		if(parameterList != null){
			for(Parameter p : parameterList){
				if(p.getParameterType().equals(ProtocolEnum.PARAMETER_TYPE.POINT)){
					StringMap<Double> temp = (StringMap<Double>) p.getData();
					house = new Point(temp.get("x").intValue(), temp.get("y").intValue());
					hasInput = true;
				}
			}
		}
	}
	
	public Point getNewHousePoint(){
		return house;
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
