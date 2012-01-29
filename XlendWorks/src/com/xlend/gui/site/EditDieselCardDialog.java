package com.xlend.gui.site;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.Xdieselcard;

/**
 *
 * @author Nick Mukhin
 */
public class EditDieselCardDialog extends EditRecordDialog {

    public static boolean okPressed;

    public EditDieselCardDialog(String title, Object obj) {
        super(title, obj);
    }

    @Override
    protected void fillContent() {
        super.fillContent(new EditDieselCardPanel((Xdieselcard) getObject()));
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }
}