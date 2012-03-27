package com.xlend.gui.logistics;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.Xtripsheet;
import com.xlend.orm.dbobject.DbObject;

/**
 *
 * @author nick
 */
public class EditTripSheetDialog extends EditRecordDialog {

    public static boolean okPressed;
    
    public EditTripSheetDialog(String title, DbObject obj) {
        super(title, obj);
    }
     @Override
    protected void fillContent() {
        super.fillContent(new EditTripSheetPanel((Xtripsheet) getObject()));
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }     
}
