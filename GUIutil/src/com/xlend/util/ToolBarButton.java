package com.xlend.util;

import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author Nick Mukhin
 */
public class ToolBarButton extends JButton {

    public ToolBarButton(Icon icon) {
        super(icon);
        setVerticalTextPosition(BOTTOM);
        setHorizontalTextPosition(CENTER);
    }

    public ToolBarButton(String imageFile) {
        super(new ImageIcon(loadImage(imageFile)));
    }

    public ToolBarButton(String imageFile, String text) {
        super(text);
        setIcon(new ImageIcon(loadImage(imageFile)));
//        setText(text);
    }
    
    private static Image loadImage(String imageFile) {
        String fileName = "images/" + imageFile;
        Image image = null;
        if (new File(fileName).exists()) {
            try {
                ImageIcon ic = new javax.swing.ImageIcon(fileName, "");
                image = ic.getImage();
            } catch (Exception ex) {
            }
        } else {
            try {
                image = ImageIO.read(ToolBarButton.class.getResourceAsStream("/" + imageFile));
            } catch (Exception ie) {
            }
        }     
        return image;
    }
}
