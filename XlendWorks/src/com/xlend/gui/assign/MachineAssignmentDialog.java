package com.xlend.gui.assign;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.Xmachine;
import com.xlend.orm.dbobject.DbObject;

/**
 *
 * @author Nick Mukhin
 */
public class MachineAssignmentDialog extends EditRecordDialog {
    public static boolean okPressed;

    public MachineAssignmentDialog(String title, DbObject obj) {
        super(title, obj);
    }

    @Override
    protected void fillContent() {
        super.fillContent(new MachineAssignmentPanel((Xmachine) getObject()));
//        saveButton.setText("Assign");
        saveButton.setVisible(false);
        cancelButton.setText("Close");
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }
}