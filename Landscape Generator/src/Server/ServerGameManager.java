package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import network.JobQueue;
import network.Parameter;
import network.ProtocolEnum;
import network.ProtocolMessage;
import network.ServerJobThread;
import network.Session;
import network.TCPReadThread;
import network.TCPWriteThread;

import org.lwjgl.LWJGLException;

import com.google.gson.Gson;

import OpenGL.OpenGL;

public class ServerGameManager {
	private int boardWidth;
	private int boardHeight;

	private JobQueue writeQueue;
	private JobQueue readQueue;
	private ServerJobThread serverJobThread;
	private TCPWriteThread writeThread;
	private OutputManager outputManager;
	private ServerInputManager inputManager;

	private ServerSocket serverSocket;

	private Gson gson;

	// private GUIHandler guiHandler;

	private HumanoidManager humanoidManager;
	// public InputManager inputManager;

	Landscape landscape;

	public void initialize(int _boardWidth, int _boardHeight, int _tileSize)
			throws LWJGLException {
		initNetwork();
		gson = new Gson();

		// Storlek p� omr�den och antal omr�den
		boardWidth = _boardWidth;
		boardHeight = _boardHeight;

		System.out.println("1");
		// Skapa v�rlden och generera omr�den
		LandscapeGenerator landGen = new LandscapeGenerator();
		landscape = new Landscape(landGen.generate(boardWidth, boardHeight));

		System.out.println("2");

		// Skapa Varelser
		humanoidManager = new HumanoidManager();
		humanoidManager.initialize(landscape);

		System.out.println("3");

		// inputManager = new InputManager();
		// inputManager.initilize();

		System.out.println("4");

		//gl.initialize(screenWidth, screenHeight, tileSize, GUIWidth);

		System.out.println("5");

		// guiHandler = new GUIHandler();
		// guiHandler.Initialize(3, 2);
		System.out.println("6");

		try {
			serverSocket = new ServerSocket(12345);
			System.out.println("Waiting for connection");
			Socket socket = (Socket) serverSocket.accept();
			System.out.println("Server Connected");
			Session s = new Session(socket);
			outputManager.addSession(s);
			TCPReadThread serverReadThread = new TCPReadThread(s, readQueue,
					true);
			serverReadThread.start();
			run();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void initNetwork() {

		inputManager = new ServerInputManager();
		writeQueue = new JobQueue();
		readQueue = new JobQueue();
		serverJobThread = new ServerJobThread(readQueue, inputManager);
		writeThread = new TCPWriteThread(writeQueue, true);
		outputManager = new OutputManager(writeQueue);
		outputManager.init();

		serverJobThread.start();
		writeThread.start();
	}

	public void run() {
		// Spela!!
//		while (true) {
			update();
			// draw();
//		}
	}

//	private String tileToString(int x, int y) {
//		StringBuilder sb = new StringBuilder();
//		sb.append(landscape.getTile(x, y).toString());
//		sb.append("\n");
//		sb.append(humanoidManager.humanoidToString(new Point(x, y)));
//		return sb.toString();
//	}

	public void update() {
		// int input = inputManager.update();

		humanoidManager.update();
		System.out.println("Updated");
		
		ProtocolMessage pm = new ProtocolMessage(ProtocolEnum.TYPE.UPDATE,
				ProtocolEnum.EVENT.MAP);
		Parameter p = new Parameter(ProtocolEnum.PARAMETER_TYPE.TILES);
		p.setData(landscape.getTiles());
		pm.addParameter(p);
		outputManager.sendToAll(gson.toJson(pm));
		
		pm = new ProtocolMessage(ProtocolEnum.TYPE.UPDATE,
				ProtocolEnum.EVENT.MAP);
		p = new Parameter(ProtocolEnum.PARAMETER_TYPE.DIMENSIONS);
		p.setData(new int[] {landscape.getBoardWidth(), landscape.getBoardHeight()});
		pm.addParameter(p);
		outputManager.sendToAll(gson.toJson(pm));
		
		
		pm = new ProtocolMessage(ProtocolEnum.TYPE.UPDATE,
				ProtocolEnum.EVENT.MAP);
		p = new Parameter(ProtocolEnum.PARAMETER_TYPE.HOUSES);
		p.setData(humanoidManager.getHouses());
		pm.addParameter(p);
		outputManager.sendToAll(gson.toJson(pm));
		
		pm = new ProtocolMessage(ProtocolEnum.TYPE.UPDATE,
				ProtocolEnum.EVENT.MAP);
		p = new Parameter(ProtocolEnum.PARAMETER_TYPE.HUMANS);
		p.setData(humanoidManager.getHumans());
		pm.addParameter(p);
		outputManager.sendToAll(gson.toJson(pm));
		
		pm = new ProtocolMessage(ProtocolEnum.TYPE.UPDATE,
				ProtocolEnum.EVENT.MAP);
		p = new Parameter(ProtocolEnum.PARAMETER_TYPE.ZOMBIES);
		p.setData(humanoidManager.getZombies());
		pm.addParameter(p);
		outputManager.sendToAll(gson.toJson(pm));
		
		System.out.println("Sent");

		// guiHandler.update();
		// Point mi = inputManager.getClickLocation();
		// Point p = gl.update(gl.getDelta(), input, mi);

		// if((p.x != mi.x || p.y != mi.y) && p.x < boardWidth && p.y <
		// boardHeight && p.x >= 0 && p.y >= 0)
		// humanoidManager.addHouse(p.x, p.y);

		// inputManager.resetClickLocation();
	}
	//
	// public void draw() {
	// gl.initDraw();
	//
	// // ArrayList<DrawingObject> otd = new ArrayList<DrawingObject>();
	// // DrawingObject cd;
	//
	// otd = landscape.draw();
	// for(int i=0; i<otd.size(); i++)
	// {
	// cd = otd.get(i);
	// gl.convertAndDraw(cd.posX, cd.posY, cd.cr, cd.cg, cd.cb, cd.notTile);
	// }
	//
	// otd = humanoidManager.draw();
	//
	// for(int i=0; i<otd.size(); i++)
	// {
	// cd = otd.get(i);
	// gl.convertAndDraw(cd.posX, cd.posY, cd.cr, cd.cg, cd.cb, cd.notTile);
	// }
	// //guiHandler.draw();
	//
	// gl.endDraw();
	// }
}
