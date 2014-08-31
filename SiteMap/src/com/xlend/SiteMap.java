package com.xlend;

import com.xlend.fx.util.Utils;
import com.xlend.orm.Xsite;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.remote.IMessageSender;
import com.xlend.rmi.ExchangeFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Dialogs;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 *
 * @author Nick Mukhin
 */
public class SiteMap extends Application {

    public static final String VERSION = "0.9";
//    public static Integer movingButton = null;
    private static final int WIDTH = 900;
    private static final int HEIGHT = 700;
    private static final int VERTICAL = 32;
//    private static Connection connection = null;
    public static String protocol;

    /**
     * @return the mainStage
     */
    public static Stage getMainStage() {
        return mainStage;
    }
    private ArrayList<Node> buttons = new ArrayList<Node>();
    private ArrayList<Xsite> sites = new ArrayList<Xsite>();
    private Hashtable<Integer, Rectangle> linesTable = new Hashtable<Integer, Rectangle>();
    private Hashtable<Node, Integer> buttonsPaneTable = new Hashtable<Node, Integer>();
    private Hashtable<Integer, Node> buttonTable = new Hashtable<Integer, Node>();
    private static Stage mainStage;
    private static IMessageSender exchanger;
    public static final String defaultServerIP = "localhost";//"192.168.1.3";
    private static boolean wasDragged = false;

    /**
     * @return the exchanger
     */
    public static IMessageSender getExchanger() {
        return exchanger;
    }

    /**
     * @param aExchanger the exchanger to set
     */
    public static void setExchanger(IMessageSender aExchanger) throws Exception {
        if (aExchanger == null) {
            throw new Exception("Data exchanger creation fails");
        }
        exchanger = aExchanger;
    }

    static {
        try {
            String serverIP = Utils.readProperty("ServerAddress", defaultServerIP);
            IMessageSender exc = ExchangeFactory.getExchanger("rmi://" + serverIP + "/XlendServer", Utils.getProperties());
            if (exc == null) {
                exc = ExchangeFactory.getExchanger(Utils.readProperty("JDBCconnection", "jdbc:mysql://"
                        + defaultServerIP
                        + "/xlend"),
                        Utils.getProperties());
            }
            if (exc == null) {
                Utils.logAndShowMessage("");
            } else {
                setExchanger(exc);
            }
        } catch (Exception ex) {
            Utils.logAndShowMessage(ex);
        }
    }
    private GraphicsContext gc;
    private Scene scene;
    private Canvas canvas;
//    private RadioButton rb1;
//    private RadioButton rb2;

    private class SiteInfoRunnable implements Runnable {

        private Integer siteID;

        public SiteInfoRunnable(Integer siteID) {
            super();
            this.siteID = siteID;
        }

        @Override
        public void run() {
            try {
                if (!wasDragged) {
                    SiteAssignmentsDialog sa = SiteAssignmentsDialog.instance;
                    if (sa == null) {
                        sa = new SiteAssignmentsDialog(getMainStage(),
                                "Site info", "modal-dialog.css", siteID);
                    } else {
                        SiteAssignmentsDialog.instanceStage.setTitle("Site info");
                        sa.loadData(siteID);
                    }
                    sa.showAndWait();
                }
                wasDragged = false;
            } catch (IOException ex) {
                Utils.logAndShowMessage(ex);
            }
        }
    }

    @Override
    public void start(Stage primaryStage) {
        mainStage = primaryStage;
        primaryStage.initStyle(StageStyle.DECORATED);

        StackPane root = new StackPane();

        loadSites();

        canvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(canvas);

        mainStage.setOnHiding(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent event) {
                saveAll();
            }
        });

        Button closeBtn = new Button("Close");
        closeBtn.setTooltip(new Tooltip("Save links and exit"));
        closeBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                saveAll();
