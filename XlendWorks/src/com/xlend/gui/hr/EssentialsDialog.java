package com.xlend.gui.hr;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.dbobject.DbObject;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/**
 *
 * @author Nick Mukhin
 */
public class EssentialsDialog extends EditRecordDialog {
    public static boolean okPressed;

    public EssentialsDialog(String title, DbObject obj) {
        super(title, obj);
    }

    @Override
    protected void fillContent() {
        super.fillContent(new EssentialsPanel((DbObject)getObject()));
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }
    
    @Override
    protected AbstractAction getCancelAction() {
        return new AbstractAction("Cancel") {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((EssentialsPanel)getEditPanel()).removeAddedRows();
                dispose();
            }
        };
    }
}
