package com.xlend.gui.hr;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.Xwagesum;
import com.xlend.orm.dbobject.DbObject;

/**
 *
 * @author Nick Mukhin
 */
public class EditWagesDialog extends EditRecordDialog {

    public static boolean okPressed;

    public EditWagesDialog(String title, DbObject obj) {
        super(title, obj);
    }

    @Override
    protected void fillContent() {
        super.fillContent(new EditWagesPanel((Xwagesum) getObject()));
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }
}
