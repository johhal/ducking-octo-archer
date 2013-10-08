package network;

import java.net.Socket;
import java.util.ArrayList;

public class Session {

    private String playerName;
    private Socket socket;
    private int money;
    private boolean moneyUpdated;

    public Session(Socket socket) {
        this.socket = socket;
        money = 100;
        moneyUpdated = true;
    }

    public void logInAs(String empID, String empName, ArrayList<String> permissions) {
        this.playerName = empName;
    }
    
    public boolean isMoneyUpdated(){
    	return moneyUpdated;
    }
    
    public void setMoneyUpdated(boolean moneyUpdated){
    	this.moneyUpdated = moneyUpdated;
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
		System.out.println("Earned "+moneyGeneratedThisTurn);
		money+=moneyGeneratedThisTurn;
		moneyUpdated = true;
		
	}

	public void removeMoney(int i) {
		System.out.println("Removing money: "+i);
		money-=i;
		moneyUpdated = true;
	}
}

