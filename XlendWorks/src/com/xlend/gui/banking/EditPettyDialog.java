/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xlend.gui.banking;

import com.xlend.gui.EditRecordDialog;
import static com.xlend.gui.banking.EditAccountDialog.okPressed;
import com.xlend.orm.Xaccounts;
import com.xlend.orm.Xpetty;
import com.xlend.orm.dbobject.DbObject;

/**
 *
 * @author Nick Mukhin
 */
class EditPettyDialog extends EditRecordDialog {

    public static boolean okPressed;

    public EditPettyDialog(String title, DbObject obj) {
        super(title, obj);
    }

    @Override
    protected void fillContent() {
        super.fillContent(new EditPettyPanel((Xpetty) getObject()));
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }
}
