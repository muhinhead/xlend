package com.xlend.gui.parts;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.Xparts;
import com.xlend.orm.Xpartstocks;
import com.xlend.orm.dbobject.DbObject;

/**
 *
 * @author Nick Mukhin
 */
class EditPartStockDialog extends EditRecordDialog {

    public static boolean okPressed;

    public EditPartStockDialog(String title, DbObject obj) {
        super(title, obj);
    }
    
    @Override
    protected void fillContent() {
        super.fillContent(new EditPartStockPanel((Xpartstocks) getObject()));
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }
}
