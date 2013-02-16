package com.xlend.gui.fleet;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.Xmachrentalrate;
import com.xlend.orm.dbobject.DbObject;

/**
 *
 * @author Nick Mukhin
 */
public class EditMachineRentalRatesDialog extends EditRecordDialog {

    public static boolean okPressed;

    public EditMachineRentalRatesDialog(String title, DbObject obj) {
        super(title, obj);
    }

    @Override
    protected void fillContent() {
        super.fillContent(new EditMachineRentalRatesPanel((Xmachrentalrate) getObject()));
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }
}
