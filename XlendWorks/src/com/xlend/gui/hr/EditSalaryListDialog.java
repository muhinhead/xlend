package com.xlend.gui.hr;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.Xsalarylist;
import com.xlend.orm.dbobject.DbObject;

/**
 *
 * @author Nick Mukhin
 */
public class EditSalaryListDialog extends EditRecordDialog {

    public static boolean okPressed;

    public EditSalaryListDialog(String title, DbObject obj) {
        super(title, obj);
    }

    @Override
    protected void fillContent() {
        super.fillContent(new EditSalaryListPanel((Xsalarylist) getObject()));
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }
}
