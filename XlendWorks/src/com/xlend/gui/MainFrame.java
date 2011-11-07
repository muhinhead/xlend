package com.xlend.gui;

import com.jtattoo.plaf.acryl.AcrylLookAndFeel;
import com.jtattoo.plaf.aero.AeroLookAndFeel;
import com.jtattoo.plaf.bernstein.BernsteinLookAndFeel;
import com.jtattoo.plaf.hifi.HiFiLookAndFeel;
import com.jtattoo.plaf.noire.NoireLookAndFeel;
import com.xlend.mvc.dbtable.DbTableDocument;
import com.xlend.mvc.dbtable.DbTableGridPanel;
import com.xlend.mvc.dbtable.DbTableView;
import com.xlend.orm.Profile;
import com.xlend.orm.Userprofile;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.remote.IMessageSender;
import com.xlend.util.ToolBarButton;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Nick Mukhin
 */
class MainFrame extends JFrame {

    public static final String XLEND_PLANT = "Xlend Plant Cc.";
    private static final String PROPERTYFILENAME = "XlendWorks.config";
    public static final String SELECT_FROM_USERS =
            "Select profile_id \"Id\","
            + "first_name \"First Name\",last_name \"Last Name\","
            + "city  \"City\", state  \"Distrikte\", email \"E-mail\" "
            + " from v_userprofile";
    private static Properties props;
    private static IMessageSender exchanger;
    public final static String LASTLOGIN = "LastLogin";
    public final static String PWDMD5 = "PWDMD5";
    protected JToolBar toolBar;
    protected ToolBarButton newDocumentButton;
    protected ToolBarButton aboutButton;
    protected ToolBarButton exitButton;
    private JPanel statusPanel = new JPanel();
    private JLabel statusLabel1 = new JLabel();
    private JLabel statusLabel2 = new JLabel();
    private JLabel statusLabel3 = new JLabel();
    private DbTableGridPanel usersPanel = null;

