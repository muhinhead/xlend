package com.xlend.gui.creditor;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.Xpayment;

/**
 *
 * @author Nick Mukhin
 */
public class EditPaymentDialog extends EditRecordDialog {

    public static boolean okPressed;

    public EditPaymentDialog(String title, Object obj) {
        super(title, obj);
    }
    
    @Override
    protected void fillContent() {
        super.fillContent(new EditPaymentPanel((Xpayment) getObject()));
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }
    
}
