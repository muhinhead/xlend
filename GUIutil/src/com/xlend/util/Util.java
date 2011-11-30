package com.xlend.util;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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

    public static byte[] readFile(String fileName) {
        byte[] b = null;
        try {
            File file = new File(fileName);
            FileInputStream fin = new FileInputStream(file);
            int n = 0;
            b = new byte[(int) file.length()];
            n = fin.read(b);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return b;
    }

    public static void writeFile(File file, byte[] imageData) {
        try {
            FileOutputStream fout = new FileOutputStream(file);
            fout.write(imageData);
            fout.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
