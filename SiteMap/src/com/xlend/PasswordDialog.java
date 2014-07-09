package com.xlend;

import com.xlend.fx.util.PopupFXdialog;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

/**
 *
 * @author Nick Mukhin
 */
public class PasswordDialog extends PopupFXdialog {
    public static PasswordDialog instance;
    public static Stage instanceStage;

    public PasswordDialog(Stage parentStage, String title, String styleSheet) throws IOException {
        super(PasswordDialog.class, parentStage, title, "PasswordDialog.fxml", styleSheet);
        instance = this;
        instanceStage = stage;
    }
}
