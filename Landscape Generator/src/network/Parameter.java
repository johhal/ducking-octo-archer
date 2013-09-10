package network;

import java.util.HashMap;


public class Parameter {

    private String parameterType;
    private HashMap<String, String> parameterValueHashMap;

    public Parameter(String parameterType) {
        this.parameterType = parameterType;
        parameterValueHashMap = new HashMap<String, String>();
    }

    public String getParameterType() {
        return parameterType;
    }

    public HashMap<String, String> getParameterValueHashMap() {
        return parameterValueHashMap;
    }

    public Parameter addValue(String key, String value) {
        parameterValueHashMap.put(key, value);
        return this;
    }

    public String getValue(String key) {
        return parameterValueHashMap.get(key);
    }
}

