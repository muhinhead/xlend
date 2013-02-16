package com.xlend.gui.site;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.Xppeissue;
import com.xlend.orm.dbobject.DbObject;

/**
 *
 * @author Nick Mukhin
 */
public class EditPPEissueDialog extends EditRecordDialog {

    public static boolean okPressed;

    public EditPPEissueDialog(String title, DbObject obj) {
        super(title, obj);
    }

    @Override
    protected void fillContent() {
        super.fillContent(new EditPPEissuePanel((Xppeissue) getObject()));
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }
}
