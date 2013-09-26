package com.xlend.gui;

import com.xlend.gui.admin.AdminFrame;
import com.xlend.gui.parts.PartsDashBoard;
import com.xlend.gui.reports.ReportsMenuDialog;
import com.xlend.orm.Sheet;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.remote.IMessageSender;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Properties;
import java.util.prefs.Preferences;
import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.TimelineBuilder;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.ColorAdjustBuilder;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.UIManager;

/**
 *
 * @author Nick Mukhin
 */
public class DashBoard extends AbstractDashBoard {

    public static DashBoard ourInstance;
    private static IMessageSender exchanger = null;
    private static final String PROPERTYFILENAME = "XlendWorks.config";
    private static final String BACKGROUNDIMAGE = "BackgroundFX.png";
    public final static String LASTLOGIN = "LastLogin";
    private static Properties props;
    private GeneralFrame workFrame = null;
    private GeneralFrame sitesFrame = null;
    private GeneralFrame hrFrame = null;
    private GeneralFrame fleetFrame = null;
    private GeneralFrame adminFrame = null;
    private GeneralFrame logisticsFrame = null;
    private GeneralFrame bankingFrame = null;

    public static Properties getProperties() {
        return props;
    }

    private static class InternalApplet extends JApplet {

        private static final int JFXPANEL_WIDTH_INT = 700;
        private static final int JFXPANEL_HEIGHT_INT = 600;
        private static JFXPanel fxContainer;
        private static final double SCALE = 1.3; // коэффициент увеличения
        private static final double DURATION = 300; // время анимации в мс
        private HBox taskBar1;
        private VBox taskBar;
        private HBox taskBar2;
        private BorderPane upperPane;
        private Text userLogin;
        private Node adminButtonNode;
        private Node logoutButtonNode;
        private boolean insideExitProcess = false;

