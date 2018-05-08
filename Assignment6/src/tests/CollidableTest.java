package tests;
import sprites.*;
import collisiondetections.*;
import gamerelated.GameEnvironment;
import biuoop.DrawSurface;
import biuoop.GUI;
import geometryprimitives.Point;
import geometryprimitives.Rectangle;
import characteristics.*;

import java.awt.*;

/**
 * author Dana Joffe.
 */
public class CollidableTest {
    /**
     * test for collision interface and implementation.
     * @param args ignored.
     */
    public static void main(String[] args) {
        GUI gui = new GUI("Blocks", 450, 300);
        biuoop.Sleeper sleeper = new biuoop.Sleeper();

        Collidable b1 = new Block(new Rectangle(new Point(50, 50), 100, 40));
        Block b2 = new Block(new Rectangle(new Point(300, 50), 100, 40));
        Block b3 = new Block(new Rectangle(new Point(150, 200), 150, 40));

        Block border1 =  new Block(new Rectangle(new Point(0, 0), 450, 10));
        border1.setColor(Color.DARK_GRAY);
        Block border2 =  new Block(new Rectangle(new Point(0, 290), 450, 10));
        border2.setColor(Color.GRAY);
        Block border3 =  new Block(new Rectangle(new Point(0, 0), 10, 300));
        Block border4 =  new Block(new Rectangle(new Point(440, 0), 10, 300));

        GameEnvironment game = new GameEnvironment();
        game.addCollidable(b1);
        game.addCollidable(b2);
        game.addCollidable(b3);
        game.addCollidable(border1);
        game.addCollidable(border2);
        game.addCollidable(border3);
        game.addCollidable(border4);

        Ball ball = new Ball(100, 200, 5, Color.BLACK);
        ball.setVelocity(Velocity.fromAngleAndSpeed(120, 1.1));
        ball.setGameEnvironment(game);

        while (true) {
            DrawSurface d = gui.getDrawSurface();

            d.setColor(Color.GREEN);
            d.fillRectangle(50, 50, 100, 40);
            d.setColor(Color.BLACK);
            d.drawRectangle(50, 50, 100, 40);

            d.setColor(Color.YELLOW);
            d.fillRectangle(300, 50, 100, 40);
            d.setColor(Color.BLACK);
            d.drawRectangle(300, 50, 100, 40);

            d.setColor(Color.CYAN);
            d.fillRectangle(150, 200, 150, 40);
            d.setColor(Color.BLACK);
            d.drawRectangle(150, 200, 150, 40);

            border1.drawOn(d);
            border2.drawOn(d);
            border3.drawOn(d);
            border4.drawOn(d);

            ball.moveOneStep();
            ball.drawOn(d);

            gui.show(d);
            sleeper.sleepFor(10);  // wait for 10 milliseconds.
        }
    }
}
