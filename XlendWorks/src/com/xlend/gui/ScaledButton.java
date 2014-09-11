package com.xlend.gui;

import com.xlend.util.ToolBarButton;
import com.xlend.util.Util;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;

/**
 *
 * @author nick
 */
public class ScaledButton extends ToolBarButton {
    private Dimension lastSize;

    public ScaledButton(String imageFile, String title, boolean animate, Dimension d) {
        super(imageFile, animate);
        setText(title);
        scaleTo(d);
    }
    
    public void scaleTo(Dimension d) {
        lastSize = d; 
        if (originalIcon != null) {
            originalImage = originalIcon.getImage().getScaledInstance(d.width, d.height, Image.SCALE_SMOOTH);
            originalIcon.setImage(originalImage);
            originalWidth = originalIcon.getIconWidth();
        }
    }

    public void setImage(byte[] imageData, String title) {
        originalIcon = new ImageIcon(originalImage = Toolkit.getDefaultToolkit().createImage(imageData));
        scaleTo(lastSize);
        super.setIcon(originalIcon);
        setText(title);
    }
    
    public void setImage(String imageFile, String title) {
        originalIcon = new ImageIcon(originalImage = Util.loadImage(imageFile));
        scaleTo(lastSize);
        super.setIcon(originalIcon);
        setText(title);
    }
}
