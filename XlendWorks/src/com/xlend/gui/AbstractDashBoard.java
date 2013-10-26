/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xlend.gui;

import com.xlend.util.ImagePanel;
import com.xlend.util.TexturedPanel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

/**
 *
 * @author nick
 */
public abstract class AbstractDashBoard extends JFrame {
//    protected IMessageSender msgSender;

    private static final String PROPERTYFILENAME = "XlendWorks.config";
    protected TexturedPanel main;
//    public IMessageSender getMsgSender() {
//        return msgSender;
//    }
//
//    public void setMsgSender(IMessageSender exch) {
//        this.msgSender = exch;
//    }
    protected JPanel controlsPanel;
    protected int dashWidth;
    protected int dashHeight;

    protected abstract String getBackGroundImage();

    public abstract void lowLevelInit();

    protected class WinListener extends WindowAdapter {

        public WinListener(JFrame frame) {
        }

        public void windowClosing(WindowEvent e) {
            exit();
        }
    }

    protected class LayerPanel extends JLayeredPane {

        LayerPanel() {
            super();
        }

        @Override
        public void setBounds(int x, int y, int width, int height) {
            super.setBounds(x, y, width, height);
            controlsPanel.setBounds(getBounds());
        }
    }

    protected abstract void fillControlsPanel() throws HeadlessException;

    public AbstractDashBoard(String title, boolean decorated) {
        super(title);
        setUndecorated(!decorated);
//        setResizable(false);
        lowLevelInit();
        initBackground();
        fillControlsPanel();
    }

    protected void initBackground() {
//        setMsgSender(exch);
        XlendWorks.setWindowIcon(this, "Xcost.png");
//        addWindowListener(new DashBoard.WinListener(this));
        controlsPanel = new JPanel(new BorderLayout());

        setLayout(new BorderLayout());

        LayerPanel layers = new LayerPanel();
        main = new TexturedPanel(getBackGroundImage());
        controlsPanel.add(main, BorderLayout.CENTER);
        addNotify();
        ImagePanel img = new ImagePanel(XlendWorks.loadImage(getBackGroundImage(), this));
        Insets insets = getInsets();
        dashWidth = img.getWidth();
        dashHeight = img.getHeight();
        this.setMinimumSize(new Dimension(dashWidth + insets.left + insets.right, dashHeight + insets.top + insets.bottom));
        layers.add(controlsPanel, JLayeredPane.DEFAULT_LAYER);

        getContentPane().add(layers, BorderLayout.CENTER);
    }

    public static void centerWindow(JFrame frame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
        frame.setExtendedState(Frame.NORMAL);
        frame.validate();
    }

    public void centerOnScreen() {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(d.width / 2 - getWidth() / 2, d.height / 2 - getHeight() / 2);
    }

    public static float getXratio(JFrame frame) {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        return (float) frame.getWidth() / d.width;
    }

    public static float getYratio(JFrame frame) {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        return (float) frame.getHeight() / d.height;
    }

    public static void setSizes(JFrame frame, double x, double y) {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize((int) (x * d.width), (int) (y * d.height));
    }

    public void setVisible(boolean show) {
        super.setVisible(show);
    }

    protected void exit() {
        dispose();
        System.exit(1);
    }
}
