import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 * This program draws a series of "Scribbles". When the mouse is pressed
 * then dragged, a series of lines are drawn from the previous to the
 * the current mouse location.
 * 
 * @author Jim Teresco
 * @author (completed by)
 * @version Spring 2022
 */

public class AnimatedScribbles extends MouseAdapter implements Runnable {

	// a list of pairs of points to keep track of the start and end coordinates
	// where we need to draw lines
	private ArrayList<FallingLine> lines = new ArrayList<>();

	// previous mouse point for the next line to draw
	private Point lastMouse;

	private JPanel panel;

	// this method is called by the paintComponent method of
	// the anonymous extension of JPanel, to keep that method
	// from getting too long
	protected void redraw(Graphics g) {

		// draw all of the lines in the list
		for (FallingLine l : lines) {
			l.paint(g);
		}
	}

	/**
	 * The run method to set up the graphical user interface
	 */
	@Override
	public void run() {

		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame frame = new JFrame("AnimatedScribbles");
		frame.setPreferredSize(new Dimension(800, 800));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// JPanel with a paintComponent method
		panel = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {

				// first, we should call the paintComponent method we are
				// overriding in JPanel
				super.paintComponent(g);

				// redraw our graphics items
				redraw(g);
			}
		};
		frame.add(panel);
		panel.addMouseListener(this);
		panel.addMouseMotionListener(this);

		// display the window we've created
		frame.pack();
		frame.setVisible(true);
	}

	@Override
	public void mousePressed(MouseEvent e) {

		lastMouse = e.getPoint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {

		lines.add(new FallingLine(lastMouse, e.getPoint()));
		lastMouse = e.getPoint();
		panel.repaint();
	}

	public static void main(String args[]) {

		javax.swing.SwingUtilities.invokeLater(new AnimatedScribbles());
	}
}
