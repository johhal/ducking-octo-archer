package network;

import java.net.Socket;
import java.util.ArrayList;

public class Session {

    private String playerName;
    private Socket socket;

    public Session(Socket socket) {
        this.socket = socket;
    }

    public void logInAs(String empID, String empName, ArrayList<String> permissions) {
        this.playerName = empName;
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
}

