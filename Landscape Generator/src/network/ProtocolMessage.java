package network;

import java.util.ArrayList;

public class ProtocolMessage {

    private ProtocolEnum.TYPE type;
    private ProtocolEnum.EVENT event;
    private ArrayList<Parameter> parameterList;

    public ProtocolMessage(ProtocolEnum.TYPE type, ProtocolEnum.EVENT event) {
        this.type = type;
        this.event = event;
        parameterList = new ArrayList<Parameter>();
    }

    public ProtocolEnum.EVENT getEvent() {
        return event;
    }

    public ArrayList<Parameter> getParameterList() {
        return parameterList;
    }

    public ProtocolEnum.TYPE getType() {
        return type;
    }


    public void addParameter(Parameter parameter) {
        parameterList.add(parameter);
    }


    public boolean hasParameters() {
        return !parameterList.isEmpty();
    }

    public void addParameterList(ArrayList<Parameter> parameterList) {
        for (Parameter p : parameterList) {
            this.parameterList.add(p);
        }
    }
}
