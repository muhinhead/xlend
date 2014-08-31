package com.xlend.fx.admin;

import com.xlend.fx.XlendWorksFX;
import com.xlend.fx.util.DbTablePanel;
import com.xlend.fx.util.PopupFXdialog;
import com.xlend.fx.util.Utils;
import com.xlend.orm.Userprofile;
import com.xlend.orm.dbobject.DbObject;
import java.io.IOException;
import java.rmi.RemoteException;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Dialogs;
import javafx.stage.Stage;

/**
 *
 * @author Nick Mukhin
 */
public class AdminTable extends DbTablePanel {

//    class Delta {
//
//        double x, y;
//    }
    private static Stage adminDialogStage;

    public AdminTable(String select) {
        super(select);
    }

    @Override
    protected EventHandler<ActionEvent> getAddAction() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                try {
                    //                Dialogs.DialogResponse result;
                    //                do {
                    //                    AdminEditDialog dlg = new AdminEditDialog("User", "Add user", null);
                    //                    dlg.fillContent((DbObject) null);
                    //                    result = dlg.show();
                    //                } while (result == Dialogs.DialogResponse.YES);
                    //                final BooleanProperty confirmationResult = new SimpleBooleanProperty();
                    PopupFXdialog ad = new PopupFXdialog(AdminTable.class,
                            AdmnUserDialog.instanceStage, "Add new user", "AdminEdit.fxml", "modal-dialog.css");
                    ad.showAndWait();
                } catch (IOException ex) {
                    Utils.logAndShowMessage(ex);
                }
            }
        };
    }

    @Override
    protected EventHandler<ActionEvent> getEditAction() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                Dialogs.DialogResponse result;
                do {
                    AdminEditDialog dlg = new AdminEditDialog("User", "Edit user", null);
                    dlg.fillContent(getCurrentRecord());
                    result = dlg.show();
                } while (result == Dialogs.DialogResponse.YES);
            }
        };
    }

//    @Override
//    protected EventHandler<ActionEvent> getDelAction() {
//        return null;
//    }
    private DbObject getCurrentRecord() {
        DbObject dbObj = null;
        ObservableList<String> row = (ObservableList<String>) getTableView().getSelectionModel().getSelectedItem();
        if (row != null) {
            int id = Integer.parseInt(row.get(0));
            try {
                dbObj = Utils.getExchanger().loadDbObjectOnID(Userprofile.class, id);
            } catch (RemoteException ex) {
                Utils.logAndShowMessage(ex);
            }
        }
        return dbObj;
    }

    @Override
    protected boolean deleteDbObject(Integer id) {
        try {
            Utils.getExchanger().deleteObject(Utils.getExchanger().loadDbObjectOnID(Userprofile.class, id.intValue()));
            return true;
        } catch (RemoteException ex) {
            Utils.logAndShowMessage(ex);
        }
        return false;
    }
}
