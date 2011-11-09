package com.xlend.util;

import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author Nick Mukhin
 */
public class Util {

    public static Image loadImage(String imageFile) {
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
