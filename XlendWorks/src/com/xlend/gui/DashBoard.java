package com.xlend.gui;

import com.xlend.gui.admin.AdminFrame;
import com.xlend.gui.parts.PartsDashBoard;
import com.xlend.gui.reports.ReportsMenuDialog;
import com.xlend.orm.Sheet;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.remote.IMessageSender;
import com.xlend.util.ImagePanel;
import com.xlend.util.TexturedPanel;
import com.xlend.util.ToolBarButton;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.prefs.Preferences;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.UIManager;

/**
 *
 * @author Admin
 */
public class DashBoard extends AbstractDashBoard {

    private static IMessageSender exchanger = null;
    public static final String XLEND_PLANT = "Xlend Plant Cc.";
    private static final String PROPERTYFILENAME = "XlendWorks.config";
    private static final String BACKGROUNDIMAGE = "Background.png";//"gears-background+xcost.jpg";
    public final static String LASTLOGIN = "LastLogin";//    public final static String PWDMD5 = "PWDMD5";
    private static Properties props;
    public static DashBoard ourInstance;

    public static Properties getProperties() {
        return props;
    }
    private JButton docsButton;
    private JButton sitesButton;
    private JButton hrbutton;
    private JButton partsbutton;
    private JButton fleetbutton;
    private JButton logisticsButton;
    private JButton bankingbutton;
    private JButton logoutButton;
    private JButton reportsButton;
    private JLabel userLogin;
    private GeneralFrame workFrame = null;
    private GeneralFrame sitesFrame = null;
    private GeneralFrame hrFrame = null;
    private GeneralFrame fleetFrame = null;
    private GeneralFrame adminFrame = null;
    private GeneralFrame logisticsFrame = null;
    private GeneralFrame bankingFrame = null;
    private ToolBarButton adminButton = null;
    private int dashWidth;
    private int dashHeight;

    public DashBoard(IMessageSender exch) {
        super("Xcost");
        ourInstance = this;
        updateSheetList(exch);
        setVisible(true);
    }

    @Override
    protected void fillControlsPanel() throws HeadlessException {
        ImagePanel img = new ImagePanel(XlendWorks.loadImage("admin.png", this));
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

            @Override
            public void actionPerformed(ActionEvent e) {
                if (adminFrame == null) {
                    adminFrame = new AdminFrame();
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

            @Override
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

        partsbutton.addActionListener(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                final PartsDashBoard partsDashBoard = PartsDashBoard.getInstance();
                if (partsDashBoard == null) {
                    new PartsDashBoard(DashBoard.this);
                } else {
                    partsDashBoard.requestFocus();
                    java.awt.EventQueue.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            partsDashBoard.toFront();
                            partsDashBoard.repaint();
                        }
                    });
                }
            }
        });

        sitesButton.addActionListener(new AbstractAction() {

            @Override
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
                    ReportsFrame reportsFrame = new ReportsFrame(getExchanger());
                }
            }
        });

        hrbutton.addActionListener(new AbstractAction() {

            @Override
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

            @Override
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

            @Override
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

            @Override
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

            @Override
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

        centerOnScreen();
        setResizable(false);
    }

    private void updateSheetList(IMessageSender exch) {
        setExchanger(exch);
        updateSheetList("DOCS", DocFrame.sheets());
        updateSheetList("SITES", SitesFrame.sheets());
        updateSheetList("HR", HRFrame.sheets());
        updateSheetList("REPORTS", ReportsFrame.sheets());
        updateSheetList("FLEET", FleetFrame.sheets());
        updateSheetList("LOGISTICS", LogisticsFrame.sheets());
        updateSheetList("BANKING", BankingFrame.sheets());
    }

    public static void updateSheetList(String parentName, String[] sheetNames) {
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

    @Override
    protected void initBackground() {

        super.initBackground();
        addWindowListener(new DashBoard.WinListener(this));
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

    protected void exit() {
        saveProps();
        super.exit();
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

    @Override
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

    public static IMessageSender getExchanger() {
        return exchanger;
    }

    static void setExchanger(IMessageSender iMessageSender) {
        exchanger = iMessageSender;
    }

    @Override
    protected String getBackGroundImage() {
        return BACKGROUNDIMAGE;
    }

    @Override
    public void lowLevelInit() {
        readProperty("junk", ""); // just to init properties
    }
}
