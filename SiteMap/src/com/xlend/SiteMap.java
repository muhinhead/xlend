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
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Nick Mukhin
 */
public class SiteMap extends Application {

    public static final String VERSION = "0.7";
    private static final int WIDTH = 900;
    private static final int HEIGHT = 700;
    private static final int VERTICAL = 32;
//    private static Connection connection = null;
    public static String protocol;
    private ArrayList<Node> buttons = new ArrayList<Node>();
    private ArrayList<Xsite> sites = new ArrayList<Xsite>();
    private Hashtable<Integer, Rectangle> linesTable = new Hashtable<Integer, Rectangle>();
    private Hashtable<Node, Integer> buttonsPaneTable = new Hashtable<Node, Integer>();
    private Hashtable<Integer, Node> buttonTable = new Hashtable<Integer, Node>();
    private static Stage mainStage;
    private static IMessageSender exchanger;
    public static final String defaultServerIP = "192.168.1.3";

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

    private class SiteInfoRunnable implements Runnable {

        private Integer siteID;

        public SiteInfoRunnable(Integer siteID) {
            super();
            this.siteID = siteID;
        }

        @Override
        public void run() {
            try {
                SiteAssignmentsDialog sa = SiteAssignmentsDialog.instance;
                if (sa == null) {
                    sa = new SiteAssignmentsDialog(mainStage,
                            "Site info", "modal-dialog.css", siteID);
                } else {
                    SiteAssignmentsDialog.instanceStage.setTitle("Site info");
                    sa.loadData(siteID);
                }
                sa.showAndWait();
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
//        gc = canvas.getGraphicsContext2D();
//        gc.setStroke(Color.RED);
//        gc.setLineWidth(1);

        root.getChildren().add(canvas);


        Button closeBtn = new Button("Close");
        closeBtn.setTooltip(new Tooltip("Save links and exit"));
        closeBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                for (Xsite site : sites) {
                    try {
                        Rectangle rect = linesTable.get(site.getXsiteId());
                        site.setXMap((int) rect.getWidth());
                        site.setYMap((int) rect.getHeight());
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
                        Utils.saveProperties();
                    } catch (Exception ex) {
                        Logger.getLogger(SiteMap.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                Platform.exit();
            }
        });
        Button clearBtn = new Button("Hide");
        clearBtn.setTooltip(new Tooltip("Hide location links"));

        Button drawBtn = new Button("Show");
        drawBtn.setTooltip(new Tooltip("Show location links"));

        clearBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                gc.clearRect(0, 0, WIDTH, HEIGHT);
            }
        });
        drawBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {

//                Node b = buttons.get(0);
//                final Point2D windowCoord = new Point2D(scene.getWindow().getX(), scene.getWindow().getY());
//                final Point2D sceneCoord = new Point2D(scene.getX(), scene.getY());
//                final Point2D nodeCoord = b.localToScene(0.0, 0.0);
//                System.out.println("!!windowCoord:" + windowCoord.getX() + "/" + windowCoord.getY());
//                System.out.println("!!sceneCoord:" + sceneCoord.getX() + "/" + sceneCoord.getY());
//                System.out.println("!!nodeCoord:" + nodeCoord.getX() + "/" + nodeCoord.getY());

                redrawLines();
            }
        });

        Pane centerPane = new Pane();
        clearBtn.setLayoutX(700);
        clearBtn.setLayoutY(660);
        centerPane.getChildren().add(clearBtn);
        drawBtn.setLayoutX(760);
        drawBtn.setLayoutY(660);
        centerPane.getChildren().add(drawBtn);
        closeBtn.setLayoutX(820);
        closeBtn.setLayoutY(660);
        centerPane.getChildren().add(closeBtn);

        canvas.widthProperty().bind(root.widthProperty());
        canvas.heightProperty().bind(root.heightProperty());

        Node siteBtn;
        int sitesQty = sites.size();
        double angleStep = 2 * Math.PI / sitesQty;
        double radius = 320; //TODO: calculate based on window size
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
//            r = linesTable.get(sites.get(i).getPK_ID());
//            deltaX = radius * Math.cos(curAngle);
//            deltaY = radius * Math.sin(curAngle);
            siteBtn = createLabeledButton("button.png", sites.get(i).getName(), sites.get(i).getXsiteId(), 0);
            r = linesTable.get(sites.get(i).getPK_ID());
            double distance = 9999999.0;
            int p = 0;
            for (int j = 0; j < pointsOnCircle.size(); j++) {
                Point2D start = pointsOnCircle.get(j);
                Point2D end = new Point2D(r.getWidth(), r.getHeight());
                double d = Math.sqrt(Math.pow(start.getX() - end.getX(), 2) + Math.pow(start.getY() - end.getY(), 2));
                if (d < distance) {
                    distance = d;
                    p = j;
                }
            }
            r.setLayoutX(pointsOnCircle.get(p).getX());
            r.setLayoutY(pointsOnCircle.get(p).getY());
            pointsOnCircle.remove(p);
            linesTable.put(sites.get(i).getPK_ID(), r);
            siteBtn.setLayoutX(r.getLayoutX()+20);
            siteBtn.setLayoutY(r.getLayoutY()+15);
            

