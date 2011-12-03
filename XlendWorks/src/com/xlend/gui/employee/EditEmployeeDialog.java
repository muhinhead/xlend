package com.xlend.gui.employee;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.Xemployee;

/**
 *
 * @author Nick Mukhin
 */
public class EditEmployeeDialog extends EditRecordDialog {
    public static boolean okPressed;

    public EditEmployeeDialog(String title, Object obj) {
        super(title, obj);
    }

    @Override
    protected void fillContent() {
        super.fillContent(new EditEmployeePanel((Xemployee) getObject()));
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }    
}
