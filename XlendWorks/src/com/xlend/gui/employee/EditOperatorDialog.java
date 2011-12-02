package com.xlend.gui.employee;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.Xoperator;

/**
 *
 * @author Nick Mukhin
 */
public class EditOperatorDialog extends EditRecordDialog {

    public static boolean okPressed;

    public EditOperatorDialog(String title, Object obj) {
        super(title, obj);
    }
    
    @Override
    protected void fillContent() {
        super.fillContent(new EditOperatorPanel((Xoperator) getObject()));
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }
}
