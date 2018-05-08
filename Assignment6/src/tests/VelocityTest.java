package tests;

import characteristics.Velocity;

/**
 * Created by djoff on 09/06/2017.
 */
public class VelocityTest {
    public static void main(String[] args) {
        Velocity v = new Velocity(3, 3);
        //v = Velocity.fromAngleAndSpeed(135, 4.24);
        v = new Velocity(3, -5.2);
        //v = Velocity.fromAngleAndSpeed(135, 4.24);
        System.out.println("size: " + v.getVectorSize() + ", getX: " + v.dx() + ", getY: " + v.dy() + ", angle: " + v.getAngle());


        v = new Velocity(5.2, 3);
        System.out.println("size: " + v.getVectorSize() + ", getX: " + v.dx() + ", getY: " + v.dy() + ", angle: " + v.getAngle());

        v = new Velocity(-3, 5.2);
        System.out.println("size: " + v.getVectorSize() + ", getX: " + v.dx() + ", getY: " + v.dy() + ", angle: " + v.getAngle());

        v = new Velocity(-5.2, -3);
        System.out.println("size: " + v.getVectorSize() + ", getX: " + v.dx() + ", getY: " + v.dy() + ", angle: " + v.getAngle());

        v = new Velocity(-5.2, 0);
        System.out.println("size: " + v.getVectorSize() + ", getX: " + v.dx() + ", getY: " + v.dy() + ", angle: " + v.getAngle());

        v = new Velocity(0, -6);
        System.out.println("size: " + v.getVectorSize() + ", getX: " + v.dx() + ", getY: " + v.dy() + ", angle: " + v.getAngle());

    }
}
