package com.xlend.gui.banking;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.Xaccounts;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.Xbankbalance;

/**
 *
 * @author Nick Mukhin
 */
class EditBalanceDialog extends EditRecordDialog {

    public static boolean okPressed;

    public EditBalanceDialog(String title, DbObject obj) {
        super(title, obj);
    }

    @Override
    protected void fillContent() {
        super.fillContent(new EditBalancePanel((Xbankbalance) getObject()));
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }
}
