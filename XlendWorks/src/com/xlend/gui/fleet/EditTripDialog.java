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
    public static Integer xlowbed_id;;

    public EditTripDialog(String title, Object obj) {
        super(title, obj);
    }
    
    private void setXlowbed() {
        EditTripPanel editPanel = (EditTripPanel) getEditPanel();
        editPanel.setXlowbed(xlowbed_id);
    }

    @Override
    protected void fillContent() {
        super.fillContent(new EditTripPanel((Xtrip) getObject()));
        setXlowbed();
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }
    
    @Override
    public void dispose() {
        super.dispose();
        EditTripDialog.xlowbed_id = null;
    }
    
    
}

