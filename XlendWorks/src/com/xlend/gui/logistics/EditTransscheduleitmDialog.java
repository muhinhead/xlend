package com.xlend.gui.logistics;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.dbobject.DbObject;
import java.awt.Dimension;

/**
 *
 * @author Nick Mukhin
 */
class EditTransscheduleitmDialog extends EditRecordDialog {
    public static boolean okPressed;

    public EditTransscheduleitmDialog(String title, Object obj) {
        super(title, obj);
    }

    @Override
    protected void fillContent() {
        super.fillContent(new EditTransscheduleitmPanel((DbObject[]) getObject()));
        setPreferredSize(new Dimension(getPreferredSize().width,500));
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }    
}
