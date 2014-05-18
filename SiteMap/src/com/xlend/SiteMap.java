package com.xlend;

import com.xlend.fx.util.Utils;
import com.xlend.orm.Xsite;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
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

    public static final String VERSION = "0.4";
    private static final int WIDTH = 900;
    private static final int HEIGHT = 700;
    private static final int VERTICAL = 36;
    private static Connection connection = null;
    private ArrayList<Node> buttons = new ArrayList<Node>();
    private ArrayList<Xsite> sites = new ArrayList<Xsite>();
    private Hashtable<Integer, Rectangle> linesTable = new Hashtable<Integer, Rectangle>();
    private Hashtable<Node, Integer> buttonsTable = new Hashtable<Node, Integer>();
    private static Stage mainStage;

//    static {
//        try {
//            DriverManager.registerDriver(
//                    (java.sql.Driver) Class.forName(Utils.readProperty("dbDriverName", "com.mysql.jdbc.Driver")).newInstance());
//            setConnection(DriverManager.getConnection(
//                    Utils.readProperty("dbConnection", "jdbc:mysql://localhost/xlend"),
//                    Utils.readProperty("dbUser", "jaco"),
//                    Utils.readProperty("dbPassword", "jaco84oliver")));
//            getConnection().setAutoCommit(true);
//            updateDb(false);
//        } catch (Exception ex) {
//            Utils.logAndShowMessage(ex);
//        }
//    }
    private static void updateDb(boolean logErrors) {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("alter table xsite add (x_map int, y_map int)");
            ps.execute();
            Logger.getLogger(SiteMap.class.getName()).log(Level.SEVERE, "table xsite altered");
        } catch (SQLException ex) {
            if (logErrors) {
                Logger.getLogger(SiteMap.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * @return the connection
     */
    public static Connection getConnection() {
        return connection;
    }

    /**
     * @param aConnection the connection to set
     */
    public static void setConnection(Connection aConnection) {
        connection = aConnection;
    }
    private GraphicsContext gc;

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
//        final ArrayList<Node> buttons = new ArrayList<Node>();
        try {
            DriverManager.registerDriver(
                    (java.sql.Driver) Class.forName(Utils.readProperty("dbDriverName", "com.mysql.jdbc.Driver")).newInstance());
            setConnection(DriverManager.getConnection(
                    Utils.readProperty("dbConnection", "jdbc:mysql://localhost/xlend"),
                    Utils.readProperty("dbUser", "jaco"),
                    Utils.readProperty("dbPassword", "jaco84oliver")));
            getConnection().setAutoCommit(true);
            updateDb(false);
        } catch (Exception ex) {
            Utils.logAndShowMessage(ex);
            Platform.exit();
        }
        mainStage = primaryStage;
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.setResizable(false);

        StackPane root = new StackPane();

        loadSites();

        BorderPane border = new BorderPane();
        border.setPadding(new Insets(15, 12, 15, 12));

        FlowPane upperBox = new FlowPane();
        upperBox.setAlignment(Pos.CENTER);
        upperBox.setPadding(new Insets(5, 5, 5, 5));
        upperBox.setVgap(7);
        upperBox.setHgap(7);
        border.setTop(upperBox);

        HBox lowerBox = new HBox(10);
        border.setBottom(lowerBox);

        VBox leftBox = new VBox(5);
        border.setLeft(leftBox);

        VBox rightBox = new VBox(5);
        border.setRight(rightBox);

        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.RED);
        gc.setLineWidth(1);

        root.getChildren().add(canvas);
        root.getChildren().add(border);

        lowerBox.setAlignment(Pos.CENTER_RIGHT);
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
                        site.save();
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
        lowerBox.getChildren().add(clearBtn);
        Button drawBtn = new Button("Show");
        drawBtn.setTooltip(new Tooltip("Show location links"));
        lowerBox.getChildren().add(drawBtn);
        lowerBox.getChildren().add(closeBtn);

        Node siteBtn;
        int i = 0;
        for (; i < (sites.size() - VERTICAL); i++) {
            siteBtn = createLabeledButton("button.png", sites.get(i).getName(), sites.get(i).getXsiteId(), 0);
            upperBox.getChildren().add(siteBtn);
            buttons.add(siteBtn);
        }
//        boolean onLeft = true;
        int middle;
        middle = (sites.size() - i) / 2;
        for (; i < sites.size(); i++) {
            siteBtn = createLabeledButton("button.png", sites.get(i).getName(), sites.get(i).getXsiteId(), -1);
            if (i < middle) {
                leftBox.getChildren().add(siteBtn);
            } else {
                rightBox.getChildren().add(siteBtn);
            }
            buttons.add(siteBtn);
//            onLeft = !onLeft;
        }

        clearBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                gc.clearRect(0, 0, WIDTH, HEIGHT);
            }
        });
        drawBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                redrawLines();
            }
        });

        Scene scene = new Scene(root, WIDTH, HEIGHT);

        primaryStage.setTitle("Sites Map v."+VERSION);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(SiteMap.class.getResource("SiteMap.css").toExternalForm());
        primaryStage.show();
        redrawLines();
    }

    private void redrawLines() {
        gc.clearRect(0, 0, WIDTH, HEIGHT);
        for (Node b : buttons) {
            Integer siteID = buttonsTable.get(b);
            if (siteID != null) {
                Rectangle rect = linesTable.get(siteID);
                rect.setX(b.getLayoutBounds().getMinX() + 20);
                rect.setY(b.getLayoutBounds().getMinY() + 15);
                Point2D r = b.localToScene(rect.getX(), rect.getY());
                gc.strokeLine(r.getX(), r.getY(), rect.getWidth() < 1.0 ? r.getX() : rect.getWidth(),
                        rect.getHeight() < 1.0 ? r.getY() : rect.getHeight());
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
                } else if (t.getEventType() == MouseEvent.MOUSE_RELEASED) {
                    redrawLines();
                }
            }
        };
        btn.setOnMouseDragged(mouseHandler);
        btn.setOnMousePressed(mouseHandler);
        btn.setOnMouseReleased(mouseHandler);
        buttonsTable.put(pane, siteID);
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
            DbObject[] rows = Xsite.load(connection,
                    "exists (select xsite_id from xopmachassing where date_end is null and xsite_id=xsite.xsite_id)",
                    "y_map,x_map");
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
            rows = Xsite.load(connection,
                    "xsite_id not in (" + topList + ") "
                    + "and exists (select xsite_id from xopmachassing where date_end is null and xsite_id=xsite.xsite_id)",
                    "x_map,y_map");
            for (DbObject o : rows) {
                sites.add(site = (Xsite) o);
                linesTable.put(site.getXsiteId(), new Rectangle(0, 0,
                        site.getXMap() == null ? 0 : site.getXMap(),
                        site.getYMap() == null ? 0 : site.getYMap()));
            }
        } catch (SQLException ex) {
            Utils.logAndShowMessage(ex);
//            Logger.getLogger(SiteMap.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
