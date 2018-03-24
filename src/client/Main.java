package client;

import tools.ObjectNotation;

import javax.swing.*;
import java.net.URL;

public class Main {

    private static Panel p;
    public static final int WIDTH = 800, HEIGHT = 600;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Prohibition Run");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);

        p = new Panel();
        frame.add(p);
        p.setLayout(null);
        p.setFocusable(true);
        p.grabFocus();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(true);

    }

    public static URL buildImageFile(String file){
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        return classLoader.getResource(file);
    }
}