package com.xlend.gui.site;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.Xdieselpchs;
import com.xlend.orm.Xsite;

/**
 *
 * @author Nick Mukhin
 */
class EditDieselPurchaseDialog extends EditRecordDialog {

    public static boolean okPressed;

    public EditDieselPurchaseDialog(String title, Object obj) {
        super(title, obj);
    }

    @Override
    protected void fillContent() {
        super.fillContent(new EditDieselPurchasePanel((Xdieselpchs) getObject()));
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }

    @Override
    public void dispose() {
        super.dispose();
        EditMachineOnSiteItemDialog.xsite = null;
    }
}
