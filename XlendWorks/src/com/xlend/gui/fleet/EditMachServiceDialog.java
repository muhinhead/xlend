package com.xlend.gui.fleet;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.Xmachservice;

/**
 *
 * @author Nick Mukhin
 */
public class EditMachServiceDialog extends EditRecordDialog {
    public static boolean okPressed;

    public EditMachServiceDialog(String title, Object obj) {
        super(title, obj);
    }
    @Override
    protected void fillContent() {
        super.fillContent(new EditMachServicePanel((Xmachservice) getObject()));
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }    
}
