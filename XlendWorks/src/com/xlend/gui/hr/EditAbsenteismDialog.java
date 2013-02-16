package com.xlend.gui.hr;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.Xabsenteeism;
import com.xlend.orm.dbobject.DbObject;

/**
 *
 * @author Nick
 */
public class EditAbsenteismDialog extends EditRecordDialog {

    public static boolean okPressed;

    public EditAbsenteismDialog(String title, DbObject obj) {
        super(title, obj);
    }

    @Override
    protected void fillContent() {
        super.fillContent(new EditAbsenteismPanel((Xabsenteeism) getObject()));
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }
}
