package com.xlend.gui.banking;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.Xdieselpurchase;
import com.xlend.orm.dbobject.DbObject;

/**
 *
 * @author Nick Mukhin
 */
public class EditDieselPurchaseDialog extends EditRecordDialog {

    public static boolean okPressed;

    public EditDieselPurchaseDialog(String title, DbObject obj) {
        super(title, obj);
    }

    @Override
    protected void fillContent() {
        super.fillContent(new EditDieselPurchasePanel((Xdieselpurchase) getObject()));
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }
}
