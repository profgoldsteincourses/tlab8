
// going to be lazy about imports in these examples...
import java.awt.*;
import javax.swing.*;

/**
 * The FallingGravityBall class is responsible for managing the life of
 * one ball that falls down the screen, stopping when it reaches the
 * bottom of the window.
 * 
 * We'll be adding some gravity effects.
 * 
 */
public class FallingGravityBall extends Thread {

    // ball size
    public static final int SIZE = 50;

    // delay time between frames of animation (ms)

    // we don't want to move too quickly, so a delay here of about 33
    // ms will make the loop in run go around about 30 times per
    // second, which is a good enough refresh rate to ensure that the
    // animation looks smooth to the human eye and brain
    public static final int DELAY_TIME = 33;

    // pixels to move each frame
    private double ySpeed;

    // latest location of the ball
    private Point upperLeft;

    // how far to fall?
    private int bottom;

    // have we reached the floor?
    private boolean done;

    // who do we live in so we can repaint?
    private JComponent container;

    /**
     * Construct a new FallingGravityBall object.
     * 
     * @param startTopCenter the initial point at which the top of the
     *                       ball should be drawn
     * @param container      the Swing component in which this ball is being
     *                       drawn to allow it to call that component's repaint
     *                       method
     */
    public FallingGravityBall(Point startTopCenter, JComponent container) {

        upperLeft = new Point(startTopCenter.x - SIZE / 2, startTopCenter.y);
        this.bottom = container.getHeight();
        this.container = container;
        ySpeed = 1;
    }

    /**
     * Draw the ball at its current location.
     * 
     * @param g the Graphics object on which the ball should be drawn
     */
    public void paint(Graphics g) {

        g.fillOval(upperLeft.x, upperLeft.y, SIZE, SIZE);
    }

    /**
     * This object's run method, which manages the life of the ball as it
     * moves down the screen.
     */
    @Override
    public void run() {

        // the run method is what runs in this object's thread for the
        // time it is "alive"

        // this FallingGravityBall's life as a thread will continue as long as this
        // ball is still located on the visible part of the screen
        while (upperLeft.y < bottom) {

            try {
                sleep(DELAY_TIME);
            } catch (InterruptedException e) {
            }

            // every 30 ms or so, we move the coordinates of the ball down
            // by a pixel
            upperLeft.translate(0, (int)ySpeed);
            ySpeed += 0.3;

            // if we want to see the ball move to its new position, we
            // need to schedule a paint event on this container
            container.repaint();
        }

        done = true;
    }

    /**
     * Return whether the ball has completed its fall to the bottom.
     * 
     * @return whether the ball has completed its fall to the bottom
     */
    public boolean done() {

        return done;
    }
}
