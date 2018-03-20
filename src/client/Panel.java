package client;

import objects.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Panel extends JPanel {

    private Timer gameTimer, spawnTimer;
    private Player p;
    private ArrayList<Entity> entities;

    public static int groundHeight = 150;
    private boolean[] keys = new boolean[512];

    private boolean drawOverlay = false;

    public Panel() {
        entities = new ArrayList<>();
        p = new Player(25, (Main.HEIGHT - groundHeight) - 75, 75, 75);

        spawnTimer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                entities.add(new Legislation(Main.WIDTH + 25, Main.HEIGHT - groundHeight - 50, 50, 50));
            }
        });
        spawnTimer.start();

        gameTimer = new Timer(1000/30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playerControls();
                if(!p.isGrounded()) {
                    p.move("null");
                }

                enemyControls();
                if(checkCollisions()) {
                    // System.out.println("Collision Detected");
                    pauseTimers();
                    drawOverlay = true;
                }

                repaint();
            }
        });
        gameTimer.start();

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                keys[e.getKeyCode()] = true;
            }

            @Override
            public void keyReleased(KeyEvent e) {
                keys[e.getKeyCode()] = false;
            }
        });
    }

    public void playerControls() {
        if(keys[KeyEvent.VK_W] || keys[KeyEvent.VK_UP]) {
            p.move("jump");
        }
        if(keys[KeyEvent.VK_D]) {
            p.move("forward");
        }
        if(keys[KeyEvent.VK_A]) {
            p.move("backward");
        }
    }

    public void enemyControls() {
        for(int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            e.move();
            if(e.isOffScreen()) {
                entities.remove(e);
                i--;
            }
        }
    }

    public void pauseTimers() {
        gameTimer.stop();
        spawnTimer.stop();
    }

    public boolean checkCollisions() {
        for(Entity e : entities) {
            if(p.isCollidingWith((e))) {
                return true;
            }
        }
        return false;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Ground
        g2.setColor(new Color(132, 63, 2));
        g2.fillRect(0, Main.HEIGHT - groundHeight, Main.WIDTH, groundHeight);

        // Player
        p.display(g2);

        // Entities
        for(Entity e : entities) {
            e.display(g2);
        }
    }
}
