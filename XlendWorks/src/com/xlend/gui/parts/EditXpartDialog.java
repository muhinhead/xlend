package com.xlend.gui.parts;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.Xparts;
import com.xlend.orm.Xpayment;
import com.xlend.orm.dbobject.DbObject;

/**
 *
 * @author Nick Mukhin 
 */
class EditXpartDialog extends EditRecordDialog {

    public static boolean okPressed;
    
    EditXpartDialog(String title, DbObject obj) {
        super(title, obj);
    }
    
    @Override
    protected void fillContent() {
        super.fillContent(new EditXpartPanel((Xparts) getObject()));
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }    
}
