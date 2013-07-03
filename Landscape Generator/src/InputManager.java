import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

public class InputManager implements MouseListener, ActionListener {
	private boolean leftMouseClicked = false;
	private boolean rightMouseClicked = false;
	public Point clickLocation;
	JPopupMenu popMenu;
	private boolean spawnZombieSelected = false;
	private boolean spawnHumanSelected = false;

	public void Initilize() {
		popMenu = new JPopupMenu();
		JMenuItem zombieItem = new JMenuItem("Zombies");
		JMenuItem humanItem = new JMenuItem("Human");
		zombieItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				spawnZombieSelected = true;
				spawnHumanSelected = false;
			}
		});
		humanItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				spawnZombieSelected = false;
				spawnHumanSelected = true;
			}
		});

		popMenu.add(zombieItem);
		popMenu.add(humanItem);
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
			System.out.println("Left mouse clicked");
		}
		if (SwingUtilities.isRightMouseButton(arg0)) {
			rightMouseClicked = true;
			clickLocation = new Point(arg0.getX(), arg0.getY());
			System.out.println("Right mouse clicked");
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
		}
		if (command.equals("human")) {
			spawnZombieSelected = false;
			spawnHumanSelected = true;
		}

	}

}
