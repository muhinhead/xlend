package com.xlend.gui.fleet;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.Xlowbed;
import com.xlend.orm.Xtrip;

/**
 *
 * @author Nick Mukhin
 */
public class EditTripDialog extends EditRecordDialog {

    public static boolean okPressed;

    public EditTripDialog(String title, Object obj) {
        super(title, obj);
    }

    @Override
    protected void fillContent() {
        super.fillContent(new EditTripPanel((Xtrip) getObject()));
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }

    @Override
    public void dispose() {
        super.dispose();
        EditTripPanel.setXlowbed(null);
    }
}
