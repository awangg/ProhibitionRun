package objects;

import animation.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

public class AlCapone extends Entity {

    private double speed;

    private BufferedImage[] animationStates;
    private Animation animate;

    public AlCapone(double x, double y, int w, int h) {
        super(x, y, w, h);
        speed = (Math.random() * 8) + 2;
        animationStates = new BufferedImage[6];
        for(int i = 1; i <= 6; i++) {
            try {
                animationStates[i-1] = ImageIO.read(new File("res/gangster/gangster" + i + ".png"));
            } catch (Exception e) {
                System.out.println("Sprite not found");
            }
        }
        animate = new Animation(animationStates, 2);
        animate.start();
    }

    @Override
    public void move() {
        animate.update();
        setPosition(getPosition().x - speed, getPosition().y);
    }

    @Override
    public void display(Graphics2D g2) {
        g2.drawImage(animate.getSprite(), (int)getPosition().x, (int)getPosition().y, getWidth(), getHeight(), null);
    }
}
