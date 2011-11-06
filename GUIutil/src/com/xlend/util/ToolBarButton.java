/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xlend.util;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author Nick Mukhin
 */
public class ToolBarButton extends NoFrameButton {

    //private final Insets margins = new Insets(0, 0, 0, 0);
    public ToolBarButton(Icon icon) {
        super(icon);
        //setMargin(margins);
        setVerticalTextPosition(BOTTOM);
        setHorizontalTextPosition(CENTER);
    }

    public ToolBarButton(String imageFile) {
        this(new ImageIcon(imageFile));
    }

    public ToolBarButton(String imageFile, String text) {
        this(new ImageIcon(imageFile));
        setText(text);
    }
}
