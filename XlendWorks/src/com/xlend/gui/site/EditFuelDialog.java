package com.xlend.gui.site;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.Xfuel;

/**
 *
 * @author Nick Mukhin
 */
class EditFuelDialog extends EditRecordDialog {

    public static boolean okPressed;

    public EditFuelDialog(String title, Object obj) {
        super(title, obj);
    }

    @Override
    protected void fillContent() {
        super.fillContent(new EditFuelPanel((Xfuel) getObject()));
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }
}
