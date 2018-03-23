package client;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by student on 3/22/18.
 */
public class GUIMod {

    private String innerText;
    private Panel parent;
    private JTextPane textbox;

    public GUIMod(Panel parent){
        innerText = "megaLUL";
        this.parent = parent;
        textbox = new JTextPane();
        textbox.setText(innerText);
        textbox.setEditable(false);
        textbox.setBackground(new Color(0,0,0,0));
        textbox.setBorder(BorderFactory.createEmptyBorder());
        textbox.setVisible(false);
        textbox.setFocusable(false);
        StyledDocument doc = textbox.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        parent.add(textbox);
    }

    public void draw(Graphics2D g2){
        try {
            BufferedImage bufferedImage = ImageIO.read(new File("res/blankScroll.png"));
            BufferedImage scroll = Panel.resizeImage(bufferedImage, parent.getWidth() / 3, parent.getHeight() * 2/3);
            int[] scrollCoords = {parent.getWidth() - scroll.getWidth() * 9/8, parent.getHeight() / 8};
            g2.drawImage(scroll, scrollCoords[0], scrollCoords[1], null);
            textbox.setBounds(scrollCoords[0] + 10, scrollCoords[1] + 70, scroll.getWidth() - 20, scroll.getHeight() - 100);
            textbox.setVisible(true);


        } catch (Exception e) {
            System.out.println("Image not found");
        }
    }

    public String getText() {
        return innerText;
    }

    public void setText(String innerText) {
        this.innerText = innerText;
        textbox.setText(innerText);
    }

    public void hide(){
        textbox.setVisible(false);
    }


}
