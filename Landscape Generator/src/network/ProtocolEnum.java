package network;


public class ProtocolEnum {

    public enum TYPE {
        UPDATE, SUBMIT, ERROR
    }

    public enum EVENT {
		MAP, NEW_HOUSE
    }



    public enum PARAMETER_TYPE {
    	HOUSES, ZOMBIES, HUMANS, TILES, LANDSCAPE, DIMENSIONS, POINT
    }
}
