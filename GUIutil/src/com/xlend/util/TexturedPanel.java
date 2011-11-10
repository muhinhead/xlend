package com.xlend.util;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author Nick Mukhin
 */
public class TexturedPanel extends JPanel {

    private static final long serialVersionUID = -8942734945873090478L;
    private Paint texture;

    public TexturedPanel(String imgName) {
        this(true, imgName);
    }

    public TexturedPanel(boolean isDoubleBuffered, String imgName) {
        this(null, isDoubleBuffered, imgName);
    }

    public TexturedPanel(LayoutManager layout, boolean isDoubleBuffered, String imgName) {
        super(layout, isDoubleBuffered);
        try {
            BufferedImage bgImage;
            File f = new File("images/" + imgName);
            if (f.exists()) {
                ImageIcon ic = new javax.swing.ImageIcon("images/" + imgName, "");
                bgImage = ImageIO.read(f);
            } else {
                bgImage = ImageIO.read(getClass().getResourceAsStream("/" + imgName));
            }
            Rectangle2D rect = new Rectangle(bgImage.getWidth(),bgImage.getHeight());
            texture = new TexturePaint(bgImage, rect);//new Rectangle2D.Float(0f, 0f, 116f, 116f));
        } catch (Exception ex) {
        }
    }

    public TexturedPanel(LayoutManager layout, String imgName) {
        this(layout, true, imgName);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (isOpaque() && texture != null) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setPaint(texture);
            g2.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}
