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
	private int port;

	private JobQueue writeQueue;
	private JobQueue readQueue;
	private ServerJobThread serverJobThread;
	private TCPWriteThread writeThread;
	private OutputManager outputManager;
	private ServerInputManager inputManager;
	private ConnectionThread connectionThread;
	private ConnectionManager connectionManager;

	private Session session;

	private ServerSocket serverSocket;

	private Gson gson;
	boolean needTiles = true;

	// private GUIHandler guiHandler;

	private HumanoidManager humanoidManager;
	// public InputManager inputManager;

	Landscape landscape;

	public void initialize(int _boardWidth, int _boardHeight, int _tileSize,
			int port) throws LWJGLException {
		this.port = port;
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

		// try {
		// serverSocket = new ServerSocket(12346);
		// Socket socket = (Socket) serverSocket.accept();
		// session = new Session(socket);
		// outputManager.addSession(session);
		// TCPReadThread serverReadThread = new TCPReadThread(session,
		// readQueue, true);
		// serverReadThread.start();
		// run();
		//
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		run();

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

		connectionManager = new ConnectionManager();
		connectionThread = new ConnectionThread(port, connectionManager);
		connectionThread.start();

	}

	public void run() {
		// Spela!!
		while (true) {
			getConnections();
			update();
			// draw();
		}
	}

	private void getConnections() {
		if (connectionManager.hasNewSession()) {
			Session temp = connectionManager.getSession();
			session = temp;
			outputManager.addSession(temp);
			TCPReadThread serverReadThread = new TCPReadThread(temp,
					readQueue, true);
			serverReadThread.start();
		}

	}

	public void update() {
		readInput();

		//if (needTiles) {
			sendTiles();
		//}

		humanoidManager.update();
		sendHumanoids();
	}

	private void sendTiles() {
		ProtocolMessage pm = new ProtocolMessage(ProtocolEnum.TYPE.UPDATE,
				ProtocolEnum.EVENT.MAP);
		Parameter p = new Parameter(ProtocolEnum.PARAMETER_TYPE.TILES);
		p.setData(landscape.getTiles());
		pm.addParameter(p);
		outputManager.sendToAll(gson.toJson(pm));
		needTiles = false;
	}

	private void readInput() {
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
	}

	private void sendHumanoids() {
		if (session != null) {
			int moneyGeneratedThisTurn = humanoidManager
					.getMoneyGeneratedThisTurn();
			if (moneyGeneratedThisTurn > 0) {
				session.addMoney(moneyGeneratedThisTurn);
				ProtocolMessage pm = new ProtocolMessage(
						ProtocolEnum.TYPE.UPDATE, ProtocolEnum.EVENT.MAP);
				Parameter p = new Parameter(ProtocolEnum.PARAMETER_TYPE.MONEY);
				p.setData(session.getMoney());
				pm.addParameter(p);
				outputManager.sendToAll(gson.toJson(pm));
			}

			if (humanoidManager.housesUpdated()) {
				ProtocolMessage pm = new ProtocolMessage(
						ProtocolEnum.TYPE.UPDATE, ProtocolEnum.EVENT.MAP);
				Parameter p = new Parameter(ProtocolEnum.PARAMETER_TYPE.HOUSES);
				p.setData(humanoidManager.getHouses());
				pm.addParameter(p);
				outputManager.sendToAll(gson.toJson(pm));
				humanoidManager.setHouseUpdated(false);
			}

			if (humanoidManager.humansUpdated()) {
				ProtocolMessage pm = new ProtocolMessage(
						ProtocolEnum.TYPE.UPDATE, ProtocolEnum.EVENT.MAP);
				Parameter p = new Parameter(ProtocolEnum.PARAMETER_TYPE.HUMANS);
				p.setData(humanoidManager.getHumans());
				pm.addParameter(p);
				outputManager.sendToAll(gson.toJson(pm));
				humanoidManager.setHumanUpdated(false);
			}

			if (humanoidManager.zombiesUpdated()) {
				ProtocolMessage pm = new ProtocolMessage(
						ProtocolEnum.TYPE.UPDATE, ProtocolEnum.EVENT.MAP);
				Parameter p = new Parameter(ProtocolEnum.PARAMETER_TYPE.ZOMBIES);
				p.setData(humanoidManager.getZombies());
				pm.addParameter(p);
				outputManager.sendToAll(gson.toJson(pm));
				humanoidManager.setZombieUpdated(false);
			}
		}
	}
}
