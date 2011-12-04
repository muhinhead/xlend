package com.xlend.gui.employee;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.Xtimesheet;

/**
 *
 * @author Nick Mukhin
 */
public class EditTimeSheetDialog extends EditRecordDialog {
    public static boolean okPressed;

    public EditTimeSheetDialog(String title, Object obj) {
        super(title, obj);
    }

    @Override
    protected void fillContent() {
        super.fillContent(new EditTimeSheetPanel((Xtimesheet) getObject()));
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }    
}

