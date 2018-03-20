package objects;

import client.*;

public class Legislation extends Entity {

    private double speed, initialJumpSpeed, vy = 0;
    private boolean grounded;

    public Legislation(int x, int y, int w, int h) {
        super(x, y, w, h);
        speed = (int)(Math.random() * 10) + 10;
        initialJumpSpeed = (int)(Math.random() * 15) + 8;
        grounded = false;
    }

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

}
