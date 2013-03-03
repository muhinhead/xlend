package com.xlend.gui.fleet;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.Xbateryissue;

/**
 *
 * @author Nick Mukhin
 */
class EditBatteryIssueDialog extends EditRecordDialog {

    public static boolean okPressed;
    
    public EditBatteryIssueDialog(String title, Object obj) {
        super(title, obj);
    }
    @Override
    protected void fillContent() {
        super.fillContent(new EditBatteryIssuePanel((Xbateryissue) getObject()));
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }
}
