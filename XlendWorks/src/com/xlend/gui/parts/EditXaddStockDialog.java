package com.xlend.gui.parts;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.dbobject.DbObject;

/**
 *
 * @author Nick Mukhin
 */
class EditXaddStockDialog extends EditRecordDialog {

    public static boolean okPressed;

    EditXaddStockDialog(String title, DbObject obj) {
        super(title, obj);
    }

    @Override
    protected void fillContent() {
        super.fillContent(new EditXaddStockPanel((DbObject) getObject()));
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }
}
