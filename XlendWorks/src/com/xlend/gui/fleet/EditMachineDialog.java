package com.xlend.gui.fleet;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.Xmachine;

/**
 *
 * @author Nick Mukhin
 */
class EditMachineDialog extends EditRecordDialog {

    public static boolean okPressed;

    public EditMachineDialog(String title, Object obj) {
        super(title, obj);
    }

    @Override
    protected void fillContent() {
        super.fillContent(new EditMachinePanel((Xmachine) getObject()));
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }
}
