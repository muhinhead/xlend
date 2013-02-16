package com.xlend.gui.assign;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.dbobject.DbObject;

/**
 *
 * @author Nick Mukhin
 */
class EditSiteAssignmentDialog extends EditRecordDialog {

    public static boolean okPressed;

    public EditSiteAssignmentDialog(String title, DbObject obj) {
        super(title, obj);
    }

    @Override
    protected void fillContent() {
        super.fillContent(new EditSiteAssignmentPanel((DbObject) getObject()));
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }
}
