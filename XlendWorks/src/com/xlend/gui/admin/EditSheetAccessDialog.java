package com.xlend.gui.admin;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.Userprofile;

/**
 *
 * @author Nick Mukhin
 */
public class EditSheetAccessDialog extends EditRecordDialog {

    public static boolean okPressed;

    public EditSheetAccessDialog(String title, Object obj) {
        super(title, obj);
    }

    @Override
    protected void fillContent() {
        super.fillContent(new EditSheetAccessPanel((Userprofile) getObject()));
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }
}
