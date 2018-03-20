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

    public Point[] getPoints(String code) {
        if(code.equals("top")) {
            return new Point[]{new Point(x+2, y), new Point(x+w/2, y), new Point(x+w-2, y)};
        }else if(code.equals("bottom")) {
            return new Point[]{new Point(x+2, y+h), new Point(x+w/2, y+h), new Point(x+w-2, y+h)};
        }else if(code.equals("left")) {
            return new Point[]{new Point(x, y+2), new Point(x, y+h/2), new Point(x, y+h-2)};
        }else if(code.equals("right")) {
            return new Point[]{new Point(x+w, y+2), new Point(x+w, y+h/2), new Point(x+w, y+h-2)};
        }
        return null;
    }

    public boolean isColliding(Point[] other) {
        for(Point p : other) {
            if(p.x > x && p.x < x+w) {
                if(p.y > y && p.y < y+h) {
                    return true;
                }
            }
        }
        return false;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point getPosition() {
        return new Point(x, y);
    }

    public int getWidth() {
        return w;
    }

    public int getHeight() {
        return h;
    }

    // Default display method; child classes will override
    public void display(Graphics2D g2) {
        g2.setColor(Color.RED);
        g2.fillRect(x, y, w, h);
    }
}
