package com.xlend.gui.quota;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.Xquotation;

/**
 *
 * @author Nick Mukhin
 */
public class EditQuotaDialog extends EditRecordDialog {
    public static boolean okPressed;

    public EditQuotaDialog(String title, Object obj) {
        super(title, obj);
    }

    @Override
    protected void fillContent() {
        super.fillContent(new EditQuotaPanel((Xquotation) getObject()));
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }
}
