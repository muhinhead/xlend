package com.xlend.gui.site;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.Xissuing;

/**
 *
 * @author Nick Mukhin
 */
public class EditIssuingDialog extends EditRecordDialog {
    public static boolean okPressed;

    public EditIssuingDialog(String title, Object obj) {
        super(title, obj);
    }

    @Override
    protected void fillContent() {
        super.fillContent(new EditIssuingPanel((Xissuing) getObject()));
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }
    
}