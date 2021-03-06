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
    private JScrollPane jsp;
    private Rectangle rect = null;

    public GUIMod(Panel parent, Rectangle optionalRect){
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

//        jsp = new JScrollPane(textbox, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
//        jsp.setBackground(new Color(0, 0, 0, 0));
//        jsp.setBorder(BorderFactory.createEmptyBorder());
//        jsp.setVisible(false);
//        jsp.createVerticalScrollBar();
//        jsp.createHorizontalScrollBar();
//        parent.add(jsp);
        parent.add(textbox);
        if(optionalRect != null){
            rect = optionalRect;
        }


    }

    public void draw(Graphics2D g2){
        try {
            BufferedImage bufferedImage = ImageIO.read(Main.buildImageFile("res/blankScroll.png"));
            BufferedImage scroll;
            int[] scrollCoords;
            if(rect == null) {
                scroll = Panel.resizeImage(bufferedImage, parent.getWidth() * 4 / 7, parent.getHeight() * 5 / 6);
                scrollCoords = new int[]{parent.getWidth() - scroll.getWidth() * 9 / 7, parent.getHeight() / 8};
            }
            else{
                scroll = Panel.resizeImage(bufferedImage, (int)rect.getWidth(), (int)rect.getHeight());
                scrollCoords = new int[]{rect.x, rect.y};
            }
            g2.drawImage(scroll, scrollCoords[0], scrollCoords[1], null);
            textbox.setBounds(scrollCoords[0] + 75, scrollCoords[1] + 80, scroll.getWidth() - 150, scroll.getHeight() - 100);
//            jsp.setBounds(textbox.getBounds());
//            jsp.setVisible(true);
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

    public void setRect(Rectangle rect){
        this.rect = rect;
    }
}
