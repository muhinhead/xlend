package com.xlend.gui.order;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.Xclient;
import com.xlend.orm.Xorder;

/**
 *
 * @author Nick Mukhin
 */
public class EditOrderDialog extends EditRecordDialog {
    public static boolean okPressed;
    public static Xclient xclient;

    public EditOrderDialog(String title, Object obj) {
        super(title, obj);
    }

    private void setXclient() {
        EditOrderPanel editPanel = (EditOrderPanel) getEditPanel();
        editPanel.setXclient(xclient);
    }
    
    @Override
    protected void fillContent() {
        super.fillContent(new EditOrderPanel((Xorder) getObject()));
        setXclient();
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }
    
     @Override
    public void dispose() {
        super.dispose();
        EditOrderDialog.xclient = null;
    }    
}
