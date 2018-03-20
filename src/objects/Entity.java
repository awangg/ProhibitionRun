package objects;

import java.awt.*;
import tools.Point;

public class Entity {
    private double x, y;
    private int w, h;
    public Entity(double x, double y, int w, int h) {
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
        }else if(code.equals("all")) {
            return new Point[]{new Point(x+2, y), new Point(x+w/2, y), new Point(x+w-2, y),
                    new Point(x+2, y+h), new Point(x+w/2, y+h), new Point(x+w-2, y+h),
                    new Point(x, y+2), new Point(x, y+h/2), new Point(x, y+h-2),
                    new Point(x+w, y+2), new Point(x+w, y+h/2), new Point(x+w, y+h-2)};
        }
        return null;
    }

    public boolean isColliding(Point p) {
        if(p.x > x && p.x < x+w) {
            if(p.y > y && p.y < y+h) {
                return true;
            }
        }
        return false;
    }

    public void setPosition(double x, double y) {
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
        g2.fillRect((int)x, (int)y, w, h);
    }
}
