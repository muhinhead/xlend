package com.xlend.gui;

import com.xlend.remote.IMessageSender;
import com.xlend.util.ImagePanel;
import com.xlend.util.TexturedPanel;
import com.xlend.util.ToggleToolBarButton;
import com.xlend.util.ToolBarButton;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Paint;
import java.awt.TexturePaint;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Admin
 */
public class DashBoard extends JFrame {

    public static final String XLEND_PLANT = "Xlend Plant Cc.";
    private static final String PROPERTYFILENAME = "XlendWorks.config";
    public final static String LASTLOGIN = "LastLogin";
    public final static String PWDMD5 = "PWDMD5";
    private static IMessageSender exchanger;
    //private final ImagePanel backgroundImg;
    private static Properties props;

    static Properties getProperties() {
        return props;
    }

    static IMessageSender getExchanger() {
        return exchanger;
    }
    private final ToolBarButton workButton;
    private final ToolBarButton hrbutton;
    private final ToolBarButton fleetbutton;
    private final ToolBarButton logoutButton;
    private JPanel controlsPanel;
    private final JLabel userLogin;
    private WorkFrame workFrame = null;
    private HRFrame hrFrame = null;
    private FleetFrame fleetFrame = null;

    private class WinListener extends WindowAdapter {

        public WinListener(JFrame frame) {
        }

        public void windowClosing(WindowEvent e) {
            exit();
        }
    }

    private class LayerPanel extends JLayeredPane {

        private LayerPanel() {
            super();
        }

        @Override
        public void setBounds(int x, int y, int width, int height) {
            super.setBounds(x, y, width, height);
            controlsPanel.setBounds(getBounds());
        }
    }

    public DashBoard(IMessageSender exch) {
        super("Xcost");
        XlendWorks.setWindowIcon(this, "Xcost.png");
        addWindowListener(new WinListener(this));
        exchanger = exch;
        getContentPane().setLayout(new BorderLayout());

        LayerPanel layers = new LayerPanel();
        controlsPanel = new JPanel(new BorderLayout());
        JPanel main = new TexturedPanel("startmenu.jpg");
        controlsPanel.add(main, BorderLayout.CENTER);
        ImagePanel img = new ImagePanel(XlendWorks.loadImage("startmenu.jpg", this));
        this.setMinimumSize(new Dimension(380, 320));
//        controlsPanel.setPreferredSize(img.getPreferredSize());
        layers.add(controlsPanel, JLayeredPane.DEFAULT_LAYER);

        getContentPane().add(layers, BorderLayout.CENTER);
        readProperty("junk", ""); // just to init properties

        workButton = new ToolBarButton("work.png");
        hrbutton = new ToolBarButton("hr.png");
        fleetbutton = new ToolBarButton("fleet.png");
        logoutButton = new ToolBarButton("logoutsmall.png");

        img = new ImagePanel(XlendWorks.loadImage("work.png", this));
        workButton.setBounds(32, 168, img.getWidth() + 3, img.getHeight() + 3);
        main.add(workButton);
        hrbutton.setBounds(153, 168, img.getWidth() + 3, img.getHeight() + 3);
        main.add(hrbutton);
        fleetbutton.setBounds(270, 168, img.getWidth() + 3, img.getHeight() + 3);
        main.add(fleetbutton);
        img = new ImagePanel(XlendWorks.loadImage("logoutsmall.png", this));
        logoutButton.setBounds(325, 260, img.getWidth() + 3, img.getHeight() + 3);
        main.add(logoutButton);

        workButton.addActionListener(new AbstractAction() {

            public void actionPerformed(ActionEvent e) {
                if (workFrame == null) {
                    workFrame = new WorkFrame(exchanger);
                } else {
                    try {
                        workFrame.setLookAndFeel(readProperty("LookAndFeel",
                                UIManager.getSystemLookAndFeelClassName()));
                    } catch (Exception ex) {
                    }
                    workFrame.setVisible(true);
                }
            }
        });

        hrbutton.addActionListener(new AbstractAction() {

            public void actionPerformed(ActionEvent e) {
                if (hrFrame == null) {
                    hrFrame = new HRFrame(exchanger);
                } else {
                    try {
                        hrFrame.setLookAndFeel(readProperty("LookAndFeel",
                                UIManager.getSystemLookAndFeelClassName()));
                    } catch (Exception ex) {
                    }
                    hrFrame.setVisible(true);
                }
            }
        });

        fleetbutton.addActionListener(new AbstractAction() {

            public void actionPerformed(ActionEvent e) {
                if (fleetFrame == null) {
                    fleetFrame = new FleetFrame(exchanger);
                } else {
                    try {
                        fleetFrame.setLookAndFeel(readProperty("LookAndFeel",
                                UIManager.getSystemLookAndFeelClassName()));
                    } catch (Exception ex) {
                    }
                    fleetFrame.setVisible(true);
                }
            }
        });

        logoutButton.addActionListener(new AbstractAction() {

            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                saveProps();
                if (XlendWorks.login(exchanger)) {
                    userLogin.setText(XlendWorks.getCurrentUserLogin());
                    setVisible(true);
                } else {
                    exit();
                }
            }
        });

