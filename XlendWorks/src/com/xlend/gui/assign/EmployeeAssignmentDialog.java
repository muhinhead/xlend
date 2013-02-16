package com.xlend.gui.assign;

import com.xlend.gui.EditRecordDialog;
import com.xlend.gui.RecordEditPanel;
import com.xlend.orm.Xemployee;
import com.xlend.orm.Xopmachassing;
import com.xlend.orm.dbobject.DbObject;

/**
 *
 * @author Nick Mukhin
 */
public class EmployeeAssignmentDialog extends EditRecordDialog {

    public static boolean okPressed;

    public EmployeeAssignmentDialog(String title, DbObject obj) {
        super(title, obj);
    }

    @Override
    protected void fillContent() {
        super.fillContent(new EmployeeAssignmentPanel((Xemployee) getObject()));
//        saveButton.setText("Assign");
        saveButton.setVisible(false);
        cancelButton.setText("Close");
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }
}
