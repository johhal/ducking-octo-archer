package network;

import java.net.Socket;
import java.util.ArrayList;

public class Session {

    private String playerName;
    private Socket socket;
    private int money;

    public Session(Socket socket) {
        this.socket = socket;
        money = 0;
    }

    public void logInAs(String empID, String empName, ArrayList<String> permissions) {
        this.playerName = empName;
    }
    
    public void setMoney(int money){
    	this.money = money;
    }
    
    public int getMoney(){
    	return money;
    }

    public void logOut() {
        playerName = null;
    }


    public Socket getSocket() {
        return socket;
    }

    public String getPlayerName() {
        return playerName;
    }

      public boolean equals(Session s){
        if(s == null){
            return false;
        }
       
        return playerName.equals(s.getPlayerName()) && socket.equals(s.getSocket());
    }
    
      public String toString(){
    	  return socket.toString();
      }

	public void addMoney(int moneyGeneratedThisTurn) {
		money+=moneyGeneratedThisTurn;
		
	}

	public void removeMoney(int i) {
		money-=i;
	}
}

