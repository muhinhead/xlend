package com.xlend.gui;

import com.xlend.gui.admin.AdminFrame;
import com.xlend.gui.parts.PartsDashBoard;
import com.xlend.gui.reports.ReportsMenuDialog;
import com.xlend.orm.Sheet;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.remote.IMessageSender;
import com.xlend.util.ImagePanel;
import com.xlend.util.ToolBarButton;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Properties;
import java.util.prefs.Preferences;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JLabel;
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
    private JButton fuelButton;
    private JButton adminButton;
    private JLabel userLogin;
    private GeneralFrame workFrame = null;
    private GeneralFrame sitesFrame = null;
    private GeneralFrame hrFrame = null;
    private GeneralFrame fleetFrame = null;
    private GeneralFrame adminFrame = null;
    private GeneralFrame logisticsFrame = null;
    private GeneralFrame bankingFrame = null;
    private GeneralFrame fuelFrame = null;
//    private int dashWidth;
//    private int dashHeight;

    public DashBoard(IMessageSender exch) {
        super("Xcost", true);
        ourInstance = this;
        setExchanger(exch);
        updateSheetList(exch);
        setVisible(true);
    }

    @Override
    protected void fillControlsPanel() throws HeadlessException {
        ImagePanel img = new ImagePanel(XlendWorks.loadImage("AdminFX.png", this));

        adminButton = new ToolBarButton("AdminFX.png", true);
        adminButton.setBounds(75, 37, img.getWidth(), img.getHeight());
        main.add(adminButton);
        adminButton.setVisible(XlendWorks.isCurrentAdmin());

        docsButton = new ToolBarButton("Docs.png", true);
        sitesButton = new ToolBarButton("Sites.png", true);
        reportsButton = new ToolBarButton("Reports.png", true);
        hrbutton = new ToolBarButton("HR.png", true);
        partsbutton = new ToolBarButton("Parts.png", true);
        fleetbutton = new ToolBarButton("Fleet.png", true);
        bankingbutton = new ToolBarButton("Banking.png", true);
        logisticsButton = new ToolBarButton("Logistics.png", true);
        logoutButton = new ToolBarButton("ExitFX.png", true);
        fuelButton = new ToolBarButton("Fuel.png", true);

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
        bankingbutton.setBounds(86, 447, img.getWidth(), img.getHeight());
//        bankingbutton.setBackground(new Color(.5f, .5f, .5f, .0f));
        main.add(bankingbutton);

        img = new ImagePanel(XlendWorks.loadImage("Reports.png", this));
        reportsButton.setBounds(260, 447, img.getWidth(), img.getHeight());
//        reportsButton.setBackground(new Color(.5f, .5f, .5f, .0f));
        main.add(reportsButton);

        img = new ImagePanel(XlendWorks.loadImage("Logistics.png", this));
        logisticsButton.setBounds(410, 445, img.getWidth(), img.getHeight());
//        logisticsButton.setBackground(new Color(.5f, .5f, .5f, .0f));
        main.add(logisticsButton);

        img = new ImagePanel(XlendWorks.loadImage("Fuel.png", this));
        fuelButton.setBounds(590, 445, img.getWidth(), img.getHeight());
        main.add(fuelButton);

        img = new ImagePanel(XlendWorks.loadImage("ExitFX.png", this));
        logoutButton.setBounds(dashWidth - img.getWidth() - 76, 37, img.getWidth(), img.getHeight());
        main.add(logoutButton);

        adminButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAdmin();
//                if (adminFrame == null) {
//                    adminFrame = new AdminFrame(exchanger);
//                } else {
//                    try {
//                        adminFrame.setLookAndFeel(readProperty("LookAndFeel",
//                                UIManager.getSystemLookAndFeelClassName()));
//                    } catch (Exception ex) {
//                    }
//                    adminFrame.setVisible(true);
//                }
            }
        });

        docsButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDocs();
            }
        });

        partsbutton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showParts();
            }
        });

        sitesButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSites();
            }
        });

        reportsButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showReports();
            }
        });

        hrbutton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showHR();
            }
        });

        fleetbutton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showFleet();
            }
        });

        logisticsButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showLogistics();
            }
        });

        bankingbutton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showBanking();
            }
        });

        logoutButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DashBoard.ourInstance.exit();
            }
        });

        fuelButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                showFuel();
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

    public static void showDocs() {
        if (ourInstance.workFrame == null) {
            ourInstance.workFrame = new DocFrame(exchanger);
        } else {
            try {
                ourInstance.workFrame.setLookAndFeel(readProperty("LookAndFeel",
                        UIManager.getSystemLookAndFeelClassName()));
            } catch (Exception ex) {
            }
            ourInstance.workFrame.setVisible(true);
        }
    }

    public static void showHR() {
        if (ourInstance.hrFrame == null) {
            ourInstance.hrFrame = new HRFrame(exchanger);
        } else {
            try {
                ourInstance.hrFrame.setLookAndFeel(readProperty("LookAndFeel",
                        UIManager.getSystemLookAndFeelClassName()));
            } catch (Exception ex) {
            }
            ourInstance.hrFrame.setVisible(true);
        }
    }

    public static void showParts() {
        final PartsDashBoard partsDashBoard = PartsDashBoard.getInstance();
        if (partsDashBoard == null) {
            new PartsDashBoard(ourInstance);
        } else {
            partsDashBoard.setVisible(true);
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

    public static void showFleet() {
        if (ourInstance.fleetFrame == null) {
            ourInstance.fleetFrame = new FleetFrame(exchanger);
        } else {
            try {
                ourInstance.fleetFrame.setLookAndFeel(readProperty("LookAndFeel",
                        UIManager.getSystemLookAndFeelClassName()));
            } catch (Exception ex) {
            }
            ourInstance.fleetFrame.setVisible(true);
        }
    }

    public static void showSites() {
        if (ourInstance.sitesFrame == null) {
            ourInstance.sitesFrame = new SitesFrame(exchanger);
        } else {
            try {
                ourInstance.sitesFrame.setLookAndFeel(readProperty("LookAndFeel",
                        UIManager.getSystemLookAndFeelClassName()));
            } catch (Exception ex) {
            }
            ourInstance.sitesFrame.setVisible(true);
        }
    }

    public static void showBanking() {
        if (ourInstance.bankingFrame == null) {
            ourInstance.bankingFrame = new BankingFrame(exchanger);
        } else {
            try {
                ourInstance.bankingFrame.setLookAndFeel(readProperty("LookAndFeel",
                        UIManager.getSystemLookAndFeelClassName()));
            } catch (Exception ex) {
            }
            ourInstance.bankingFrame.setVisible(true);
        }
    }

    public static void showReports() {
        new ReportsMenuDialog();
        if (ReportsMenuDialog.okPressed) {
            ReportsFrame reportsFrame = new ReportsFrame(exchanger);
        }
    }

    public static void showLogistics() {
        if (ourInstance.logisticsFrame == null) {
            ourInstance.logisticsFrame = new LogisticsFrame(exchanger);
        } else {
            try {
                ourInstance.logisticsFrame.setLookAndFeel(readProperty("LookAndFeel",
                        UIManager.getSystemLookAndFeelClassName()));
            } catch (Exception ex) {
            }
            ourInstance.logisticsFrame.setVisible(true);
        }
    }

    public static void showFuel() {
        if (ourInstance.fuelFrame == null) {
            ourInstance.fuelFrame = new FuelFrame(exchanger);
        } else {
            try {
                ourInstance.fuelFrame.setLookAndFeel(readProperty("LookAndFeel",
                        UIManager.getSystemLookAndFeelClassName()));
            } catch (Exception ex) {
            }
            ourInstance.fuelFrame.setVisible(true);
        }
    }

    public static void showAdmin() {
        if (ourInstance.adminFrame == null) {
            ourInstance.adminFrame = new AdminFrame(exchanger);
        } else {
            try {
                ourInstance.adminFrame.setLookAndFeel(readProperty("LookAndFeel",
                        UIManager.getSystemLookAndFeelClassName()));
            } catch (Exception ex) {
            }
            ourInstance.adminFrame.setVisible(true);
        }
    }

    private void updateSheetList(IMessageSender exch) {
        setExchanger(exch);
        HashSet<String> allSheetNames = new HashSet<String>();
        for (String[] sheetList : new String[][]{DocFrame.sheets(), SitesFrame.sheets(),
            HRFrame.sheets(), ReportsFrame.sheets(), FleetFrame.sheets(), LogisticsFrame.sheets(),
            BankingFrame.sheets(), PartsDashBoard.sheets(), FuelFrame.sheets()}) {
            for (String sheetName : sheetList) {
                allSheetNames.add(sheetName);
            }
        }
        DbObject[] sheets;
        ArrayList<Sheet> obsoleteSheets = new ArrayList<Sheet>();
        try {
            sheets = exch.getDbObjects(Sheet.class, "parent_id is not null", null);
            for (DbObject rec : sheets) {
                Sheet sheet = (Sheet) rec;
                if (!allSheetNames.contains(sheet.getSheetname())) {
                    obsoleteSheets.add(sheet);
                }
            }
        } catch (Exception ex) {
            XlendWorks.log(ex);
        }

        if (obsoleteSheets.size() > 0) {
            XlendWorks.log("Removing OBSOLETE SHEETS:");
        }
        for (Sheet s : obsoleteSheets) {
            XlendWorks.log("!! id=" + s.getSheetId() + " name=[" + s.getSheetname() + "] parent_id=" + s.getParentId());
            try {
                exch.deleteObject(s);
            } catch (RemoteException ex) {
                XlendWorks.log(ex);
            }
        }

        updateSheetList("DOCS", DocFrame.sheets());
        updateSheetList("SITES", SitesFrame.sheets());
        updateSheetList("HR", HRFrame.sheets());
        updateSheetList("REPORTS", ReportsFrame.sheets());
        updateSheetList("FLEET", FleetFrame.sheets());
        updateSheetList("LOGISTICS", LogisticsFrame.sheets());
        updateSheetList("BANKING", BankingFrame.sheets());
        updateSheetList("PARTS", PartsDashBoard.sheets());
        updateSheetList("FUEL", FuelFrame.sheets());
    }

    private static void updateSheetList(String parentName, String[] sheetNames) {
        DbObject rec;
        DbObject[] children;
        DbObject[] sheets;
        try {
            sheets = exchanger.getDbObjects(Sheet.class, "sheetname like binary '" + parentName + "'", "sheet_id");
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
                children = exchanger.getDbObjects(Sheet.class, "parent_id=" + papa_id + " and sheetname like binary '" + s + "'", null);
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

    private static String getPropertyFileName() {
        String propFileName = (new File(PROPERTYFILENAME).exists() ? PROPERTYFILENAME : XlendWorks.getHomeDir()+PROPERTYFILENAME);
//        System.out.println("!!!! propFileName="+propFileName);
        return propFileName;
    }
    
    public static void saveProperties() {
        try {
            if (props != null) {
                props.store(new FileOutputStream(getPropertyFileName()),
                        "-----------------------");
            }
        } catch (IOException e) {
            //e.printStackTrace();
            XlendWorks.logAndShowMessage(e);
        }
    }

    public static void saveProps() {
        if (props != null) {
            if (XlendWorks.getCurrentUser() != null) {
                props.setProperty(LASTLOGIN, XlendWorks.getCurrentUser().getLogin());
            }
            props.setProperty("ServerAddress", props.getProperty("ServerAddress", 
                    XlendWorks.defaultServerIP + ":1099"));
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
                File propFile = new File(getPropertyFileName());
                if (!propFile.exists() || propFile.length() == 0) {
//                    String curPath = propFile.getAbsolutePath();
//                    curPath = curPath.substring(0,
//                            curPath.indexOf(getPropertyFileName())).replace('\\', '/');
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

//    public static IMessageSender getExchanger() {
//        return exchanger;
//    }
//
    static void setExchanger(IMessageSender iMessageSender) {
        exchanger = iMessageSender;
    }

//    @Override
    protected String getBackGroundImage() {
        return BACKGROUNDIMAGE;
    }

    @Override
    public void lowLevelInit() {
        readProperty("junk", ""); // just to init properties
    }
}
