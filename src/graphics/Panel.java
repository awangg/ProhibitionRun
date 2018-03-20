package graphics;

import objects.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Panel extends JPanel {

    private Timer t;
    private Player p;
    private ArrayList<Entity> entities;

    public static int groundHeight = 150;
    private boolean[] keys = new boolean[512];

    public Panel() {
        entities = new ArrayList<>();
        p = new Player(25, (Main.HEIGHT - groundHeight) - 75, 75, 75);

        t = new Timer(1000/30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controls();
                if(!p.isGrounded()) {
                    p.move("null");
                }
                repaint();
            }
        });
        t.start();

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

    public void controls() {
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

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Ground
        g2.setColor(new Color(132, 63, 2));
        g2.fillRect(0, Main.HEIGHT - groundHeight, Main.WIDTH, groundHeight);

        // Player
        p.display(g2);
    }
}
