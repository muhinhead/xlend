package com.xlend;

import com.xlend.fx.util.PopupFXdialog;
import java.io.IOException;
import javafx.stage.Stage;

/**
 *
 * @author Nick Mukhin
 */
public class SiteAssignmentsDialog extends PopupFXdialog {

    public static SiteAssignmentsDialog instance;
    public static Stage instanceStage;
    private Integer siteID = null;

    public SiteAssignmentsDialog(Stage parentStage, String title, String styleSheet, Integer siteID) throws IOException {
        super(SiteAssignmentsDialog.class, parentStage, title, "SiteAssignments.fxml", styleSheet);
        instance = this;
        instanceStage = stage;
        loadData(siteID);
    }

    public void loadData(Integer siteID) {
        this.siteID = siteID;
        if (SiteAssignmentsController.instance != null) {
            SiteAssignmentsController.instance.populateControls(siteID);
        }
    }
}
