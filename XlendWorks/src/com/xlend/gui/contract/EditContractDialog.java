package com.xlend.gui.contract;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.Xcontract;

/**
 *
 * @author Nick Mukhin
 */
public class EditContractDialog extends EditRecordDialog {

     public static boolean okPressed;

    public EditContractDialog(String title, Object obj) {
        super(title, obj);
    }
    
    @Override
    protected void fillContent() {
        super.fillContent(new EditContractPanel((Xcontract) getObject()));
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }
    
}
