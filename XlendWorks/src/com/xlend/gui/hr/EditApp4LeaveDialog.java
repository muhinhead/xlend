package com.xlend.gui.hr;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.Xappforleave;
import com.xlend.orm.dbobject.DbObject;

/**
 *
 * @author Nick Mukhin
 */
class EditApp4LeaveDialog extends EditRecordDialog {

    public static boolean okPressed;

    public EditApp4LeaveDialog(String title, DbObject obj) {
        super(title, obj);
    }

    @Override
    protected void fillContent() {
        super.fillContent(new EditApp4LeavePanel((Xappforleave) getObject()));
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }
}
