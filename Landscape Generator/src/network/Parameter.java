package network;


public class Parameter {

    private ProtocolEnum.PARAMETER_TYPE parameterType;
    private Object data;

    public Parameter(ProtocolEnum.PARAMETER_TYPE parameterType) {
        this.parameterType = parameterType;
    }

    public ProtocolEnum.PARAMETER_TYPE getParameterType() {
        return parameterType;
    }

    public Object getParameterValueHashMap() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getData() {
        return data;
    }
}

