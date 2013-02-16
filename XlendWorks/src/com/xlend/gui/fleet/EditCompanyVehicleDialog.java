package com.xlend.gui.fleet;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.Xmachine;

/**
 *
 * @author Nick Mukhin
 */
public class EditCompanyVehicleDialog extends EditRecordDialog {

    public static boolean okPressed;

    public EditCompanyVehicleDialog(String title, Object obj) {
        super(title, obj);
    }

    @Override
    protected void fillContent() {
        super.fillContent(new EditCompanyVehiclePanel((Xmachine) getObject()));
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }
}
