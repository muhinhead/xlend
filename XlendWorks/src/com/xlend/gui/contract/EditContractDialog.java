package com.xlend.gui.contract;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.Xcontract;

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

    
//    @Override
//    protected AbstractAction getSaveAction() {
//        return new AbstractAction("Save") {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                try {
//                    if (getEditPanel().save()) {
//                        okPressed = true;
//                        dispose();
//                    }
//                } catch (Exception ex) {
//                    XlendWorks.log(ex);
//                    GeneralFrame.errMessageBox("Error:", ex.getMessage());
//                }
//            }
//        };
//    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }
    
}
