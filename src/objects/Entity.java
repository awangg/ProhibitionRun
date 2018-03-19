package objects;

import java.awt.*;
import java.util.*;

public class Entity {
    private int x, y, w, h;
    public Entity(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public boolean isColliding() {
        return false;
    }

    public Point getPosition() {
        return new Point(x, y);
    }

    // Default display method; child classes will override
    public void display(Graphics2D g2) {
        g2.setColor(Color.RED);
        g2.fillRect(x, y, w, h);
    }
}
