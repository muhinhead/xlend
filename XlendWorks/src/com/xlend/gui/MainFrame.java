package com.xlend.gui;

import com.xlend.remote.IMessageSender;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.prefs.Preferences;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Nick Mukhin
 */
class MainFrame extends JFrame {

    private static final String PROPERTYFILENAME = "XlendWorks.config";
    private static Properties props;
    private final IMessageSender exchanger;
    public final static String LASTLOGIN = "LastLogin";
    public final static String PWDMD5 = "PWDMD5";

    private void fillContentPane() {
        buildMenu();
        getContentPane().setLayout(new BorderLayout());
        JTabbedPane tabs = new JTabbedPane();//JTabbedPane.LEFT);
        tabs.add(getContactsPanel(), "Contacts");
        tabs.add(getClientGroupsPanel(), "Client groups");
        tabs.add(getUsersPanel(), "Users");
        getContentPane().add(tabs);
    }

    private Component getContactsPanel() {
        return new JPanel();
    }

    private Component getClientGroupsPanel() {
        return new JPanel();
    }

    private Component getUsersPanel() {
        return new JPanel();
    }

    private void buildMenu() {
        JMenuBar bar = new JMenuBar();
        JMenu m = new JMenu("File");
        JMenuItem mi = new JMenuItem("Exit");
        mi.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                exit();
            }
        });
        m.add(mi);
        bar.add(m);

        m = new JMenu("Options");
        m.add(appearanceMenu("Theme"));
        bar.add(m);

        setJMenuBar(bar);
    }

    protected JMenu appearanceMenu(String item) {
        JMenu m;
        JMenuItem it;
        m = new JMenu(item);

        it = m.add(new JMenuItem("Nimbus"));
        it.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
                } catch (Exception e1) {
                }
            }
        });

        it = m.add(new JMenuItem("System"));
        it.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e1) {
                }
            }
        });
        it = m.add(new JMenuItem("Java"));
        it.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
                } catch (Exception e1) {
                }
            }
        });
        it = m.add(new JMenuItem("Motif"));
        it.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
                } catch (Exception e1) {
                }
            }
        });
        return m;
    }

    public static void setSizes(JFrame frame, double x, double y) {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize((int) (x * d.width), (int) (y * d.height));
    }

    private void setSizes(double x, double y) {
        setSizes(this, x, y);
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

    private class WinListener extends WindowAdapter {

        public WinListener(JFrame frame) {
        }

        public void windowClosing(WindowEvent e) {
            exit();
        }
    }

    public MainFrame(IMessageSender exch) {
        super("Contact Management");
        exchanger = exch;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            setLookAndFeel(readProperty("LookAndFeel",
                    "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel"));
        } catch (Exception e) {
            try {
                setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ie) {
                XlendWorks.log(ie);
            }
        }
        addWindowListener(new WinListener(this));
        fillContentPane();
        float width = Float.valueOf(readProperty("WindowWidth", "0.7"));
        float height = Float.valueOf(readProperty("WindowHeight", "0.6"));
        if (width == 0.0 || height == 0.0) {
            setSizes(.7, .8);
            centerWindow(this);
            setExtendedState(JFrame.MAXIMIZED_BOTH);
        } else {
            setSizes(width, height);
            centerWindow(this);
        }
        setVisible(true);
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

    private void setLookAndFeel(String lf) throws ClassNotFoundException,
            InstantiationException, IllegalAccessException,
            UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(lf);
        SwingUtilities.updateComponentTreeUI(this);
        props.setProperty("LookAndFeel", lf);
    }

    public static void errMessageBox(String title, String msg) {
        JOptionPane.showMessageDialog(null, msg, title, JOptionPane.ERROR_MESSAGE);
    }

    public static void infoMessageBox(String title, String msg) {
        JOptionPane.showMessageDialog(null, msg, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static int yesNo(String msg, String title) {
        int ok = JOptionPane.showConfirmDialog(null, title, msg,
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return ok;
    }

    public static void notImplementedYet() {
        errMessageBox("Sorry!", "Not implemented yet");
    }

    private void exit() {
        System.out.println("EXIT!");
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        if (getExtendedState() == JFrame.MAXIMIZED_BOTH) {
            props.setProperty("WindowWidth", "0");
            props.setProperty("WindowHeight", "0");
        } else {
            props.setProperty("WindowWidth", "" + (float) getWidth() / d.width);
            props.setProperty("WindowHeight", "" + (float) getHeight() / d.height);
        }
        props.setProperty(LASTLOGIN, XlendWorks.getCurrentUser().getLogin());
        props.setProperty("ServerAddress", props.getProperty("ServerAddress", "localhost:1099"));
        Preferences userPref = Preferences.userRoot();
        userPref.put(PWDMD5, XlendWorks.getCurrentUser().getPwdmd5());
        saveProperties();
        dispose();
    }

    public void saveProperties() {
        try {
            props.store(new FileOutputStream(PROPERTYFILENAME),
                    "-----------------------");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
