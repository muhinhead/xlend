package com.xlend.gui.parts;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.Xbookouts;
import com.xlend.orm.dbobject.DbObject;

/**
 *
 * @author Nick Mukhin
 */
class EditXbookOutDialog extends EditRecordDialog {

    public static boolean okPressed;

    EditXbookOutDialog(String title, DbObject obj) {
        super(title, obj);
    }

    @Override
    protected void fillContent() {
        super.fillContent(new EditXbookOutPanel((DbObject) getObject()));
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }
}
