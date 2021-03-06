package Client;

import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.lwjgl.LWJGLException;

import OpenGL.OpenGL;
import Server.DrawingObject;
import Server.House;
import Server.Human;
import Server.Tile;
import Server.Zombie;
import network.*;

public class ClientGameManager implements ActionListener {
	private String address;
	private int port;

	private ClientJobThread clientJobThread;
	private TCPReadThread readThread;
	private TCPWriteThread writeThread;
	private JobQueue readQueue;
	private JobQueue writeQueue;
	private Session session;
	private GUIModel guiModel;
	private MessageGenerator messageGenerator;

	private ClientInputManager inputManager;

	private OpenGL gl;

	public ClientGameManager(String address, int port) {
		this.address = address;
		this.port = port;
	}

	public void init() throws UnknownHostException, IOException, LWJGLException {
		guiModel = new GUIModel();
		initNetwork();
		messageGenerator = new MessageGenerator(writeQueue, session);
		inputManager = new ClientInputManager(messageGenerator, guiModel);
		gl = new OpenGL();
		gl.initialize(800, 600, 2, 0);
		guiModel.addActionListener(this);
	}

	private void initNetwork() throws UnknownHostException, IOException {
		session = new Session(new Socket(address, port));
		readQueue = new JobQueue();
		writeQueue = new JobQueue();
		writeThread = new TCPWriteThread(writeQueue, false);
		clientJobThread = new ClientJobThread(readQueue, session, guiModel);
		readThread = new TCPReadThread(session, readQueue, false);
		clientJobThread.start();
		readThread.start();
		writeThread.start();
	}

	public void run() throws FileNotFoundException, IOException {
		while (gl.isRunning()) {
			draw();
			update();
		}
	}

	public void update() {
		int input = inputManager.update();

		Point mi = inputManager.getClickLocation();
		Point p = gl.update(gl.getDelta(), input, mi, 50, 50);
		if (p.x >= 0 && p.y >= 0) {
			System.out.println(p.x + " " + p.y);
			inputManager.tileClicked(p);
		}

		inputManager.resetClickLocation();
	}

	public void draw() throws FileNotFoundException, IOException {

		DrawingObject cd;
		ArrayList<DrawingObject> otd = new ArrayList<DrawingObject>();
		ArrayList<ArrayList<Tile>> board = guiModel.getTiles();
		if (board.size() > 0) {
			gl.initDraw(board.get(0).size(), board.size());

			for (int i = 0; i < board.size(); i++) {
				int maxJ = board.get(i).size();
				// if(i<board.size())
				// i++;
				// else
				// i = 0;
				for (int j = 0; j < maxJ; j++) {
					otd.add(board.get(i).get(j).draw(i, j));

				}
			}
			for (ClientHuman h : guiModel.getHumans()) {
				otd.add(h.draw());
			}

			for (ClientZombie z : guiModel.getZombies()) {
				otd.add(z.draw());
			}

			for (ClientHouse h : guiModel.getHouses()) {
				otd.add(h.draw());
			}

			if (guiModel.cycleChanged()) {
				double cycle = guiModel.getCycle();
				
				if (cycle > 0.5) {
					gl.setLight(50, -200, 50);
				} else {
					gl.setLight(50, 200, 50);
				}

				int tileSize = gl.getTileSize();
				int spaceBetweenTiles = gl.getSpaceBetweenTiles();
				int sizeX = board.size();
				int sizeZ = board.get(0).size();
				int radius = (tileSize + spaceBetweenTiles) * sizeZ / 2;
				double rad = Math.PI * 2 * cycle;

				int y = (int) (Math.sin(rad) * radius);
				int z = (int) (Math.cos(rad) * radius) + radius;
				int x = (tileSize + spaceBetweenTiles) * sizeX / 2;

				gl.setLight(x, y, z);

			}

			// gl.drawGUI();

			for (int i = 0; i < otd.size(); i++) {
				cd = otd.get(i);
				gl.convertAndDraw(cd.posX, cd.posY, cd.texturePos, cd.notTile);
			}

			gl.endDraw();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Runnable r = new Runnable() {
			@Override
			public void run() {
				try {
					ClientGameManager.this.draw();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		};
		EventQueue.invokeLater(r);
	}

}
