package com.xlend.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Администратор
 */
public class FXdashboardController implements Initializable, ControlledScreen {

    private ScreensController myController;
    @FXML
    private BorderPane upperPane;
    @FXML
    private HBox taskBar1;
    @FXML
    private VBox taskBar;
    @FXML
    private HBox taskBar2;
    @FXML
    private Label userLogin;
    private static final String[] icons = new String[]{
        "Docs.png", "HR.png", "Parts.png", "Sites.png",
        "Fleet.png", "Banking.png", "Reports.png", "Logistics.png",
        "Fuel.png",
        "AdminFX.png", "ExitFX.png"
    };
    private Node adminButtonNode;
    private Node logoutButtonNode;
    private Node docsButtonNode;
    private Node hrButtonNode;
    private Node partsButtonNode;
    private Node sitesButtonNode;
    private Node fleetButtonNode;
    private Node bankingButtonNode;
    private Node reportsButtonNode;
    private Node logisticsButtonNode;
    private Node fuelButtonNode;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        userLogin.setText("Hi " + XlendWorks.getCurrentUserLogin());

        adminButtonNode = FXutils.createButton(getClass(), "/" + icons[icons.length - 2], new Runnable() {
            @Override
            public void run() {
                DashBoard.showAdmin();
            }
        });
        upperPane.setLeft(adminButtonNode);
        adminButtonNode.setVisible(XlendWorks.isCurrentAdmin());
        logoutButtonNode = FXutils.createButton(getClass(), "/" + icons[icons.length - 1], new Runnable() {
            @Override
            public void run() {
                //TODO: here should be switching to login screen
                DashBoard.ourInstance.exit();
//                myController.setScreen("FXlogin");
            }
        });
        upperPane.setRight(logoutButtonNode);

        docsButtonNode = FXutils.createButton(getClass(), "/" + icons[0], new Runnable() {
            @Override
            public void run() {
                DashBoard.showDocs();
            }
        });

        hrButtonNode = FXutils.createButton(getClass(), "/" + icons[1], new Runnable() {
            @Override
            public void run() {
                DashBoard.showHR();
            }
        });

        partsButtonNode = FXutils.createButton(getClass(), "/" + icons[2], new Runnable() {
            @Override
            public void run() {
                DashBoard.showParts();
            }
        });

        sitesButtonNode = FXutils.createButton(getClass(), "/" + icons[3], new Runnable() {
            @Override
            public void run() {
                DashBoard.showSites();
            }
        });

        fleetButtonNode = FXutils.createButton(getClass(), "/" + icons[4], new Runnable() {
            @Override
            public void run() {
                DashBoard.showFleet();
            }
        });

        bankingButtonNode = FXutils.createButton(getClass(), "/" + icons[5], new Runnable() {
            @Override
            public void run() {
                DashBoard.showBanking();
            }
        });

        reportsButtonNode = FXutils.createButton(getClass(), "/" + icons[6], new Runnable() {
            @Override
            public void run() {
                DashBoard.showReports();
            }
        });

        logisticsButtonNode = FXutils.createButton(getClass(), "/" + icons[7], new Runnable() {
            @Override
            public void run() {
                DashBoard.showLogistics();
            }
        });
        
        fuelButtonNode = FXutils.createButton(getClass(), "/" + icons[8], new Runnable() {
            @Override
            public void run() {
                DashBoard.showFuel();
            }
        });

        taskBar1.getChildren().add(docsButtonNode);
        taskBar1.getChildren().add(hrButtonNode);
        taskBar1.getChildren().add(partsButtonNode);
        taskBar1.getChildren().add(sitesButtonNode);
        taskBar1.getChildren().add(fleetButtonNode);

        taskBar2.getChildren().add(bankingButtonNode);
        taskBar2.getChildren().add(reportsButtonNode);
        taskBar2.getChildren().add(logisticsButtonNode);
        taskBar2.getChildren().add(fuelButtonNode);
    }

    /**
     *
     * @param screenParent
     */
    @Override
    public void setScreenParent(ScreensController screenParent) {
        this.myController = screenParent;
    }
}
