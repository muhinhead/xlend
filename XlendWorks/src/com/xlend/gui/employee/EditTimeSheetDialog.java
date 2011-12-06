package com.xlend.gui.employee;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.Xemployee;
import com.xlend.orm.Xtimesheet;

/**
 *
 * @author Nick Mukhin
 */
public class EditTimeSheetDialog extends EditRecordDialog {

    public static boolean okPressed;
    public static Xemployee xemployee;

    public EditTimeSheetDialog(String title, Object obj) {
        super(title, obj);
    }

    private void setXemployee() {
        EditTimeSheetPanel editPanel = (EditTimeSheetPanel) getEditPanel();
        editPanel.setXemployee(xemployee);
    }

    @Override
    protected void fillContent() {
        super.fillContent(new EditTimeSheetPanel((Xtimesheet) getObject()));
        setXemployee();
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }
    
    @Override
    public void dispose() {
        super.dispose();
        EditTimeSheetDialog.xemployee = null;
    }
}
