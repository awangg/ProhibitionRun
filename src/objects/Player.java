package objects;

import graphics.*;
import graphics.Panel;

import java.awt.*;

public class Player extends Entity {

    private int speed = 10, vy = 0;
    private boolean grounded = true;

    public Player(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    public void move(String code) {
        setPosition(getPosition().x, getPosition().y + vy);
        if(!grounded) {
            vy = Math.min(vy+1, 1);
        }

        if(vy >= 0) {
            if(getPosition().y >= Panel.groundHeight) {
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
                vy -= 13;
                grounded = false;
            }
        }
    }

    public boolean isGrounded() {
        return grounded;
    }
}
