package com.xlend.gui.quota;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.Xclient;
import com.xlend.orm.Xquotation;

/**
 *
 * @author Nick Mukhin
 */
public class EditQuotaDialog extends EditRecordDialog {
    public static boolean okPressed;
    public static Xclient xclient;

    public EditQuotaDialog(String title, Object obj) {
        super(title, obj);
    }

    private void setXclient() {
        EditQuotaPanel editPanel = (EditQuotaPanel) getEditPanel();
        editPanel.setXclient(xclient);
    }
    
    @Override
    protected void fillContent() {
        super.fillContent(new EditQuotaPanel((Xquotation) getObject()));
        setXclient();
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }
    
    
     @Override
    public void dispose() {
        super.dispose();
        EditQuotaDialog.xclient = null;
    }     
}
