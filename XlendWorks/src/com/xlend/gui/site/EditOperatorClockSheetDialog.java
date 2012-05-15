package com.xlend.gui.site;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.Xopclocksheet;
import com.xlend.orm.dbobject.DbObject;

/**
 *
 * @author Nick Mukhin
 */
class EditOperatorClockSheetDialog extends EditRecordDialog {
    public static boolean okPressed;
    
    public EditOperatorClockSheetDialog(String title, DbObject obj) {
        super(title, obj);
    }
    
    @Override
    protected void fillContent() {
        super.fillContent(new EditOperatorClockSheetPanel((Xopclocksheet) getObject()));
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }
}
