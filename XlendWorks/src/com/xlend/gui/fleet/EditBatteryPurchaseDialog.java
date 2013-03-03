package com.xlend.gui.fleet;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.Xbatterypurchase;

/**
 *
 * @author Nick Mukhin
 */
class EditBatteryPurchaseDialog extends EditRecordDialog {

    public static boolean okPressed;
    
    public EditBatteryPurchaseDialog(String title, Object obj) {
        super(title, obj);
    }
    @Override
    protected void fillContent() {
        super.fillContent(new EditBatteryPurchasePanel((Xbatterypurchase) getObject()));
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }
}
