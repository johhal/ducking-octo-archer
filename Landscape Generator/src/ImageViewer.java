import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ImageViewer {

	JPanel gui;
	/** Displays the image. */
	JLabel imageCanvas;
	JPopupMenu popMenu;
	JMenuItem zombieItem;
	JMenuItem humanItem;

	/** Set the image as icon of the image canvas (display it). */
	public void setImage(Image image) {
		imageCanvas.setIcon(new ImageIcon(image));
	}

	public void setMouseListener(MouseListener click) {
		imageCanvas.addMouseListener(click);
	}

	public void setActionListener(ActionListener listener) {
		zombieItem.addActionListener(listener);
		humanItem.addActionListener(listener);
	}

	public void initComponents() {
		if (gui == null) {
			gui = new JPanel(new BorderLayout());
			gui.setBorder(new EmptyBorder(5, 5, 5, 5));
			imageCanvas = new JLabel();

			popMenu = new JPopupMenu();
			zombieItem = new JMenuItem("Zombies");
			humanItem = new JMenuItem("Human");
			popMenu.add(zombieItem);
			popMenu.add(humanItem);

			JPanel imageCenter = new JPanel(new GridBagLayout());
			imageCenter.add(imageCanvas);
			JScrollPane imageScroll = new JScrollPane(imageCenter);
			imageScroll.setPreferredSize(new Dimension(510, 510));
			gui.add(imageScroll, BorderLayout.CENTER);

			setMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent arg0) {
					if (SwingUtilities.isRightMouseButton(arg0)) {
						popMenu.show(arg0.getComponent(), arg0.getX(),
								arg0.getY());
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

			});
		}
	}

	public Container getGui() {
		initComponents();
		return gui;
	}
}