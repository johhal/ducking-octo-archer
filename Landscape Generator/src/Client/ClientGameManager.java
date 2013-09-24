package Client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.lwjgl.LWJGLException;

import OpenGL.OpenGL;
import Server.DrawingObject;
import Server.House;
import Server.Human;
import Server.Tile;
import Server.Zombie;
import network.*;

public class ClientGameManager {
	private String address;
	private int port;
	
	private ClientJobThread clientJobThread;
	private TCPReadThread readThread;
	private TCPWriteThread writeThread;
	private JobQueue readQueue;
	private JobQueue writeQueue;
	private Session session;
	private GUIModel guiModel;
	
	private OpenGL gl;
	private DrawingObject otd;
	
	public ClientGameManager(String address, int port){
		this.address = address;
		this.port = port;
	}
	
	public void init() throws UnknownHostException, IOException, LWJGLException{
		guiModel = new GUIModel();
		initNetwork();
		
		gl = new OpenGL();
		gl.initialize(800, 600, 2, 0);		
	}

	private void initNetwork() throws UnknownHostException, IOException {
		session = new Session(new Socket(address,port));
		System.out.println("Client Connected!");
		readQueue = new JobQueue();
		writeQueue = new JobQueue();
		writeThread = new TCPWriteThread(writeQueue, false);
		clientJobThread = new ClientJobThread(readQueue, session, guiModel);	
		readThread = new TCPReadThread(session, readQueue, false);
	}
	
	 public void draw() {
		 gl.initDraw();
		 
		 float n;
		 short nt;
		 DrawingObject cd;
		 int i = 0;
		 int j = 0;
		 Tile[][] board = guiModel.getTiles();
		 for(Tile[] ta: board)
		 {
			if(i<board.length)
				 i++;
			 else
				 i = 0;
			 nt = 0;
			 for(Tile t : ta)
			 {
				 if(j < ta.length)
				 otd = t.draw(i, j);
			 }
		 }
		 for(Human h:  guiModel.getHumans())
		 {
			 cd = h.draw();
			 gl.convertAndDraw(cd.posX, cd.posY, cd.cr, cd.cg, cd.cb, cd.notTile);
		 }
		 for(Zombie z:  guiModel.getZombies())
		 {
		 	cd = z.draw();
		 	gl.convertAndDraw(cd.posX, cd.posY, cd.cr, cd.cg, cd.cb, cd.notTile);
		 }
		
		 for(House h: guiModel.getHouses())
		 {
			 cd = h.draw();
			 gl.convertAndDraw(cd.posX, cd.posY, cd.cr, cd.cg, cd.cb, cd.notTile);
		 }
		 //guiHandler.draw();
		
		 gl.endDraw();
		 }

}
