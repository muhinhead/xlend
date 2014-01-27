package com.xlend.gui.work;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.Xmachineorder;

/**
 *
 * @author Nick Mukhin
 */
public class EditMachineOrderPlacementDialog extends EditRecordDialog {

    public static boolean okPressed;
    
    public EditMachineOrderPlacementDialog(String title, Object obj) {
        super(title, obj);
    }
    @Override
    protected void fillContent() {
        super.fillContent(new EditMachineOrderPlacementPanel((Xmachineorder) getObject()));
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }
}
