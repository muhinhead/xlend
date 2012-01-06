package com.xlend.gui.site;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.Xconsume;

/**
 *
 * @author Nick Mukhin
 */
class EditConsumableDialog extends EditRecordDialog {

    public static boolean okPressed;

    public EditConsumableDialog(String title, Object obj) {
        super(title, obj);
    }

    @Override
    protected void fillContent() {
        super.fillContent(new EditConsumablePanel((Xconsume) getObject()));
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }
}
