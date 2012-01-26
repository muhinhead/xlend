package com.xlend.gui.creditor;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.Xcreditor;

/**
 *
 * @author Nick Mukhin
 */
public class EditCreditorDialog extends EditRecordDialog {
    public static boolean okPressed;

    public EditCreditorDialog(String title, Object obj) {
        super(title, obj);
    }

    @Override
    protected void fillContent() {
        super.fillContent(new EditCreditorPanel((Xcreditor) getObject()));
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }
}
