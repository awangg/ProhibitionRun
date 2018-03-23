package objects;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;

import client.*;

public class Backdrop extends Entity {

    private double speed = 20, distanceBetween;
    private BufferedImage streetlight, building;
    private int w2, h2;
    private double y2;

    public Backdrop(double x, double y, double y2, int w, int h, int w2, int h2) {
        super(x, y, w, h, null);

        try {
            streetlight = ImageIO.read(new File("res/streetlight.png"));
            building = ImageIO.read(new File("res/building.png"));
        } catch (Exception e) {
            System.out.println("Image not found");
        }

        distanceBetween = (Math.random() * 300) + 50;
        this.y2 = y2;
        this.w2 = w2;
        this.h2 = h2;
    }

    @Override
    public void move() {
        setPosition(getPosition().x - speed, getPosition().y);
        if(isOffScreen()) {
            recalculateDistanceBetween();
            setPosition(Main.WIDTH + 25, getPosition().y);
        }
    }

    @Override
    public boolean isOffScreen() {
        if(getPosition().x + distanceBetween + getWidth() + w2 + h2 <= 0) {
            return true;
        }
        return false;
    }

    public void recalculateDistanceBetween() {
        distanceBetween = (Math.random() * 300) + 50;
    }

    @Override
    public void display(Graphics2D g2) {
        /*
        g2.setColor(Color.GRAY);
        g2.fillRect((int)getPosition().x, (int)getPosition().y, getWidth(), getHeight());
        */

        g2.drawImage(streetlight, (int)getPosition().x, (int)getPosition().y, getWidth(), getHeight(), null);
        g2.drawImage(building, (int)(getPosition().x + distanceBetween), (int)y2, w2, h2, null);
    }
}
