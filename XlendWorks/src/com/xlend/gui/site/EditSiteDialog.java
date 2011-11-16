package com.xlend.gui.site;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.Xsite;

/**
 *
 * @author Nick Mukhin
 */
public class EditSiteDialog extends EditRecordDialog {

    public static boolean okPressed;

    public EditSiteDialog(String title, Object obj) {
        super(title, obj);
    }

    @Override
    protected void fillContent() {
        super.fillContent(new EditSitePanel((Xsite) getObject()));
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }
}
