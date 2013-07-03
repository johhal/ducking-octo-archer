import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.SwingUtilities;

public class InputManager implements MouseListener {
	private boolean leftMouseClicked = false;
	private boolean rightMouseClicked = false;
	public Point clickLocation;

	public InputManager() {

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
		if(SwingUtilities.isRightMouseButton(arg0)){
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

}
