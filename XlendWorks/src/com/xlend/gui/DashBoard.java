package com.xlend.gui;

import com.xlend.gui.admin.AdminFrame;
import com.xlend.gui.reports.ReportsMenuDialog;
import com.xlend.orm.Sheet;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.remote.IMessageSender;
import com.xlend.util.ImagePanel;
import com.xlend.util.TexturedPanel;
import com.xlend.util.ToolBarButton;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.prefs.Preferences;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 *
 * @author Admin
 */
public class DashBoard extends JFrame {

    public static final String XLEND_PLANT = "Xlend Plant Cc.";
    private static final String PROPERTYFILENAME = "XlendWorks.config";
    private static final String BACKGROUNDIMAGE = "Background.png";//"gears-background+xcost.jpg";
    public final static String LASTLOGIN = "LastLogin";//    public final static String PWDMD5 = "PWDMD5";
    private static IMessageSender exchanger;
    private static Properties props;
    public static DashBoard ourInstance;

    public static Properties getProperties() {
        return props;
    }

    public static IMessageSender getExchanger() {
        return exchanger;
    }

    public static void setExchanger(IMessageSender exch) {
        exchanger = exch;
    }
    private final JButton docsButton;
    private final JButton sitesButton;
    private final JButton hrbutton;
    private final JButton partsbutton;
    private final JButton fleetbutton;
    private final JButton logisticsButton;
    private final JButton bankingbutton;
    private final JButton logoutButton;
    private JPanel controlsPanel;
    private final JLabel userLogin;
    private GeneralFrame workFrame = null;
    private GeneralFrame sitesFrame = null;
    private GeneralFrame hrFrame = null;
    private GeneralFrame fleetFrame = null;
    private GeneralFrame adminFrame = null;
    private GeneralFrame reportsFrame = null;
    private GeneralFrame logisticsFrame = null;
    private GeneralFrame bankingFrame = null;
    private ToolBarButton adminButton = null;
    private final JButton reportsButton;

    private void updateSheetList() {
        updateSheetList("DOCS", DocFrame.sheets());
        updateSheetList("SITES", SitesFrame.sheets());
        updateSheetList("HR", HRFrame.sheets());
        updateSheetList("REPORTS", ReportsFrame.sheets());
        updateSheetList("FLEET", FleetFrame.sheets());
        updateSheetList("LOGISTICS", LogisticsFrame.sheets());
        updateSheetList("BANKING", BankingFrame.sheets());
    }

    private void updateSheetList(String parentName, String[] sheetNames) {
        DbObject rec;
        DbObject[] children;
        DbObject[] sheets;
        try {
            sheets = exchanger.getDbObjects(Sheet.class, "sheetname='" + parentName + "'", "sheet_id");
            Sheet sh;
            String[] names;
            int papa_id;
            if (sheets.length == 0) {
                sh = new Sheet(null);
                sh.setSheetname(parentName);
                sh.setSheetId(0);
                sh.setNew(true);
                sh = (Sheet) exchanger.saveDbObject(sh);
                papa_id = sh.getSheetId();
            } else {
                sh = (Sheet) sheets[0];
                papa_id = sh.getSheetId();
                for (int i = 1; i < sheets.length; i++) {//to remove duplicates
                    exchanger.deleteObject(sheets[i]);
                }
            }
            sheets = exchanger.getDbObjects(Sheet.class, "parent_id=" + papa_id, null);
            for (String s : sheetNames) {
                children = exchanger.getDbObjects(Sheet.class, "parent_id=" + papa_id + " and sheetname='" + s + "'", null);
                if (children.length == 0) {
                    sh = new Sheet(null);
                    sh.setSheetname(s);
                    sh.setParentId(papa_id);
                    sh.setSheetId(0);
                    sh.setNew(true);
                    exchanger.saveDbObject(sh);
                } else {
                    for (int i = 1; i < children.length; i++) {
                        exchanger.deleteObject(children[i]);
                    }
                }
            }
        } catch (Exception ex) {
            XlendWorks.log(ex);
        }
    }

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
        ourInstance = this;
        XlendWorks.setWindowIcon(this, "Xcost.png");
        addWindowListener(new WinListener(this));
        exchanger = exch;
        getContentPane().setLayout(new BorderLayout());

