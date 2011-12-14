package com.xlend.util;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author Nick Mukhin
 */
public class ToolBarButton extends JButton implements MouseListener {

    private static Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
    private boolean containsCursor = false;

    public ToolBarButton(Icon icon) {
        super(icon);
        setVerticalTextPosition(BOTTOM);
        setHorizontalTextPosition(CENTER);
        addMouseListener(this);
    }

    public ToolBarButton(String imageFile) {
        super(new ImageIcon(Util.loadImage(imageFile)));
        addMouseListener(this);
    }

    public ToolBarButton(String imageFile, String text) {
        super(text);
        setIcon(new ImageIcon(Util.loadImage(imageFile)));
        addMouseListener(this);
    }

    @Override
    protected void paintBorder(Graphics g) {
        if (containsCursor) {
            super.paintBorder(g);
        }
    }

    public void mouseClicked(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mousePressed(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mouseReleased(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mouseEntered(MouseEvent e) {
        if (isEnabled()) {
            containsCursor = true;
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
    }

    public void mouseExited(MouseEvent e) {
        if (isEnabled()) {
            containsCursor = false;
            setCursor(Cursor.getDefaultCursor().getDefaultCursor());
        }
    }
}
