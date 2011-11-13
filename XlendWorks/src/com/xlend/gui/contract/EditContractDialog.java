package com.xlend.gui.contract;

import com.xlend.gui.EditRecordDialog;
import com.xlend.gui.WorkFrame;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xcontract;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/**
 *
 * @author Nick Mukhin
 */
public class EditContractDialog extends EditRecordDialog {

     public static boolean okPressed;

    public EditContractDialog(String title, Object obj) {
        super(title, obj);
    }
    
    @Override
    protected void fillContent() {
        super.fillContent(new EditContractPanel((Xcontract) getObject()));
    }

    
    @Override
    protected AbstractAction getSaveAction() {
        return new AbstractAction("Save") {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (getEditPanel().save()) {
                        okPressed = true;
                        dispose();
                    }
                } catch (Exception ex) {
                    XlendWorks.log(ex);
                    WorkFrame.errMessageBox("Error:", ex.getMessage());
                }
            }
        };
    }
    
}
