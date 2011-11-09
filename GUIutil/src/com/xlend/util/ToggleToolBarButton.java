package com.xlend.util;

import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JToggleButton;

/**
 *
 * @author Nick Mukhin
 */
public class ToggleToolBarButton extends JToggleButton {
    public ToggleToolBarButton(Icon icon) {
        super(icon);
        setVerticalTextPosition(BOTTOM);
        setHorizontalTextPosition(CENTER);
    }

    public ToggleToolBarButton(String imageFile) {
        super(new ImageIcon(Util.loadImage(imageFile)));
    }

    public ToggleToolBarButton(String imageFile, String text) {
        super(text);
        setIcon(new ImageIcon(Util.loadImage(imageFile)));
    }
}
