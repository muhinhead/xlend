/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xlend.fx.admin;

import com.xlend.constants.Selects;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;

/**
 * FXML Controller class
 *
 * @author Nick Mukhin
 */
public class AdminUserDialogController implements Initializable {

    @FXML
    Tab userTab;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        userTab.setContent(new AdminTable(Selects.SELECT_FROM_USERS));
    }    
}
