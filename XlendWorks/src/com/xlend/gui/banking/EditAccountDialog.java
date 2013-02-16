package com.xlend.gui.banking;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.Xaccounts;
import com.xlend.orm.dbobject.DbObject;

/**
 *
 * @author Nick Mukhin
 */
public class EditAccountDialog extends EditRecordDialog {

    public static boolean okPressed;

    public EditAccountDialog(String title, DbObject obj) {
        super(title, obj);
    }

    @Override
    protected void fillContent() {
        super.fillContent(new EditAccountPanel((Xaccounts) getObject()));
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }
}