        LayerPanel layers = new LayerPanel();
        controlsPanel = new JPanel(new BorderLayout());
        JPanel main = new TexturedPanel(BACKGROUNDIMAGE);
        controlsPanel.add(main, BorderLayout.CENTER);
        ImagePanel img = new ImagePanel(XlendWorks.loadImage(BACKGROUNDIMAGE, this));
        addNotify();
        Insets insets = getInsets();
        int dashWidth = img.getWidth();
        int dashHeight = img.getHeight();
        int yShift = 97;
        int xShift = 20;
        this.setMinimumSize(new Dimension(dashWidth + insets.left + insets.right, dashHeight + insets.top + insets.bottom));
        layers.add(controlsPanel, JLayeredPane.DEFAULT_LAYER);

        getContentPane().add(layers, BorderLayout.CENTER);
        readProperty("junk", ""); // just to init properties

        img = new ImagePanel(XlendWorks.loadImage("admin.png", this));
        adminButton = new ToolBarButton("admin.png");
        adminButton.setBounds(75, 37, img.getWidth(), img.getHeight());
        main.add(adminButton);
        adminButton.setVisible(XlendWorks.isCurrentAdmin());

        docsButton = new ToolBarButton("Docs.png");
        sitesButton = new ToolBarButton("Sites.png");
        reportsButton = new ToolBarButton("Reports.png");
        hrbutton = new ToolBarButton("HR.png");
        partsbutton = new ToolBarButton("Parts.png");
        fleetbutton = new ToolBarButton("Fleet.png");
        bankingbutton = new ToolBarButton("Banking.png");
        logisticsButton = new ToolBarButton("Logistics.png");
        logoutButton = new ToolBarButton("logout.png");

        img = new ImagePanel(XlendWorks.loadImage("Docs.png", this));
        docsButton.setBounds(32, 285, img.getWidth(), img.getHeight());
//        docsButton.setBackground(new Color(.5f, .5f, .5f, .0f));
        main.add(docsButton);

        img = new ImagePanel(XlendWorks.loadImage("HR.png", this));
        hrbutton.setBounds(192, 285, img.getWidth(), img.getHeight());
//        hrbutton.setBackground(new Color(.5f, .5f, .5f, .0f));
        main.add(hrbutton);

        img = new ImagePanel(XlendWorks.loadImage("Parts.png", this));
        partsbutton.setBounds(352, 285, img.getWidth(), img.getHeight());
//        partsbutton.setBackground(new Color(.5f, .5f, .5f, .0f));
        main.add(partsbutton);

        img = new ImagePanel(XlendWorks.loadImage("Sites.png", this));
        sitesButton.setBounds(512, 285, img.getWidth(), img.getHeight());
//        sitesButton.setBackground(new Color(.5f, .5f, .5f, .0f));
        main.add(sitesButton);

        img = new ImagePanel(XlendWorks.loadImage("Fleet.png", this));
        fleetbutton.setBounds(672, 285, img.getWidth(), img.getHeight());
//        fleetbutton.setBackground(new Color(.5f, .5f, .5f, .0f));
        main.add(fleetbutton);

        img = new ImagePanel(XlendWorks.loadImage("Banking.png", this));
        bankingbutton.setBounds(166, 447, img.getWidth(), img.getHeight());
//        bankingbutton.setBackground(new Color(.5f, .5f, .5f, .0f));
        main.add(bankingbutton);

        img = new ImagePanel(XlendWorks.loadImage("Reports.png", this));
        reportsButton.setBounds(340, 447, img.getWidth(), img.getHeight());
//        reportsButton.setBackground(new Color(.5f, .5f, .5f, .0f));
        main.add(reportsButton);

        img = new ImagePanel(XlendWorks.loadImage("Logistics.png", this));
        logisticsButton.setBounds(490, 445, img.getWidth(), img.getHeight());
//        logisticsButton.setBackground(new Color(.5f, .5f, .5f, .0f));
        main.add(logisticsButton);

