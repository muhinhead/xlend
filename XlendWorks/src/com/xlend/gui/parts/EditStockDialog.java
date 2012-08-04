package com.xlend.gui.parts;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.Xpartstocks;
import com.xlend.orm.dbobject.DbObject;

/**
 *
 * @author Nick Mukhin
 */
class EditStockDialog extends EditRecordDialog {

    public static boolean okPressed;

    public EditStockDialog(String title, DbObject obj) {
        super(title, obj);
    }
    
    @Override
    protected void fillContent() {
        super.fillContent(new EditStockPanel((Xpartstocks) getObject()));
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }
}
