
// going to be lazy about imports in these examples...
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/**
 * A program to demonstrate a simple animation of a ball that falls to
 * the ground after being released from somewhere.
 * 
 * IMPORTANT NOTE: Thread safety is not ensured! We will get to that
 * soon. Concurrency adds complications that we have not yet seen how
 * to handle!
 * 
 * @author Jim Teresco
 * @version Spring 2022
 */

public class FallingGravityBallDemo extends MouseAdapter implements Runnable {

	// list of FallingGravityBall objects currently on the screen
	private java.util.List<FallingGravityBall> list;

	private JPanel panel;

	/**
	 * The run method to set up the graphical user interface
	 */
	@Override
	public void run() {

		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame frame = new JFrame("FallingGravityBall");
		frame.setPreferredSize(new Dimension(500, 500));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// JPanel with a paintComponent method
		panel = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {

				// first, we should call the paintComponent method we are
				// overriding in JPanel
				super.paintComponent(g);

				// redraw each ball at its current position,
				// remove the ones that are done along the way
				int i = 0;
				while (i < list.size()) {
					FallingGravityBall b = list.get(i);
					if (b.done()) {
						list.remove(i);
					} else {
						b.paint(g);
						i++;
					}
				}
			}
		};
		frame.add(panel);
		panel.addMouseListener(this);

		// construct the list
		list = new ArrayList<FallingGravityBall>();

		// display the window we've created
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Mouse press event handler to create a new FallingGravityBall with its top
	 * centered at the press point.
	 * 
	 * @param e mouse event info
	 */
	@Override
	public void mousePressed(MouseEvent e) {

		FallingGravityBall newBall = new FallingGravityBall(e.getPoint(), panel);
		list.add(newBall);

		// calling start on the object that extends Thread results in
		// its run method being called once the operating system and
		// JVM have set up the thread
		newBall.start();
		panel.repaint();
	}

	public static void main(String args[]) {

		// The main method is responsible for creating a thread (more
		// about those later) that will construct and show the graphical
		// user interface.
		javax.swing.SwingUtilities.invokeLater(new FallingGravityBallDemo());
	}
}
