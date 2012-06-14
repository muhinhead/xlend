package com.xlend.gui.admin;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.Xpaidmethod;
import com.xlend.orm.dbobject.DbObject;

/**
 *
 * @author Nick Mukhin
 */
class EditPayMethodDialog extends EditRecordDialog {
    public static boolean okPressed;
    
    public EditPayMethodDialog(String title, DbObject obj) {
        super(title, obj);
    }

    @Override
    protected void fillContent() {
        super.fillContent(new EditPayMethodPanel((Xpaidmethod) getObject()));
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }
}
