package com.xlend.gui.fleet;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.Xlowbed;

/**
 *
 * @author Nick Mukhin
 */
class EditLowBedDialog extends EditRecordDialog {

    public static boolean okPressed;

    public EditLowBedDialog(String title, Object obj) {
        super(title, obj);
    }

    @Override
    protected void fillContent() {
        super.fillContent(new EditLowBedPanel((Xlowbed) getObject()));
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }
}