        JLabel greeting = new JLabel("WELCOME");
        greeting.setFont(greeting.getFont().deriveFont(Font.BOLD, 12));
        greeting.setBounds(10, 10, greeting.getPreferredSize().width, greeting.getPreferredSize().height);
        main.add(greeting);
        userLogin = new JLabel(XlendWorks.getCurrentUserLogin());
        userLogin.setFont(greeting.getFont());
        userLogin.setBounds(10, 30, 50, userLogin.getPreferredSize().height);
        main.add(userLogin);

        centerWindow(this);
        setResizable(false);
        setVisible(true);
    }

    public void setVisible(boolean show) {
        if (workFrame != null) {
            workFrame.dispose();
            workFrame = null;
        }
        if (hrFrame != null) {
            hrFrame.dispose();
            hrFrame = null;
        }
        if (fleetFrame != null) {
            fleetFrame.dispose();
            fleetFrame = null;
        }
        
        super.setVisible(show);
    }

    public static void saveProperties() {
        try {
            if (props != null) {
                props.store(new FileOutputStream(PROPERTYFILENAME),
                        "-----------------------");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveProps() {
        if (props != null) {
            props.setProperty(LASTLOGIN, XlendWorks.getCurrentUser().getLogin());
            props.setProperty("ServerAddress", props.getProperty("ServerAddress", "localhost:1099"));
        }
        Preferences userPref = Preferences.userRoot();
        userPref.put(PWDMD5, XlendWorks.getCurrentUser().getPwdmd5());
        saveProperties();
    }

    private void exit() {
        saveProps();
        dispose();
        System.exit(1);
    }

    public static String readProperty(String key, String deflt) {
        if (null == props) {
            props = new Properties();
            try {
                File propFile = new File(PROPERTYFILENAME);
                if (!propFile.exists() || propFile.length() == 0) {
                    String curPath = propFile.getAbsolutePath();
                    curPath = curPath.substring(0,
                            curPath.indexOf(PROPERTYFILENAME)).replace('\\', '/');
                    propFile.createNewFile();
                    props.setProperty("user", "admin");
                    props.setProperty("userPassword", "admin");
                    propFile.createNewFile();
                } else {
                    props.load(new FileInputStream(propFile));
                }
            } catch (IOException e) {
                e.printStackTrace();
                return deflt;
            }
        }
        return props.getProperty(key, deflt);
    }

    public static void setSizes(JFrame frame, double x, double y) {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize((int) (x * d.width), (int) (y * d.height));
    }
    
    public static float getXratio(JFrame frame) {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        return (float) frame.getWidth() / d.width;
    }

    public static float getYratio(JFrame frame) {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        return (float) frame.getHeight() / d.height;
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
        frame.setLocation((screenSize.width - frameSize.width) / 2,
                (screenSize.height - frameSize.height) / 2);

        frame.setExtendedState(Frame.NORMAL);
        frame.validate();
    }
}
