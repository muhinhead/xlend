package com.xlend.gui.site;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.Xbreakdown;

/**
 *
 * @author Nick Mukhin
 */
class EditBreakdownDialog extends EditRecordDialog {

    public static boolean okPressed;

    public EditBreakdownDialog(String title, Object obj) {
        super(title, obj);
    }

    @Override
    protected void fillContent() {
        super.fillContent(new EditBreakdownPanel((Xbreakdown) getObject()));
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }
}
