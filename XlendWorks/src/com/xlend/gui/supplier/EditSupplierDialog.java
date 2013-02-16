package com.xlend.gui.supplier;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.Xsupplier;

/**
 *
 * @author Nick Mukhin
 */
public class EditSupplierDialog extends EditRecordDialog {

    public static boolean okPressed;

    public EditSupplierDialog(String title, Object obj) {
        super(title, obj);
    }

    @Override
    protected void fillContent() {
        super.fillContent(new EditSupplierPanel((Xsupplier) getObject()));
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }
}
