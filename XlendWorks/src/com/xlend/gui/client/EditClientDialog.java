package com.xlend.gui.client;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.Xclient;

/**
 *
 * @author Nick Mukhin
 */
public class EditClientDialog extends EditRecordDialog {

    public static boolean okPressed;

    public EditClientDialog(String title, Object obj) {
        super(title, obj);
    }

    @Override
    protected void fillContent() {
        super.fillContent(new EditClientPanel((Xclient) getObject()));
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }
}
