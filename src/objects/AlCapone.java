package objects;

import java.awt.*;

public class AlCapone extends Entity {

    private double speed;

    public AlCapone(double x, double y, int w, int h) {
        super(x, y, w, h);
        speed = (Math.random() * 8) + 2;
    }

    @Override
    public void move() {
        setPosition(getPosition().x - speed, getPosition().y);
    }

    @Override
    public void display(Graphics2D g2) {
        g2.setColor(Color.YELLOW);
        g2.fillRect((int)getPosition().x, (int)getPosition().y, getWidth(), getHeight());
    }
}