    private void fillContentPane() {
        XlendWorks.setWindowIcon(this, "Xcost.png");
        statusPanel.setBorder(BorderFactory.createEtchedBorder());
        statusPanel.setLayout(new BorderLayout());
        setStatusLabel1Text(" ");
        statusLabel1.setBorder(BorderFactory.createEtchedBorder());
        statusLabel2.setHorizontalTextPosition(SwingConstants.CENTER);
        statusLabel2.setText(" ");
//        statusPanel.add(statusLabel1,BorderLayout.WEST);
        statusPanel.add(statusLabel2,BorderLayout.CENTER);

        buildMenu();
        getContentPane().setLayout(new BorderLayout());
        JTabbedPane tabs = new JTabbedPane();
        tabs.add(getContactsPanel(), "Contacts");
        tabs.add(getDocumentPanel(), "Documents");
        tabs.add(new JPanel(), "Contracts");
        tabs.add(new JPanel(), "Machines");
        tabs.add(new JPanel(), "Sites");
        tabs.add(getUsersPanel(), "Users");

        newDocumentButton = new ToolBarButton("newdoc.png");
        newDocumentButton.setToolTipText("New document");
        aboutButton = new ToolBarButton("help.png");
        aboutButton.setToolTipText("About program");
        exitButton = new ToolBarButton("exit.png");
        exitButton.setToolTipText("Exit");

        toolBar = new JToolBar();
        toolBar.add(newDocumentButton);
        toolBar.addSeparator();
        toolBar.add(aboutButton);
        toolBar.add(exitButton);

        getContentPane().add(toolBar, BorderLayout.NORTH);
        getContentPane().add(tabs);
        getContentPane().add(statusPanel, 
                java.awt.BorderLayout.SOUTH);

        exitButton.addActionListener(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                exit();
            }
        });
        newDocumentButton.addActionListener(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                notImplementedYet();
            }
        });
        aboutButton.addActionListener(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                notImplementedYet();
            }
        });
    }

    private Component getContactsPanel() {
        return new JPanel();
    }

    private Component getDocumentPanel() {
        return new JPanel();
    }

    private void updateGrid(DbTableView view, DbTableDocument doc, String select)
            throws RemoteException {
        int row = view.getSelectedRow();
        doc.setBody(exchanger.getTableBody(select));
        view.getController().updateExcept(null);
        row = row < view.getRowCount() ? row : row - 1;
        if (row >= 0) {
            view.setRowSelectionInterval(row, row);
        }
    }

    private AbstractAction addUserAction() {
        return new AbstractAction("Add User") {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new EditUserDialog("New User", null);
                    if (EditUserDialog.okPressed) {
                        updateGrid(usersPanel.getTableView(),
                                usersPanel.getTableDoc(), SELECT_FROM_USERS);
                    }
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                    errMessageBox("Error:", ex.getMessage());
                }
            }
        };
    }

    private AbstractAction editUserAction() {
        return new AbstractAction("Edit User") {

            @Override
            public void actionPerformed(ActionEvent e) {
                int id = usersPanel.getSelectedID();
                if (id > 0) {
                    try {
                        Profile pf = (Profile) exchanger.loadDbObjectOnID(Profile.class, id);
                        Userprofile up = (Userprofile) exchanger.loadDbObjectOnID(Userprofile.class, id);
                        DbObject[] recs = new DbObject[]{pf, up};
                        new EditUserDialog("Edit User", recs);
                        if (EditUserDialog.okPressed) {
                            updateGrid(usersPanel.getTableView(),
                                    usersPanel.getTableDoc(), SELECT_FROM_USERS);
                        }
                    } catch (RemoteException ex) {
                        ex.printStackTrace();
                        errMessageBox("Error:", ex.getMessage());
                    }
                }
            }
        };
    }

    private AbstractAction delUserAction() {
        return new AbstractAction("Delete User") {

            @Override
            public void actionPerformed(ActionEvent e) {
                int id = usersPanel.getSelectedID();
                if (id > 0) {
                    try {
                        Profile pf = (Profile) exchanger.loadDbObjectOnID(Profile.class, id);
                        if (yesNo("Attention!", "Do you want to delete user [" + pf.getFirstName() + " " + pf.getLastName() + "]?")
                                == JOptionPane.YES_OPTION) {
                            exchanger.deleteObject(pf);
                            updateGrid(usersPanel.getTableView(),
                                    usersPanel.getTableDoc(), SELECT_FROM_USERS);
                        }
                    } catch (RemoteException ex) {
                        ex.printStackTrace();
                        errMessageBox("Error:", ex.getMessage());
                    }
                }
            }
        };
    }

    private JPanel getUsersPanel() {
        if (null == usersPanel) {
            try {
                usersPanel = new DbTableGridPanel(
                        addUserAction(), editUserAction(), delUserAction(),
                        exchanger.getTableBody(SELECT_FROM_USERS));
                //TODO: more complex logic based on current user's responsibility
                usersPanel.getDelAction().setEnabled(XlendWorks.getCurrentUser().getManager() == 1);
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        }
        return usersPanel;
    }

    private void buildMenu() {
        JMenuBar bar = new JMenuBar();
        JMenu m = createMenu("File", "File Operations");//new JMenu("File");
        JMenuItem mi = createMenuItem("Exit","Exit from program");//new JMenuItem("Exit");
        mi.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                exit();
            }
        });
        m.add(mi);
        bar.add(m);

        m = createMenu("Options","Program settings");//new JMenu("Options");
        m.add(appearanceMenu("Theme"));
        bar.add(m);

        setJMenuBar(bar);
    }

    protected JMenu appearanceMenu(String item) {
        JMenu m;
        JMenuItem it;
        m = createMenu(item);
        it = m.add(createMenuItem("Tiny"));
        it.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    setLookAndFeel("de.muntjak.tinylookandfeel.TinyLookAndFeel");
                } catch (Exception e1) {
                }
            }
        });
        it = m.add(createMenuItem("Acryl"));
        it.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    AcrylLookAndFeel.setTheme("Default", "LICENSE KEY HERE", XLEND_PLANT);
                    setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
                } catch (Exception e1) {
                }
            }
        });
        it = m.add(createMenuItem("Nimbus"));
        it.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
                } catch (Exception e1) {
                }
            }
        });
        it = m.add(createMenuItem("Noire"));
        it.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    NoireLookAndFeel.setTheme("Default", "LICENSE KEY HERE", XLEND_PLANT);
                    setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");
                } catch (Exception e1) {
                }
            }
        });
        it = m.add(createMenuItem("HiFi"));
        it.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    HiFiLookAndFeel.setTheme("Default", "LICENSE KEY HERE", XLEND_PLANT);
                    setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
                } catch (Exception e1) {
                }
            }
        });
        it = m.add(createMenuItem("Bernstein"));
        it.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    BernsteinLookAndFeel.setTheme("Default", "LICENSE KEY HERE", XLEND_PLANT);
                    setLookAndFeel("com.jtattoo.plaf.bernstein.BernsteinLookAndFeel");
                } catch (Exception e1) {
                }
            }
        });
        it = m.add(createMenuItem("Aero"));
        it.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    AeroLookAndFeel.setTheme("Green", "LICENSE KEY HERE", XLEND_PLANT);
                    setLookAndFeel("com.jtattoo.plaf.aero.AeroLookAndFeel");
                } catch (Exception e1) {
                }
            }
        });
        it = m.add(createMenuItem("Nimrod"));
        it.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    setLookAndFeel("com.nilo.plaf.nimrod.NimRODLookAndFeel");
                } catch (Exception e1) {
                }
            }
        });
        it = m.add(createMenuItem("System"));
        it.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e1) {
                }
            }
        });
        it = m.add(createMenuItem("Java"));
        it.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
                } catch (Exception e1) {
                }
            }
        });
        it = m.add(createMenuItem("Motif"));
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
//        try {
//            String theme = readProperty("LookAndFeel", "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
//            if (theme.contains("jtattoo")) {
//                setSubThemes();
//            }
//            setLookAndFeel(theme);
//        } catch (Exception e) {
//            try {
//                setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//            } catch (Exception ie) {
//                XlendWorks.log(ie);
//            }
//        }
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

    public void setStatusLabel1Text(String lbl) {
        statusLabel1.setText(lbl);
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
        System.exit(1);
    }

    public void saveProperties() {
        try {
            props.store(new FileOutputStream(PROPERTYFILENAME),
                    "-----------------------");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static IMessageSender getExchanger() {
        return exchanger;
    }

    public static void setSubThemes() {
        AcrylLookAndFeel.setTheme("Default", "LICENSE KEY HERE", MainFrame.XLEND_PLANT);
        HiFiLookAndFeel.setTheme("Default", "LICENSE KEY HERE", MainFrame.XLEND_PLANT);
        NoireLookAndFeel.setTheme("Default", "LICENSE KEY HERE", MainFrame.XLEND_PLANT);
        BernsteinLookAndFeel.setTheme("Default", "LICENSE KEY HERE", MainFrame.XLEND_PLANT);
        AeroLookAndFeel.setTheme("Green", "LICENSE KEY HERE", MainFrame.XLEND_PLANT);
    }

    protected JMenuItem createMenuItem(String label, String microHelp) {
        JMenuItem m = new JMenuItem(label);
        setMenuStatusMicroHelp(m, microHelp);
        return m;
    }

    protected JMenuItem createMenuItem(String label) {
        return createMenuItem(label,label);
    }

    protected JMenu createMenu(String label, String microHelp) {
        JMenu m = new JMenu(label);
        setMenuStatusMicroHelp(m, microHelp);
        return m;
    }
    
    protected JMenu createMenu(String label) {
        return createMenu(label, label);
    }

    protected void setMenuStatusMicroHelp(final JMenuItem m, final String msg) {
        m.addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent e) {
                statusLabel2.setText(msg == null ? m.getText() : msg);
            }
        });
    }    
}
