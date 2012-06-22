package com.xlend.gui.logistics;

import com.xlend.gui.EditRecordDialog;

/**
 *
 * @author Nick Mukhin
 */
class EditTransscheduleitmDialog extends EditRecordDialog {
    public static boolean okPressed;

    public EditTransscheduleitmDialog(String title, Object obj) {
        super(title, obj);
    }

    @Override
    protected void fillContent() {
        super.fillContent(new EditTransscheduleitmPanel((Object[]) getObject()));
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }    
}