//                for (Xsite site : sites) {
//                    try {
//                        Rectangle rect = linesTable.get(site.getXsiteId());
//                        if (rect.getWidth() > 0 || rect.getHeight() > 0) {
//                            site.setXMap((int) rect.getWidth() + (int) (rect.getX() - 20) * 10000);
//                            site.setYMap((int) rect.getHeight() + (int) (rect.getY() - 15) * 10000);
//                        }
//                        if (site.getXorderId() != null && site.getXorderId().intValue() == 0) {
//                            site.setXorderId(null);
//                        }
//                        if (site.getXorder2Id() != null && site.getXorder2Id().intValue() == 0) {
//                            site.setXorder2Id(null);
//                        }
//                        if (site.getXorder3Id() != null && site.getXorder3Id().intValue() == 0) {
//                            site.setXorder3Id(null);
//                        }
//                        getExchanger().saveDbObject(site);
//                    } catch (Exception ex) {
//                        Logger.getLogger(SiteMap.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//                Utils.saveProperties();
                Platform.exit();
            }
        });
        Button clearAllBtn = new Button("Clear");
        clearAllBtn.setTooltip(new Tooltip("Clear all location links"));

        Button hideBtn = new Button("Hide");
        hideBtn.setTooltip(new Tooltip("Hide location links"));

        Button drawBtn = new Button("Show");
        drawBtn.setTooltip(new Tooltip("Show location links"));

        clearAllBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                if (checkAdminsPermission()) {
                    gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                    for (Xsite site : sites) {
                        try {
                            Rectangle rect = linesTable.get(site.getXsiteId());
                            site.setXMap(0);
                            site.setYMap(0);
                            rect.setWidth(0);
                            rect.setHeight(0);
                        } catch (Exception ex) {
                            Logger.getLogger(SiteMap.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        });

        hideBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            }
        });
        drawBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                redrawLines();
            }
        });

        Pane centerPane = new Pane();
        clearAllBtn.setLayoutX(10);
        clearAllBtn.setLayoutY(10);
        centerPane.getChildren().add(clearAllBtn);
        hideBtn.setLayoutX(70);
        hideBtn.setLayoutY(10);
        centerPane.getChildren().add(hideBtn);
        drawBtn.setLayoutX(130);
        drawBtn.setLayoutY(10);
        centerPane.getChildren().add(drawBtn);
        closeBtn.setLayoutX(190);
        closeBtn.setLayoutY(10);
        centerPane.getChildren().add(closeBtn);

        canvas.widthProperty().bind(root.widthProperty());
        canvas.heightProperty().bind(root.heightProperty());

        Node siteBtn;
        int sitesQty = sites.size();
        double angleStep = 2 * Math.PI / sitesQty;
        double radius = 320;
        int centerX = 420;
        int centerY = 315;
        double curAngle = 0.0;
        double deltaX, deltaY;
        Rectangle r;

        ArrayList<Point2D> pointsOnCircle = new ArrayList<Point2D>();
        for (int i = 0; i < sitesQty; i++, curAngle += angleStep) {
            deltaX = radius * Math.cos(curAngle);
            deltaY = radius * Math.sin(curAngle);
            pointsOnCircle.add(new Point2D(centerX + deltaX, centerY + deltaY));
        }

        for (int i = 0; i < sitesQty; i++) {
            siteBtn = createLabeledButton("button.png", sites.get(i).getName(), sites.get(i).getXsiteId(), 0);
            r = linesTable.get(sites.get(i).getPK_ID());
            Point2D end = new Point2D(r.getWidth(), r.getHeight());
            int p = 0;
            if (r.getX() < 1 && r.getY() < 1 && (end.getX() > 1 || end.getY() > 1)) {
                double distance = 9999999.0;
                for (int j = 0; j < pointsOnCircle.size(); j++) {
                    Point2D start = pointsOnCircle.get(j);
                    double d = Math.sqrt(Math.pow(start.getX() - end.getX(), 2) + Math.pow(start.getY() - end.getY(), 2));
                    if (d > 1 && d < distance) {
                        distance = d;
                        p = j;
                    }
                }
            }
            if (r.getX() < 1 && r.getY() < 1) {
                r.setLayoutX(pointsOnCircle.get(p).getX());
                r.setLayoutY(pointsOnCircle.get(p).getY());
                pointsOnCircle.remove(p);
            } else {
                r.setLayoutX(r.getX() - 20);
                r.setLayoutY(r.getY() - 15);
            }
            linesTable.put(sites.get(i).getPK_ID(), r);
            siteBtn.setLayoutX(r.getLayoutX() + 20);
            siteBtn.setLayoutY(r.getLayoutY() + 15);
            centerPane.getChildren().add(siteBtn);
            buttons.add(siteBtn);
        }
        root.getChildren().add(centerPane);

        scene = new Scene(root, WIDTH, HEIGHT);

        primaryStage.setTitle("Sites Map v." + VERSION + " (" + protocol + ")");
        primaryStage.setScene(scene);
        scene.getStylesheets().add(SiteMap.class.getResource("SiteMap.css").toExternalForm());

        primaryStage.show();
        redrawLines();
    }

    private void saveAll() {
        for (Xsite site : sites) {
            try {
                Rectangle rect = linesTable.get(site.getXsiteId());
                if (rect.getWidth() > 0 || rect.getHeight() > 0) {
                    site.setXMap((int) rect.getWidth() + (int) (rect.getX() - 20) * 10000);
                    site.setYMap((int) rect.getHeight() + (int) (rect.getY() - 15) * 10000);
                }
                if (site.getXorderId() != null && site.getXorderId().intValue() == 0) {
                    site.setXorderId(null);
                }
                if (site.getXorder2Id() != null && site.getXorder2Id().intValue() == 0) {
                    site.setXorder2Id(null);
                }
                if (site.getXorder3Id() != null && site.getXorder3Id().intValue() == 0) {
                    site.setXorder3Id(null);
                }
                getExchanger().saveDbObject(site);
            } catch (Exception ex) {
                Logger.getLogger(SiteMap.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Utils.saveProperties();
    }

    private void redrawLines() {
        gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.RED);
        gc.setLineWidth(1);
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (Node b : buttons) {

            Integer siteID = buttonsPaneTable.get(b);
            if (siteID != null) {
                Rectangle rect = linesTable.get(siteID);
                Point2D nodeCoord = b.localToScene(0.0, 0.0);
                rect.setX(nodeCoord.getX() + 20);
                rect.setY(nodeCoord.getY() + 15);
                if (rect.getWidth() > 0) {
                    gc.strokeLine(rect.getX(),
                            rect.getY(),
                            rect.getWidth(),
                            rect.getHeight());
                }
            }
        }
    }

    private Node createLabeledButton(String imgFile, String lbl, final Integer siteID, int leftRight) {
        Node btn = FXutils.createButton(getClass(), imgFile, new SiteInfoRunnable(siteID), false);
        Tooltip t = new Tooltip(lbl + "\npress Shift to draw a line");
        Tooltip.install(btn, t);
        Node pane;
        Label label = new Label(lbl);
        label.setTextFill(Color.web("#AAAAAA"));
        if (leftRight == 0) {
            label.setAlignment(Pos.CENTER);
            pane = new VBox();
            ((VBox) pane).getChildren().add(btn);
            ((VBox) pane).getChildren().add(label);
        } else {
            pane = new HBox();
            if (leftRight < 0) {
                ((HBox) pane).getChildren().add(btn);
            }
            ((HBox) pane).getChildren().add(label);
            if (leftRight > 0) {
                ((HBox) pane).getChildren().add(btn);
                label.setAlignment(Pos.CENTER_RIGHT);
            }
        }

        EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {
            private double x = 0;
            private double y = 0;
            private double mousex = 0;
            private double mousey = 0;

            @Override
            public void handle(MouseEvent event) {
                Rectangle rect = linesTable.get(siteID);
                if (event.isShiftDown()) {
                    if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
                    } else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                        rect.setWidth(event.getSceneX());
                        rect.setHeight(event.getSceneY());
                        linesTable.put(siteID, rect);
                    } else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
                        redrawLines();
                    }
                } else {
                    Node b = null;
                    for (Node pane : buttons) {
                        if (siteID.equals(buttonsPaneTable.get(pane))) {
                            b = pane;
                            break;
                        }
                    }
                    if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
                        mousex = event.getSceneX();
                        mousey = event.getSceneY();
                        x = b.getLayoutX();
                        y = b.getLayoutY();
                    } else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                        rect.setX(event.getSceneX());
                        rect.setY(event.getSceneY());
                        linesTable.put(siteID, rect);
                        x += event.getSceneX() - mousex;
                        y += event.getSceneY() - mousey;
                        b.setLayoutX(x);
                        b.setLayoutY(y);
                        mousex = event.getSceneX();
                        mousey = event.getSceneY();
                        redrawLines();
                        wasDragged = true;
                    }
                }
            }
        };
        pane.setOnMouseDragged(mouseHandler);
        pane.setOnMousePressed(mouseHandler);
        pane.setOnMouseReleased(mouseHandler);

        buttonsPaneTable.put(pane, siteID);
        buttonTable.put(siteID, btn);
        return pane;
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private boolean checkAdminsPermission() {
        boolean ok = false;
        PasswordDialog pwdDialog = PasswordDialog.instance;
        try {
            if (pwdDialog == null) {
                pwdDialog = new PasswordDialog(getMainStage(), "Admin's password", "modal-dialog.css");
            }
            pwdDialog.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(SiteMap.class.getName()).log(Level.SEVERE, null, ex);
        }
        return PasswordDialogController.okPressed;
    }

    private void loadSites() {
        try {
            String topList = "-1";
            Xsite site;
            DbObject[] rows = getExchanger().getDbObjects(Xsite.class,
                    "ifnull(x_map,0)>0 and is_active and exists (select xsite_id from xopmachassing where date_end is null and xsite_id=xsite.xsite_id)",
                    null);
            Rectangle r = null;
            for (DbObject o : rows) {
                sites.add(site = (Xsite) o);
                topList += ("," + site.getPK_ID());
                linesTable.put(site.getXsiteId(), r = new Rectangle(
                        site.getXMap() == null ? 0 : site.getXMap() > 10000 ? site.getXMap() / 10000 : site.getXMap(),
                        site.getYMap() == null ? 0 : site.getYMap() > 10000 ? site.getYMap() / 10000 : site.getYMap(),
                        site.getXMap() == null ? 0 : site.getXMap() > 10000 ? site.getXMap() % 10000 : site.getXMap(),
                        site.getYMap() == null ? 0 : site.getYMap() > 10000 ? site.getYMap() % 10000 : site.getYMap()));
            }
            rows = getExchanger().getDbObjects(Xsite.class,
                    "ifnull(x_map,0)=0 and is_active and exists (select xsite_id from xopmachassing where date_end is null and xsite_id=xsite.xsite_id) and "
                    + "xsite_id not in (" + topList + ")",
                    null);
            for (DbObject o : rows) {
                sites.add(site = (Xsite) o);
                linesTable.put(site.getXsiteId(), r = new Rectangle(
                        site.getXMap() == null ? 0 : site.getXMap() > 10000 ? site.getXMap() / 10000 : site.getXMap(),
                        site.getYMap() == null ? 0 : site.getYMap() > 10000 ? site.getYMap() / 10000 : site.getYMap(),
                        site.getXMap() == null ? 0 : site.getXMap() > 10000 ? site.getXMap() % 10000 : site.getXMap(),
                        site.getYMap() == null ? 0 : site.getYMap() > 10000 ? site.getYMap() % 10000 : site.getYMap()));
            }
        } catch (Exception ex) {
            Utils.logAndShowMessage(ex);
        }
    }
}
