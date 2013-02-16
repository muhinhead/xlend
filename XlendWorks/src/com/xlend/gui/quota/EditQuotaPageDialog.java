package com.xlend.gui.quota;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.Xquotationpage;
import com.xlend.orm.dbobject.DbObject;

/**
 *
 * @author Nick Mukhin
 */
class EditQuotaPageDialog extends EditRecordDialog {

    public static boolean okPressed;

    public EditQuotaPageDialog(String title, DbObject obj) {
        super(title, obj);
    }

    @Override
    protected void fillContent() {
        super.fillContent(new EditQuotaPagePanel((Xquotationpage) getObject()));
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }
}