//            r.setLayoutX(centerX + deltaX + 20);
//            r.setLayoutY(centerY + deltaY + 15);
//            linesTable.put(sites.get(i).getPK_ID(), r);
//            
//            
//            siteBtn.setLayoutX(centerX + deltaX);
//            siteBtn.setLayoutY(centerY + deltaY);

            centerPane.getChildren().add(siteBtn);
            buttons.add(siteBtn);
        }

//        border.setCenter(centerPane);
        root.getChildren().add(centerPane);

        scene = new Scene(root, WIDTH, HEIGHT);

        primaryStage.setTitle("Sites Map v." + VERSION + " (" + protocol + ")");
        primaryStage.setScene(scene);
        scene.getStylesheets().add(SiteMap.class.getResource("SiteMap.css").toExternalForm());

        primaryStage.show();
        redrawLines();
    }

    private void redrawLines() {
        gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.RED);
        gc.setLineWidth(1);
        gc.clearRect(0, 0, WIDTH, HEIGHT);
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
        Tooltip t = new Tooltip(lbl);
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
            @Override
            public void handle(MouseEvent t) {
                Rectangle rect = linesTable.get(siteID);
                if (t.getEventType() == MouseEvent.MOUSE_PRESSED) {
                } else if (t.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                    rect.setWidth(t.getSceneX());
                    rect.setHeight(t.getSceneY());
                    linesTable.put(siteID, rect);
                } else if (t.getEventType() == MouseEvent.MOUSE_RELEASED) {
                    redrawLines();
                }
            }
        };
        btn.setOnMouseDragged(mouseHandler);
        btn.setOnMousePressed(mouseHandler);
        btn.setOnMouseReleased(mouseHandler);
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

    private void loadSites() {
        try {
            String topList = "-1";
            DbObject[] rows = getExchanger().getDbObjects(Xsite.class,
                    "not is_active "
                    + "and exists (select xsite_id from xopmachassing where date_end is null and xsite_id=xsite.xsite_id)", "y_map,x_map");
            int forTop = rows.length - VERTICAL;
            Xsite site;
            for (int i = 0; i < forTop; i++) {
                site = (Xsite) rows[i];
                topList += ("," + site.getXsiteId());
                sites.add(site);
                linesTable.put(site.getXsiteId(), new Rectangle(0, 0,
                        site.getXMap() == null ? 0 : site.getXMap(),
                        site.getYMap() == null ? 0 : site.getYMap()));
            }
            rows = getExchanger().getDbObjects(Xsite.class,
                    "xsite_id not in (" + topList + ") "
                    + "and exists (select xsite_id from xopmachassing where date_end is null and xsite_id=xsite.xsite_id)",
                    "x_map,y_map");
            for (DbObject o : rows) {
                sites.add(site = (Xsite) o);
                linesTable.put(site.getXsiteId(), new Rectangle(0, 0,
                        site.getXMap() == null ? 0 : site.getXMap(),
                        site.getYMap() == null ? 0 : site.getYMap()));
            }
        } catch (Exception ex) {
            Utils.logAndShowMessage(ex);
//            Logger.getLogger(SiteMap.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
