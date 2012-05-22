package com.xlend.gui.work;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.Xhourcompare;

/**
 *
 * @author Nick Mukhin
 */
class EditHourCompareDialog extends EditRecordDialog {

    public static boolean okPressed;

    public EditHourCompareDialog(String title, Object obj) {
        super(title, obj);
    }
    
     @Override
    protected void fillContent() {
        super.fillContent(new EditHourComparePanel((Xhourcompare) getObject()));
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }
}
