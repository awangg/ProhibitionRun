package objects;

import java.io.*;
import java.awt.*;
import javax.imageio.*;
import java.awt.image.*;

public class Keg extends Entity {

    private double speed;
    private BufferedImage sprite;

    public Keg(double x, double y, int w, int h) {
        super(x, y, w, h, null);
        speed = 15;
        try {
            sprite = ImageIO.read(new File("res/keg.png"));
        } catch (Exception e) {
            System.out.println("Sprite not found");
        }
    }

    public void move() {
        setPosition(getPosition().x - speed, getPosition().y);
    }

    public void display(Graphics2D g2) {
        g2.drawImage(sprite, (int)getPosition().x, (int)getPosition().y, getWidth(), getHeight(), null);
    }

}