        @Override
        public void init() {
            fxContainer = new JFXPanel();
            fxContainer.setPreferredSize(new Dimension(JFXPANEL_WIDTH_INT, JFXPANEL_HEIGHT_INT));
            add(fxContainer, BorderLayout.CENTER);
            // create JavaFX scene
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    createScene();
                }
            });
        }

        private void createScene() {
            String[] icons = new String[]{
                "Docs.png", "HR.png", "Parts.png", "Sites.png",
                "Fleet.png", "Banking.png", "Reports.png", "Logistics.png",
                "AdminFX.png", "ExitFX.png"
            };

            Runnable[] btnRuns = new Runnable[]{
                new Runnable() {
                    @Override
                    public void run() {
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
                },
                new Runnable() {
                    @Override
                    public void run() {
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
                },
                new Runnable() {
                    @Override
                    public void run() {
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
                },
                new Runnable() {
                    @Override
                    public void run() {
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
                },
                new Runnable() {
                    @Override
                    public void run() {
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
                },
                new Runnable() {
                    @Override
                    public void run() {
                        if (ourInstance.bankingFrame == null) {
                            ourInstance.bankingFrame = new SitesFrame(exchanger);
                        } else {
                            try {
                                ourInstance.bankingFrame.setLookAndFeel(readProperty("LookAndFeel",
                                        UIManager.getSystemLookAndFeelClassName()));
                            } catch (Exception ex) {
                            }
                            ourInstance.bankingFrame.setVisible(true);
                        }
                    }
                },
                new Runnable() {
                    @Override
                    public void run() {
                        new ReportsMenuDialog();
                        if (ReportsMenuDialog.okPressed) {
                            ReportsFrame reportsFrame = new ReportsFrame(exchanger);
                        }
                    }
                },
                new Runnable() {
                    @Override
                    public void run() {
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
                }
            };

            BorderPane root = new BorderPane();
            upperPane = new BorderPane();
//        upperPane.setHgap(10);
//        upperPane.setVgap(10);
            upperPane.setPadding(new Insets(10, 10, 10, 10));

            taskBar = new VBox(12);
            taskBar1 = new HBox(10);
//        taskBar1.setPadding(new Insets(10, 30, 50, 30));
//        taskBar1.setPrefHeight(150);
            taskBar1.setAlignment(Pos.CENTER);

            taskBar2 = new HBox(30);
//        taskBar2.setPadding(new Insets(10, 30, 50, 30));
//        taskBar2.setPrefHeight(150);
            taskBar2.setAlignment(Pos.CENTER);

            taskBar.getChildren().add(taskBar1);
            taskBar.getChildren().add(taskBar2);

            root.setBottom(taskBar);
            root.setTop(upperPane);
            root.setLeft(userLogin = new Text("    Hi " + XlendWorks.getCurrentUserLogin()));
//        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));

            String image = DashBoard.InternalApplet.class.getResource("/BackgroundFX.png").toExternalForm();
            root.setStyle("-fx-background-color: #646464;");
//        System.out.println("!!image: "+image);
            root.setStyle("-fx-background-image: url('" + image + "');"
                    + "-fx-background-position: center center;"
                    + "-fx-background-repeat: stretch;");

            for (int i = 0; i < 5; i++) {
                Node node = createButton("/" + icons[i], btnRuns[i]);
                taskBar1.getChildren().add(node);
            }
            for (int i = 5; i < icons.length - 2; i++) {
                Node node = createButton("/" + icons[i], btnRuns[i]);
                taskBar2.getChildren().add(node);
            }

            adminButtonNode = createButton("/" + icons[icons.length - 2], new Runnable() {
                @Override
                public void run() {
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
            });
            upperPane.setLeft(adminButtonNode);
            adminButtonNode.setVisible(XlendWorks.isCurrentAdmin());
            logoutButtonNode = createButton("/" + icons[icons.length - 1], new Runnable() {
                @Override
                public void run() {
                    ourInstance.exit();
                }
            });
            upperPane.setRight(logoutButtonNode);
            fxContainer.setScene(new Scene(root));
        }

        private void setInsideExitProcess(boolean yes) {
            insideExitProcess = yes;
            System.out.println("!! insideExitProcess=" + yes + " !!");
        }

        private Node createButton(String iconName, final Runnable action) {
            // загружаем картинку
            final ImageView node = new ImageView(new Image(getClass().getResource(iconName).toString()));

            // создаём анимацию увеличения картинки      
            final ScaleTransition animationGrow = new ScaleTransition(Duration.valueOf("" + DURATION + "ms"), node);
            animationGrow.setToX(SCALE);
            animationGrow.setToY(SCALE);

            // и уменьшения
            final ScaleTransition animationShrink = new ScaleTransition(Duration.valueOf("" + DURATION + "ms"), node);
            animationShrink.setToX(1);
            animationShrink.setToY(1);

            // добавляем эффект отражения
            final Reflection effect = new Reflection();
//        node.setEffect(effect);

            final ColorAdjust effectPressed = ColorAdjustBuilder.create().brightness(-0.5d).build();
            node.setOnMouseReleased(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    // в обработчике нажатия применяем эффект. Тут имеется следующая тонкость: это уже второй эффект для кнопки,
                    // поэтому мы его выставляем не напрямую, а как input для первого эффекта
                    effect.setInput(effectPressed);
                    // создаём Timeline, который через 300 мс отключит затемнение.
                    TimelineBuilder.create().keyFrames(new KeyFrame(Duration.valueOf("300ms"), new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent event) {
                            effect.setInput(null);
                        }
                    })).build().play();
                    action.run();
                }
            });

            // обработчик нажатия мыши
            node.setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    action.run();
                }
            });
            // при наведении курсора мы запускаем анимацию увеличения кнопки
            node.setOnMouseEntered(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    node.toFront();
                    animationShrink.stop();
                    animationGrow.playFromStart();
                }
            });
            // когда курсор сдвигается -- запускаем анимацию уменьшения
            node.setOnMouseExited(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    animationGrow.stop();
                    animationShrink.playFromStart();
                }
            });
            return node;
        }
    }

    /**
     * @return the exchanger
     */
    public static IMessageSender getExchanger() {
        return exchanger;
    }

    /**
     * @param aExchanger the exchanger to set
     */
    public static void setExchanger(IMessageSender aExchanger) {
        exchanger = aExchanger;
    }

    public DashBoard(IMessageSender exch) {
        super("Xcost");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ourInstance = this;
        updateSheetList(exch);
        setVisible(true);
    }

    private void updateSheetList(IMessageSender exch) {
        setExchanger(exch);
        HashSet<String> allSheetNames = new HashSet<String>();
        for (String[] sheetList : new String[][]{DocFrame.sheets(), SitesFrame.sheets(),
            HRFrame.sheets(), ReportsFrame.sheets(), FleetFrame.sheets(), LogisticsFrame.sheets(),
            BankingFrame.sheets(), PartsDashBoard.sheets()}) {
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

    public static void saveProperties() {
        try {
            if (props != null) {
                props.store(new FileOutputStream(PROPERTYFILENAME),
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
            props.setProperty("ServerAddress", props.getProperty("ServerAddress", "localhost:1099"));
        }
        Preferences userPref = Preferences.userRoot();
        saveProperties();
    }

    /**
     *
     */
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

    @Override
    protected void initBackground() {
        XlendWorks.setWindowIcon(this, "Xcost.png");
    }

    @Override
    protected String getBackGroundImage() {
        return BACKGROUNDIMAGE;
    }

    @Override
    public void lowLevelInit() {
        readProperty("junk", ""); // just to init properties
    }

    @Override
    protected void fillControlsPanel() throws HeadlessException {
        JApplet applet = new InternalApplet();
        applet.init();

        setContentPane(applet.getContentPane());

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        applet.start();
    }
}
