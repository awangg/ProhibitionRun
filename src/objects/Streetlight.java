package objects;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;

import client.*;

public class Streetlight extends Entity {

    private double speed = 20;
    private BufferedImage sprite;

    public Streetlight(double x, double y, int w, int h) {
        super(x, y, w, h);
        try {
            sprite = ImageIO.read(new File("res/streetlight.png"));
        } catch (Exception e) {
            System.out.println("Image not found");
        }
    }

    public void move() {
        setPosition(getPosition().x - speed, getPosition().y);
        if(isOffScreen()) {
            setPosition(Main.WIDTH + 25, getPosition().y);
        }
    }

    public void display(Graphics2D g2) {
        /*
        g2.setColor(Color.GRAY);
        g2.fillRect((int)getPosition().x, (int)getPosition().y, getWidth(), getHeight());
        */

        g2.drawImage(sprite, (int)getPosition().x, (int)getPosition().y, getWidth(), getHeight(), null);
    }
}
