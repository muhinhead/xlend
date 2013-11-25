package com.xlend.gui.employee;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.Xemployee;
import javax.swing.WindowConstants;

/**
 *
 * @author Nick Mukhin
 */
public class EditEmployeeDialog extends EditRecordDialog {

    public static boolean okPressed;
    private EditEmployeePanel edPanel;

    public EditEmployeeDialog(String title, Object obj) {
        super(title, obj);
    }

    @Override
    protected void fillContent() {
        super.fillContent(edPanel = new EditEmployeePanel((Xemployee) getObject()));
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }

    @Override
    public void freeResources() {
        edPanel.freeResources();
        super.freeResources();
    }
}
