package objects;

import client.*;
import animation.*;
import client.Panel;
import tools.Point;
import java.awt.image.*;
import java.awt.*;
import javax.imageio.*;
import java.io.*;

public class Player extends Entity {

    private double speed = 10, vy = 0;
    private boolean grounded = true;
    private double jumpHeight = 20;

    private BufferedImage[] animationStates;
    private Animation animate;

    public Player(double x, double y, int w, int h) {
        super(x, y, w, h, null);

        animationStates = new BufferedImage[11];
        for(int i = 1; i <= 11; i++) {
            try {
                animationStates[i-1] = ImageIO.read(Main.buildImageFile("res/player/player" + i + ".png"));
            } catch (Exception e) {
                System.out.println("Sprite not found");
            }
        }
        animate = new Animation(animationStates, 2);
        animate.start();
    }

    public void animate() {
        animate.update();
    }

    public void move(String code) {
        setPosition(getPosition().x, getPosition().y + vy);
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

        if(code.equals("forward")) {
            setPosition(getPosition().x + speed, getPosition().y);
        }else if(code.equals("backward")) {
            setPosition(getPosition().x - speed, getPosition().y);
        }else if(code.equals("jump") && grounded) {
            if(vy >= 0) {
                vy -= jumpHeight;
                grounded = false;
            }
        }
    }

    public boolean isCollidingWith(Entity other) {
        for(Point p : getPoints("all")) {
            if(other.isColliding(p)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void display(Graphics2D g2) {
        /*
        g2.setColor(Color.GREEN);
        g2.fillRect((int)getPosition().x, (int)getPosition().y, getWidth(), getHeight());
        */
        g2.drawImage(animate.getSprite(), (int)getPosition().x, (int)getPosition().y, getWidth(), getHeight(), null);
    }

    public boolean isGrounded() {
        return grounded;
    }

    public double getSpeed() {
        return speed;
    }
}
