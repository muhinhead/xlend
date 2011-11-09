package com.xlend.util;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author Nick Mukhin 
 */
public class ImagePanel extends JPanel {

  private Image img;

  public ImagePanel(String img, LayoutManager mgr) {
    this(new ImageIcon(img).getImage(), mgr);
  }

  public ImagePanel(Image img, LayoutManager mgr) {
    this.img = img;
    setLayout(mgr);
  }

  public void paintComponent(Graphics g) {
    g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
  }
}