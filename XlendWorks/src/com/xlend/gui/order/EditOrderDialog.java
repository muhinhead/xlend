package com.xlend.gui.order;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.Xorder;

/**
 *
 * @author Nick Mukhin
 */
public class EditOrderDialog extends EditRecordDialog {
    public static boolean okPressed;

    public EditOrderDialog(String title, Object obj) {
        super(title, obj);
    }

    @Override
    protected void fillContent() {
        super.fillContent(new EditOrderPanel((Xorder) getObject()));
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }
}
