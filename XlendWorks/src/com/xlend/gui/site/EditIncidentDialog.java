package com.xlend.gui.site;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.Xincidents;

/**
 *
 * @author Nick Mukhin
 */
class EditIncidentDialog extends EditRecordDialog {

    public static boolean okPressed;

    public EditIncidentDialog(String title, Object obj) {
        super(title, obj);
    }

    @Override
    protected void fillContent() {
        super.fillContent(new EditIncidentPanel((Xincidents) getObject()));
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }
}
