package tests;
import sprites.*;

import biuoop.DrawSurface;
import biuoop.GUI;

/**
 * BallsTest1.
 */
public class BallsTest1 {
    /**
     * drawing balls.
     * @param args ignored.
     */
    public static void main(String[] args) {
        GUI gui = new GUI("Balls Test 1", 400, 400);
        DrawSurface d = gui.getDrawSurface();
        Ball b1 = new Ball(100, 100, 30, java.awt.Color.RED);
        Ball b2 = new Ball(100, 150, 10, java.awt.Color.BLUE);
        Ball b3 = new Ball(80, 249, 50, java.awt.Color.GREEN);
        b1.drawOn(d);
        b2.drawOn(d);
        b3.drawOn(d);
        gui.show(d);
    }
}