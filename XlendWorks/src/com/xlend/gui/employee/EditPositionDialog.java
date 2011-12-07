package com.xlend.gui.employee;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.Xposition;

/**
 *
 * @author Nick Mukhin
 */
class EditPositionDialog extends EditRecordDialog {

    public static boolean okPressed;

    public EditPositionDialog(String title, Object obj) {
        super(title, obj);
    }

    @Override
    protected void fillContent() {
        super.fillContent(new EditPositionPanel((Xposition) getObject()));
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }
}
