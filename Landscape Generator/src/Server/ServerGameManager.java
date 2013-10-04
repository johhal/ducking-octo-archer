package Server;

import java.awt.Point;
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

	private Session session;

	private ServerSocket serverSocket;

	private Gson gson;
	boolean needTiles = true;

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

		// Skapa v�rlden och generera omr�den
		LandscapeGenerator landGen = new LandscapeGenerator();
		landscape = new Landscape(landGen.generate(boardWidth, boardHeight));

		// Skapa Varelser
		humanoidManager = new HumanoidManager();
		humanoidManager.initialize(landscape);

		// inputManager = new InputManager();
		// inputManager.initilize();

		// gl.initialize(screenWidth, screenHeight, tileSize, GUIWidth);

		// guiHandler = new GUIHandler();
		// guiHandler.Initialize(3, 2);

		try {
			serverSocket = new ServerSocket(12345);
			Socket socket = (Socket) serverSocket.accept();
			session = new Session(socket);
			outputManager.addSession(session);
			TCPReadThread serverReadThread = new TCPReadThread(session,
					readQueue, true);
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
		while (true) {
			update();
			// draw();
		}
	}

	// private String tileToString(int x, int y) {
	// StringBuilder sb = new StringBuilder();
	// sb.append(landscape.getTile(x, y).toString());
	// sb.append("\n");
	// sb.append(humanoidManager.humanoidToString(new Point(x, y)));
	// return sb.toString();
	// }

	public void update() {
		// int input = inputManager.update();

		if (inputManager.hasInput()) {
			Point p = inputManager.getNewHousePoint();
			if (session.getMoney() >= 100) {
				if (p != null) {
					inputManager.resetNewHousePoint();
					humanoidManager.addHouse(p.x, p.y);
					session.removeMoney(100);
				}
			}
			inputManager.resetInput();
		}

		if (needTiles) {
			ProtocolMessage pm = new ProtocolMessage(ProtocolEnum.TYPE.UPDATE,
					ProtocolEnum.EVENT.MAP);
			Parameter p = new Parameter(ProtocolEnum.PARAMETER_TYPE.TILES);
			p.setData(landscape.getTiles());
			pm.addParameter(p);
			outputManager.sendToAll(gson.toJson(pm));
			needTiles = false;
		}

		humanoidManager.update();

		int moneyGeneratedThisTurn = humanoidManager
				.getMoneyGeneratedThisTurn();
		if (moneyGeneratedThisTurn > 0) {
			System.out.println("ServerGameManager: moneyGenerated:"+moneyGeneratedThisTurn);
			session.addMoney(moneyGeneratedThisTurn);
			ProtocolMessage pm = new ProtocolMessage(ProtocolEnum.TYPE.UPDATE,
					ProtocolEnum.EVENT.MAP);
			Parameter p = new Parameter(ProtocolEnum.PARAMETER_TYPE.MONEY);
			p.setData(session.getMoney());
			pm.addParameter(p);
			outputManager.sendToAll(gson.toJson(pm));
		}

		if (humanoidManager.housesUpdated()) {
			ProtocolMessage pm = new ProtocolMessage(ProtocolEnum.TYPE.UPDATE,
					ProtocolEnum.EVENT.MAP);
			Parameter p = new Parameter(ProtocolEnum.PARAMETER_TYPE.HOUSES);
			p.setData(humanoidManager.getHouses());
			pm.addParameter(p);
			outputManager.sendToAll(gson.toJson(pm));
			humanoidManager.setHouseUpdated(false);
		}

		if (humanoidManager.humansUpdated()) {
			ProtocolMessage pm = new ProtocolMessage(ProtocolEnum.TYPE.UPDATE,
					ProtocolEnum.EVENT.MAP);
			Parameter p = new Parameter(ProtocolEnum.PARAMETER_TYPE.HUMANS);
			p.setData(humanoidManager.getHumans());
			pm.addParameter(p);
			outputManager.sendToAll(gson.toJson(pm));
			humanoidManager.setHumanUpdated(false);
		}

		if (humanoidManager.zombiesUpdated()) {
			ProtocolMessage pm = new ProtocolMessage(ProtocolEnum.TYPE.UPDATE,
					ProtocolEnum.EVENT.MAP);
			Parameter p = new Parameter(ProtocolEnum.PARAMETER_TYPE.ZOMBIES);
			p.setData(humanoidManager.getZombies());
			pm.addParameter(p);
			outputManager.sendToAll(gson.toJson(pm));
			humanoidManager.setZombieUpdated(false);
		}

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
