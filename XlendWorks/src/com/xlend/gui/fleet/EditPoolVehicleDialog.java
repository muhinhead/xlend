package com.xlend.gui.fleet;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.Xmachine;

/**
 *
 * @author Nick Mukhin
 */
public class EditPoolVehicleDialog extends EditRecordDialog {

    public static boolean okPressed;

    public EditPoolVehicleDialog(String title, Object obj) {
        super(title, obj);
    }

    @Override
    protected void fillContent() {
        super.fillContent(new EditPoolVehiclePanel((Xmachine) getObject()));
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }
}
