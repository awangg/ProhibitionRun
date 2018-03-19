package graphics;
import graphics.*;

import javax.swing.*;

public class Main {
    private static Panel p;
    public static void main(String[] args) {
        JFrame frame = new JFrame("Prohibition Run");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(800,600);

        p = new Panel();
        frame.add(p);
        p.setLayout(null);
        p.setFocusable(true);
        p.grabFocus();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(true);
    }
}