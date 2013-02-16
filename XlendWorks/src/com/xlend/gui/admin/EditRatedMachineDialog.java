package com.xlend.gui.admin;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.Cbitems;
import com.xlend.orm.dbobject.DbObject;

/**
 *
 * @author Nick Mukhin
 */
class EditRatedMachineDialog extends EditRecordDialog {

    public static boolean okPressed;

    public EditRatedMachineDialog(String title, DbObject obj) {
        super(title, obj);
    }

    @Override
    protected void fillContent() {
        super.fillContent(new EditRatedMachinePanel((Cbitems) getObject()));
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }
}
