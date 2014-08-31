package com.xlend;

import com.xlend.fx.util.Utils;
import com.xlend.orm.Userprofile;
import com.xlend.orm.Xsite;
import com.xlend.orm.dbobject.DbObject;
import java.net.URL;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Dialogs;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Nick Mukhin
 */
public class PasswordDialogController implements Initializable {

    public static boolean okPressed = false;
    @FXML
    private PasswordField passwordField;
    static PasswordDialogController instance;
    @FXML
    private Label messageLabel;
    @FXML
    private HBox actionParent;
    @FXML
    private Button okButton;
    @FXML
    private Button cancelButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        instance = this;
    }

    @FXML
    public void handleCancelAction(ActionEvent event) {
        okPressed = false;
        PasswordDialog.instanceStage.close();
    }

    @FXML
    public void handleOkAction(ActionEvent event) {
        String password = passwordField.getText().trim();
        if (password.length() > 0) {
            try {
                DbObject[] recs = SiteMap.getExchanger().getDbObjects(Userprofile.class, "pwdmd5='" + password + "'", null);
                if (recs.length > 0) {
                    for (DbObject rec : recs) {
                        Userprofile user = (Userprofile) rec;
                        if (user.getSupervisor() != null && user.getSupervisor() != 0) {
                            okPressed = true;
                            break;
                        }
                    }
                }
                if (!okPressed) {
                    Dialogs.showErrorDialog(SiteMap.getMainStage(), "The operation is not allowed");
                }
                PasswordDialog.instanceStage.close();
            } catch (RemoteException ex) {
                Logger.getLogger(PasswordDialogController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
