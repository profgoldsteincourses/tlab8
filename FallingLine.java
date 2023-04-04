import java.awt.*;
/**
 * Starter for a Falling Line.  CSIS-225 Lab 8.
 * 
 * @author Jim Teresco (starter code)
 * @version Spring 2022
 */
public class FallingLine extends Thread {

    private Point p1, p2;

    public FallingLine(Point p1, Point p2) {

        this.p1 = p1;
        this.p2 = p2;
    }

    public void paint(Graphics g) {

        g.drawLine(p1.x, p1.y, p2.x, p2.y);
    }

}