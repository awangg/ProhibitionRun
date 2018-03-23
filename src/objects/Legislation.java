package objects;

import client.Panel;
import client.Main;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

public class Legislation extends Entity {

    private double speed, initialJumpSpeed, vy = 0;
    private boolean grounded;
    private BufferedImage sprite;

    public Legislation(int x, int y, int w, int h, String id) {
        super(x, y, w, h, id);
        speed = (int)(Math.random() * 10) + 10;
        initialJumpSpeed = (int)(Math.random() * 15) + 8;
        grounded = false;
        try {
            sprite = ImageIO.read(new File("res/legislation.png"));
        } catch (Exception e) {
            System.out.println("Image not found");
        }
    }

    @Override
    public void move() {
        setPosition(getPosition().x - speed, getPosition().y + vy);
        if(!grounded) {
            vy = Math.min(vy + 1, 20);
        }

        if(vy >= 0) {
            if(getPosition().y + getHeight() >= Main.HEIGHT - Panel.groundHeight) {
                vy = 0;
                grounded = true;
                setPosition(getPosition().x, Main.HEIGHT - Panel.groundHeight - getHeight());
            }
        }

        if(grounded) {
            if(vy >= 0) {
                vy -= initialJumpSpeed;
                grounded = false;
            }
        }

    }

    public void display(Graphics2D g2) {
        g2.drawImage(sprite, (int)getPosition().x, (int)getPosition().y, getWidth(), getHeight(), null);
    }

}
