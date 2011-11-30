package com.xlend.gui.order;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.Xorderpage;

/**
 *
 * @author Nick Mukhin
 */
class EditOrderPageDialog extends EditRecordDialog {

    public static boolean okPressed;

    public EditOrderPageDialog(String title, Object obj) {
        super(title, obj);
    }

    @Override
    protected void fillContent() {
        super.fillContent(new EditOrderPagePanel((Xorderpage) getObject()));
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }
}
