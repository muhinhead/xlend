package com.xlend.fx.admin;

import com.xlend.fx.util.PopupFXdialog;
import java.io.IOException;
import javafx.stage.Stage;

/**
 *
 * @author Nick Mukhin
 */
public class AdmnUserDialog extends PopupFXdialog {

    public static Stage instanceStage;
    
    public AdmnUserDialog(Stage parentStage, String styleSheet) throws IOException {
        super(AdmnUserDialog.class, parentStage, "Admin's console", "AdminUserDialog.fxml", styleSheet);
        instanceStage = stage;
    }
}