        img = new ImagePanel(XlendWorks.loadImage("logout.png", this));
        logoutButton.setBounds(dashWidth - img.getWidth() - 76, 37, img.getWidth(), img.getHeight());
        main.add(logoutButton);

        adminButton.addActionListener(new AbstractAction() {

            public void actionPerformed(ActionEvent e) {
                if (adminFrame == null) {
                    adminFrame = new AdminFrame(getExchanger());
                } else {
                    try {
                        adminFrame.setLookAndFeel(readProperty("LookAndFeel",
                                UIManager.getSystemLookAndFeelClassName()));
                    } catch (Exception ex) {
                    }
                    adminFrame.setVisible(true);
                }
            }
        });

        docsButton.addActionListener(new AbstractAction() {

            public void actionPerformed(ActionEvent e) {
                if (workFrame == null) {
                    workFrame = new DocFrame(getExchanger());
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

        sitesButton.addActionListener(new AbstractAction() {

            public void actionPerformed(ActionEvent e) {
                if (sitesFrame == null) {
                    sitesFrame = new SitesFrame(getExchanger());
                } else {
                    try {
                        sitesFrame.setLookAndFeel(readProperty("LookAndFeel",
                                UIManager.getSystemLookAndFeelClassName()));
                    } catch (Exception ex) {
                    }
                    sitesFrame.setVisible(true);
                }
            }
        });

        reportsButton.addActionListener(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new ReportsMenuDialog();
                if (ReportsMenuDialog.okPressed) {
                    reportsFrame = new ReportsFrame(getExchanger());
                }
            }
        });

        hrbutton.addActionListener(new AbstractAction() {

            public void actionPerformed(ActionEvent e) {
                if (hrFrame == null) {
                    hrFrame = new HRFrame(getExchanger());
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
                    fleetFrame = new FleetFrame(getExchanger());
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

        logisticsButton.addActionListener(new AbstractAction() {

            public void actionPerformed(ActionEvent e) {
                if (logisticsFrame == null) {
                    logisticsFrame = new LogisticsFrame(getExchanger());
                } else {
                    try {
                        logisticsFrame.setLookAndFeel(readProperty("LookAndFeel",
                                UIManager.getSystemLookAndFeelClassName()));
                    } catch (Exception ex) {
                    }
                    logisticsFrame.setVisible(true);
                }
            }
        });

        bankingbutton.addActionListener(new AbstractAction() {

            public void actionPerformed(ActionEvent e) {
                if (bankingFrame == null) {
                    bankingFrame = new BankingFrame(getExchanger());
                } else {
                    try {
                        bankingFrame.setLookAndFeel(readProperty("LookAndFeel",
                                UIManager.getSystemLookAndFeelClassName()));
                    } catch (Exception ex) {
                    }
                    bankingFrame.setVisible(true);
                }
            }
        });

        logoutButton.addActionListener(new AbstractAction() {

            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                saveProps();
                if (XlendWorks.login(getExchanger())) {
                    userLogin.setText(XlendWorks.getCurrentUserLogin());
                    adminButton.setVisible(XlendWorks.isCurrentAdmin());
                    setVisible(true);
                    repaint();
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

//        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//        setLocation(screenSize.width - getWidth() - 10, 10);

        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(d.width / 2 - getWidth() / 2, d.height / 2 - getHeight() / 2);

        setResizable(false);
        setVisible(true);
        updateSheetList();
    }

    public void setVisible(boolean show) {
        if (adminFrame != null) {
            adminFrame.dispose();
            adminFrame = null;
        }
        if (workFrame != null) {
            workFrame.dispose();
            workFrame = null;
        }
        if (sitesFrame != null) {
            sitesFrame.dispose();
            sitesFrame = null;
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
            if (XlendWorks.getCurrentUser() != null) {
                props.setProperty(LASTLOGIN, XlendWorks.getCurrentUser().getLogin());
            }
            props.setProperty("ServerAddress", props.getProperty("ServerAddress", "localhost:1099"));
        }
        Preferences userPref = Preferences.userRoot();
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
