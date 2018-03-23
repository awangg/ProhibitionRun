package client;

import objects.*;
import tools.ObjectNotation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class Panel extends JPanel {

    private double state = 1;
    /*
    0 - loading credits; 1 - start screen; 1.5 - starting animation; 2 - playing; 3 - game over; 4 - victory;
    */
    boolean gameOver = false;

    private Timer gameTimer, spawnTimer;
    private Player p;
    private ArrayList<Entity> entities;

    public static int groundHeight = 150;
    private boolean[] keys = new boolean[512];

    private boolean drawOverlay = false, paused = false, caponeSpawned = false;
    private int caponeSpawnTime, currentTime, startTime, score, delay, dbKey;
    private GUIMod gui;
    private ObjectNotation db, uniqueDB;

    private BufferedImage communismLeft, communismRight, studio;
    private int initLeft = 100, initRight = 400, frameSpeed = 2, opacity = 0;

    private boolean hit21st = false;

    public Panel() {
        entities = new ArrayList<>();
        entities.add(new Backdrop(Main.WIDTH + 25, Main.HEIGHT - groundHeight - 200, Main.HEIGHT - groundHeight - 600, 75, 200, 400, 600));
        delay = 2000;

        gui = new GUIMod(this);
        dbKey = 0;
        db = new ObjectNotation("db.txt");
        uniqueDB = new ObjectNotation("uniqueInfoDB.txt");
        initLoadingImages();

        p = new Player(Main.WIDTH/2 - 35, 100, 70, 120);

        caponeSpawnTime = (int)((Math.random() * 20000) + 20000);
        startTime = (int)(System.nanoTime()/1000000);

        spawnTimer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(state == 2) {

                    if(currentTime - startTime >= 60000 && Math.random() < .1) {
                        entities.add(new Legislation(Main.WIDTH + 25, Main.HEIGHT - groundHeight - 60, 60, 60, true, db.keys()[db.keys().length - 1]));
                    }else {
                        entities.add(new Legislation(Main.WIDTH + 25, Main.HEIGHT - groundHeight - 60, 60, 60, false, db.keys()[(int)(Math.random() * (db.keys().length-1))]));
                    }

                    if (Math.random() >= .25) {
                        entities.add(new Keg(Main.WIDTH + 50, Main.HEIGHT - groundHeight - 55, 60, 52));
                    }


                    if(currentTime - startTime >= caponeSpawnTime && !caponeSpawned) {
                        entities.add(new AlCapone(Main.WIDTH + 25, Main.HEIGHT - groundHeight - 120, 70, 120, "Al Capone"));
                        caponeSpawned = true;
                    }

                }
            }
        });
        spawnTimer.start();

        gameTimer = new Timer(1000/30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(state == 1) {
                    p.animate();
                }else if(state == 2 && !paused) {
                    currentTime = (int)(System.nanoTime()/1000000);
                    playerControls();
                    if (!p.isGrounded()) {
                        p.move("null");
                    }
                    p.animate();

                    enemyControls();

                    setSpawnDelay(currentTime - startTime);

                    if (checkCollisions()) {
                        // System.out.println("Collision Detected");
                        drawOverlay = true;
                        pauseTimers();
                    }

                    if(score <= -50) {
                        gameOver = true;
                    }
                }
                if(state == 2 && paused) {
                    checkResume();
                }
                if(state == 2 && gameOver) {
                    resetGame();
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

    public void resetGame() {
        if(keys[KeyEvent.VK_R]) {
            score = 0;

            entities.clear();
            entities.add(new Backdrop(Main.WIDTH + 25, Main.HEIGHT - groundHeight - 200, Main.HEIGHT - groundHeight - 600, 75, 200, 400, 600));
            delay = 2000;

            p = new Player(25, (Main.HEIGHT - groundHeight) - 120, 70, 120);

            caponeSpawnTime = (int) ((Math.random() * 20000) + 20000);
            startTime = (int) (System.nanoTime() / 1000000);

            spawnTimer.start();

            paused = false;
            gui.hide();
            drawOverlay = false;

            gameOver = false;
            keys[KeyEvent.VK_R] = false;
        }
    }

    public void initLoadingImages() {
        try {
            communismLeft = ImageIO.read(new File("res/communismLeft.png"));
            communismRight = ImageIO.read(new File("res/communismRight.png"));
            studio = ImageIO.read(new File("res/studio.png"));
        } catch (Exception e) {
            System.out.println("Image not found");
        }
    }

    public void playerControls() {
        if(keys[KeyEvent.VK_W] && p.isGrounded()) {
            p.move("jump");
        }
        if(keys[KeyEvent.VK_D] && p.isGrounded()) {
            p.move("forward");
            if(p.getPosition().x + p.getWidth() >= Main.WIDTH) {
                p.setPosition(Main.WIDTH - p.getWidth(), p.getPosition().y);
            }
        }
        if(keys[KeyEvent.VK_A] && p.isGrounded()) {
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
            spawnTimer.start();
            drawOverlay = false;
            gui.hide();

            if (hit21st) {
                state = 4;
            }
            paused = false;
            keys[KeyEvent.VK_SPACE] = false;
        }
    }

    public boolean checkCollisions() {
        for(int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            if(p.isCollidingWith(e) && !(e instanceof Backdrop)) {
                if (e instanceof Keg) {
                    score += 20;
                    entities.remove(e);
                    i--;
                    return false;
                }else {
                    score -= 50;
                    String def;
                    if(e.isUniqueInfo())
                        def = uniqueDB.get(e.getId());
                    else{
                        def = db.get(e.getId());
                        Legislation l = (Legislation)e;
                        if(l.getIs21st()) {
                            hit21st = true;
                        }
                    }
                    gui.setText(e.getId() + "\n\n" + def);
                    //System.out.println("dbKey is now: " + dbKey + " and db is " + Arrays.toString(db.keys()));
                    entities.remove(e);
                    drawOverlay = true;
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

    public void setSpawnDelay(int time) {
        int multiplier = (time/1000) / 30;
        if(spawnTimer.getDelay() > 500) {
            spawnTimer.setDelay(delay - (150 * multiplier));
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        Font def = g2.getFont();

        if(state == 0) {
            g2.drawImage(studio, 0, 0, Main.WIDTH, Main.HEIGHT, null);
            g2.drawImage(communismLeft, initLeft, 0, null);
            g2.drawImage(communismRight, initRight, 0, null);

            if (initLeft > -100) {
                initLeft -= frameSpeed;
            }
            if (initRight + communismRight.getWidth() < Main.WIDTH + 100) {
                initRight += frameSpeed;
            }

            if ((initLeft <= -100 || initRight >= Main.WIDTH + 100) && opacity < 252) {
                opacity += 4;
            }
            g2.setColor(new Color(0, 0, 0, opacity));
            g2.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);

            if (opacity >= 252) {
                state = 1;
            }
        } else if(state == 1) {
            g2.setColor(Color.BLACK);
            g2.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);

            g2.setFont(new Font("Monospaced", Font.BOLD, 48));
            g2.setColor(Color.WHITE);
            g2.drawString("PROHIBITION RUN", Main.WIDTH/2 - g2.getFontMetrics().stringWidth("PROHIBITION RUN")/2, 75);

            p.display(g2);

        } else if(state == 2) {
            //Background
            g2.setColor(new Color(0, 0, 102));
            g2.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);

            // Ground
            g2.setColor(new Color(132, 63, 2));
            g2.fillRect(0, Main.HEIGHT - groundHeight, Main.WIDTH, groundHeight);

            // Entities
            for (Entity e : entities) {
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

            if (drawOverlay) {
                gui.draw(g2);
            }

            if(gameOver) {
                g2.setColor(new Color(0, 0, 0, 150));
                g2.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);

                g2.setColor(Color.WHITE);
                g2.setFont(new Font(def.getName(), def.getStyle(), 36));
                String death = "GAME OVER";
                g2.drawString(death, Main.WIDTH/2 - (g2.getFontMetrics().stringWidth(death)/2), Main.HEIGHT/2 - 10);

                pauseTimers();
            }
        } else if(state == 4) {
            g2.setColor(Color.GREEN);
            g2.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
        }
    }

    public static BufferedImage resizeImage(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }
}
