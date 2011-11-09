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
        super(new ImageIcon(Util.loadImage(imageFile)));
    }

    public ToolBarButton(String imageFile, String text) {
        super(text);
        setIcon(new ImageIcon(Util.loadImage(imageFile)));
    }
}
