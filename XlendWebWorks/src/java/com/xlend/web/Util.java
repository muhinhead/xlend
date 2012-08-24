package com.xlend.web;

import java.awt.Image;
import java.awt.Window;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.swing.ImageIcon;

/**
 *
 * @author Nick Mukhin
 */
public class Util {

    public static Image loadImage(String iconName, ServletContext servletContext) {
        Image im = null;
        String path = servletContext.getRealPath(File.separator)+"images/" + iconName;
        File f = new File(path);
        if (f.exists()) {
            try {
                ImageIcon ic = new javax.swing.ImageIcon(path, "");
                im = ic.getImage();
            } catch (Exception ex) {
                Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return im;
    }
}
