package com.xlend.gui.fleet;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.Xmachine;

/**
 *
 * @author Nick Mukhin
 */
class EditTrackDialog extends EditRecordDialog {

    public static boolean okPressed;

    public EditTrackDialog(String title, Object obj) {
        super(title, obj);
    }

    @Override
    protected void fillContent() {
        super.fillContent(new EditTrackPanel((Xmachine) getObject()));
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }
}
