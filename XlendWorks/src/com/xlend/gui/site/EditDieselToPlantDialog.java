package com.xlend.gui.site;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.dbobject.DbObject;

/**
 *
 * @author Nick Mukhin
 */
public class EditDieselToPlantDialog extends EditRecordDialog {
    public static boolean okPressed;
    
    public EditDieselToPlantDialog(String title, DbObject obj) {
        super(title, obj);
    }
    
    @Override
    protected void fillContent() {
        super.fillContent(new EditDieselToPlantPanel((DbObject) getObject()));
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }
}
