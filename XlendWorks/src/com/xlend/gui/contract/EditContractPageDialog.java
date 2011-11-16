package com.xlend.gui.contract;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.Xcontractpage;

/**
 *
 * @author Nick Mukhin
 */
public class EditContractPageDialog extends EditRecordDialog {

    public static boolean okPressed;

    public EditContractPageDialog(String title, Object obj) {
        super(title, obj);
    }

    @Override
    protected void fillContent() {
        super.fillContent(new EditContractPagePanel((Xcontractpage) getObject()));
    }    

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }
    
}
