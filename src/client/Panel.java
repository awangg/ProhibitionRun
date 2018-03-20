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

    private boolean drawOverlay = false, paused = false, caponeSpawned = false;
    private long caponeSpawnTime, currentTime, startTime;

    private int score;

    public Panel() {
        entities = new ArrayList<>();
        entities.add(new Backdrop(Main.WIDTH + 25, Main.HEIGHT - groundHeight - 200, Main.HEIGHT - groundHeight - 600, 75, 200, 400, 600));

        p = new Player(25, (Main.HEIGHT - groundHeight) - 120, 70, 120);

//        caponeSpawnTime = (long)(Math.random() * 20000) + 20000;
        caponeSpawnTime = (long)(Math.random() * 5000) + 1000;
        startTime = System.nanoTime()/1000000;

        spawnTimer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                entities.add(new Legislation(Main.WIDTH + 25, Main.HEIGHT - groundHeight - 60, 60, 60));

                if(Math.random() >= .5) {
                    entities.add(new Keg(Main.WIDTH + 50, Main.HEIGHT - groundHeight - 55, 60, 52));
                }

                if(currentTime - startTime >= caponeSpawnTime && !caponeSpawned) {
                    entities.add(new AlCapone(Main.WIDTH + 25, Main.HEIGHT - groundHeight - 120, 70, 120));
                    caponeSpawned = true;
                }
            }
        });
        spawnTimer.start();

        gameTimer = new Timer(1000/30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!paused) {
                    currentTime = System.nanoTime()/1000000;
                    playerControls();
                    if (!p.isGrounded()) {
                        p.move("null");
                    }
                    p.animate();

                    enemyControls();
                    if (checkCollisions()) {
                        // System.out.println("Collision Detected");
                        drawOverlay = true;
                        pauseTimers();
                    }
                }else {
                    checkResume();
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
            if(p.getPosition().x + p.getWidth() >= Main.WIDTH) {
                p.setPosition(Main.WIDTH - p.getWidth(), p.getPosition().y);
            }
        }
        if(keys[KeyEvent.VK_A]) {
            p.move("backward");
            if(p.getPosition().x <= 0) {
                p.setPosition(0, p.getPosition().y);
            }
        }
        if(keys[KeyEvent.VK_ESCAPE]) {
            System.exit(0);
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
        paused = true;
        spawnTimer.stop();
    }

    public void checkResume() {
        if(keys[KeyEvent.VK_SPACE]) {
            paused = false;
            spawnTimer.start();
            keys[KeyEvent.VK_SPACE] = false;
        }
    }

    public boolean checkCollisions() {
        for(int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            if(p.isCollidingWith(e) && !(e instanceof Backdrop)) {
                if (e instanceof Keg) {
                    score += 50;
                    entities.remove(e);
                    i--;
                    return false;
                }else {
                    score -= 10;
                    entities.remove(e);
                    i--;
                    return true;
                }
            }
        }
        return false;
    }

    public String determineTime(long givenTime) {
        long totalSeconds = givenTime / 1000;
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;
        if(seconds < 10) {
            return minutes + ":0" + seconds;
        }else {
            return minutes + ":" + seconds;
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        //Background
        g2.setColor(new Color(0, 0, 102));
        g2.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);

        // Ground
        g2.setColor(new Color(132, 63, 2));
        g2.fillRect(0, Main.HEIGHT - groundHeight, Main.WIDTH, groundHeight);

        // Entities
        for(Entity e : entities) {
            e.display(g2);
        }

        //Scoreboard
        g2.setColor(Color.WHITE);
        String scoreText = "Score: " + score;
        g2.drawString(scoreText, Main.WIDTH - 10 - g2.getFontMetrics().stringWidth(scoreText), 25);
        String timeText = "Time Elapsed: " + determineTime(currentTime - startTime);
        g2.drawString(timeText, Main.WIDTH - 10 - g2.getFontMetrics().stringWidth(timeText), 50);

        // Player
        p.display(g2);
    }
}
