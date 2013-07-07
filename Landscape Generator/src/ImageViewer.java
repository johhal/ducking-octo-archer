import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ImageViewer {

	JPanel gui;
	/** Displays the image. */
	JLabel imageCanvas;
	JPopupMenu popMenu;
	JMenuItem zombieItem;
	JMenuItem humanItem;
	JToggleButton zombieButton;
	JToggleButton humanButton;
	JToggleButton houseButton;
	
	ButtonGroup group;

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
		
		zombieButton.addActionListener(listener);
	    humanButton.addActionListener(listener);
	    houseButton.addActionListener(listener);
	}

	public void initComponents() {
		if (gui == null) {
			gui = new JPanel(new BorderLayout());
			gui.setBorder(new EmptyBorder(5, 5, 5, 5));
			imageCanvas = new JLabel();

			initPopupMenu();
			
			initButtons();
			
			GridBagLayout gbl = new GridBagLayout();
			JPanel buttonPanel = new JPanel(new GridBagLayout());
			GridBagConstraints c = gbl.getConstraints(buttonPanel);
			c.fill = GridBagConstraints.BOTH;
			c.gridx=0;
			c.gridy=0;
			buttonPanel.add(zombieButton,c);
			c.gridy++;
			buttonPanel.add(humanButton,c);
			c.gridy++;
			buttonPanel.add(houseButton,c);

			JPanel imageCenter = new JPanel(new GridBagLayout());
			imageCenter.add(imageCanvas);
			JScrollPane imageScroll = new JScrollPane(imageCenter);
			imageScroll.setPreferredSize(new Dimension(510, 510));
			gui.add(imageScroll, BorderLayout.CENTER);
			gui.add(buttonPanel, BorderLayout.WEST);

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

	private void initPopupMenu() {
		//Initsierar popupp-menyerna
		popMenu = new JPopupMenu();
		zombieItem = new JMenuItem("Zombies");
		zombieItem.setActionCommand("zombie");
		humanItem = new JMenuItem("Human");
		humanItem.setActionCommand("human");
		popMenu.add(zombieItem);
		popMenu.add(humanItem);
	}
	
	private void initButtons(){
		//Ladda in bilder och initsiera knappar.
		
		zombieButton = new JToggleButton("Zombie");
	    zombieButton.setMnemonic(KeyEvent.VK_B);
	    zombieButton.setActionCommand("zombie");

	    humanButton = new JToggleButton("Human");
	    humanButton.setMnemonic(KeyEvent.VK_C);
	    humanButton.setActionCommand("human");
	    humanButton.setSelected(true);

	    houseButton = new JToggleButton("House");
	    houseButton.setMnemonic(KeyEvent.VK_D);
	    houseButton.setActionCommand("house");

	    group = new ButtonGroup();
	    group.add(zombieButton);
	    group.add(humanButton);
	    group.add(houseButton);
	    humanButton.setIcon(new ImageIcon("resources/human_button_logo.png"));
	    humanButton.setVerticalTextPosition(SwingConstants.BOTTOM);
	    humanButton.setHorizontalTextPosition(SwingConstants.CENTER);
	    zombieButton.setIcon(new ImageIcon("resources/zombie_button_logo.png"));
	    zombieButton.setVerticalTextPosition(SwingConstants.BOTTOM);
	    zombieButton.setHorizontalTextPosition(SwingConstants.CENTER);
	    houseButton.setIcon(new ImageIcon("resources/house_button_logo.png"));
	    houseButton.setVerticalTextPosition(SwingConstants.BOTTOM);
	    houseButton.setHorizontalTextPosition(SwingConstants.CENTER);

	}

	public Container getGui() {
		
		initComponents();
		return gui;
	}
}