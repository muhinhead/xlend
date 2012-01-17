package com.xlend.gui.hr;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.Xwagesum;

/**
 *
 * @author Nick Mukhin
 */
public class EditWagesDialog extends EditRecordDialog {
    public static boolean okPressed;
    
    public EditWagesDialog(String title, Object obj) {
        super(title, obj);
    }
    
     @Override
    protected void fillContent() {
        super.fillContent(new EditWagesPanel((Xwagesum) getObject()));
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }
}
