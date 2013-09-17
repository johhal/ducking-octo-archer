import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class InputManager implements MouseListener, ActionListener {
	private boolean leftMouseClicked = false;
	private boolean rightMouseClicked = false;

	public Point clickLocation = new Point(0,0);
	
	JPopupMenu popMenu;
	
	private boolean spawnZombieSelected = false;
	private boolean spawnHumanSelected = false;
	private boolean spawnHouseSelected = false;
	
	private boolean prevLeftBtn;
	private boolean prevRightBtn;

	public void initilize() throws LWJGLException {
		
	}
	
	public void resetClickLocation()
	{
		clickLocation = new Point(0,0);
		leftMouseClicked = false;
	}
	
	public boolean spawnZombieSelected() {
		return spawnZombieSelected;
	}

	public boolean spawnHumanSelected() {
		return spawnHumanSelected;
	}

	public boolean isLeftMouseClicked() {
		return leftMouseClicked;
	}

	public boolean isRightMouseClicked() {
		return rightMouseClicked;
	}
	

	public Point getClickLocation() {
		leftMouseClicked = false;
		rightMouseClicked = false;
		return clickLocation;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (SwingUtilities.isLeftMouseButton(arg0)) {
			leftMouseClicked = true;
			clickLocation = new Point(arg0.getX(), arg0.getY());
		}
		if (SwingUtilities.isRightMouseButton(arg0)) {
			System.out.println("INPUT MANAGER MOUSE CLICKED");
			rightMouseClicked = true;
			clickLocation = new Point(arg0.getX(), arg0.getY());
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command.equals("zombie")) {
			spawnZombieSelected = true;
			spawnHumanSelected = false;
			spawnHouseSelected = false;
		}
		if (command.equals("human")) {
			spawnZombieSelected = false;
			spawnHumanSelected = true;
			spawnHouseSelected = false;
		}
		if (command.equals("house")) {
			spawnZombieSelected = false;
			spawnHumanSelected = false;
			spawnHouseSelected = true;
		}
	}
	
	private int mathExp(int base, int exp)
	{
		int tmp = base;
		for(int i = 0; i < exp; i++)
			tmp *= base;
		if (exp == 0)
			tmp = 1;
		return tmp;
	}
	
	private int checkKeyboard()
	{
		int tmp = 0;
		if(Keyboard.isKeyDown(Keyboard.KEY_A))
		{
			tmp += 1*mathExp(10, 0);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D))
		{
			tmp += 1*mathExp(10, 1);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_W))
		{
			tmp += 1*mathExp(10, 2);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_S))
		{
			tmp += 1*mathExp(10, 3);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_Q))
		{
			tmp += 1*mathExp(10, 4);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_E))
		{
			tmp += 1*mathExp(10, 5);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_Z))
		{
			tmp += 1*mathExp(10, 6);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_C))
		{
			tmp += 1*mathExp(10, 7);
		}
		return tmp;
	}
	
	public int update()
	{		
		if(Mouse.isButtonDown(0)){
			prevLeftBtn=true;
		}
		
		if(Mouse.isButtonDown(1)){
			prevRightBtn=true;
		}
		
		if(!Mouse.isButtonDown(0) && prevLeftBtn == true){
			clickLocation = new Point(Mouse.getX(), Mouse.getY());
			prevLeftBtn = false;
			leftMouseClicked = true;
		}
		
		if(!Mouse.isButtonDown(1) && prevRightBtn == true){
			clickLocation = new Point(Mouse.getX(), Mouse.getY());
			prevRightBtn = false;
		}
		
		return checkKeyboard();
	}
}
