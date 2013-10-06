package com.xlend.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author Nick Mukhin
 */
public class FXloginController implements Initializable, ControlledScreen {

    private ScreensController myController;
    @FXML
    private HBox btnHbox;
    @FXML
    private ComboBox loginComboBox;
    @FXML
    private PasswordField pwdField;
    
    private Node loginButtonNode;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loginComboBox.getItems().clear();
        loginComboBox.getItems().addAll(XlendWorks.loadAllLogins());
        loginButtonNode = FXutils.createButton(getClass(), "/LoginFX.png", new Runnable() {
            @Override
            public void run() {
//                DashBoard.showAdmin();
                if (myController.getScreen("FXdashboard") == null) {
                    myController.loadScreen("FXdashboard", "FXdashboard.fxml");
                }
                myController.setScreen("FXdashboard");
            }
        });
        btnHbox.getChildren().add(loginButtonNode);
    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        this.myController = screenParent;
    }
}
