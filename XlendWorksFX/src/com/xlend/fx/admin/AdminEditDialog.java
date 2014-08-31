package com.xlend.fx.admin;

import com.xlend.fx.util.RecEditDialog;
import com.xlend.orm.dbobject.DbObject;
import javafx.util.Callback;

/**
 *
 * @author Nick Mukhin
 */
public class AdminEditDialog extends RecEditDialog {

    public AdminEditDialog(String title, String header, Callback callback) {
        super(title, header, callback);
    }

    @Override
    public void fillContent(DbObject obj) {
        super.fillContent(new AdminEditPanel(obj));
    }
}
